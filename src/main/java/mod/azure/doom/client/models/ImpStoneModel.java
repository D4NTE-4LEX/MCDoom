package mod.azure.doom.client.models;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ImpStoneModel extends GeoModel<ImpStoneEntity> {

	@Override
	public ResourceLocation getModelResource(ImpStoneEntity object) {
		return DoomMod.modResource("geo/imp2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ImpStoneEntity object) {
		return DoomMod.modResource("textures/entity/stoneimp.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ImpStoneEntity object) {
		return DoomMod.modResource("animations/imp2016.animation.json");
	}

	@Override
	public void setCustomAnimations(ImpStoneEntity animatable, long instanceId, AnimationState<ImpStoneEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		var head = getAnimationProcessor().getBone("neck");
		var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderType getRenderType(ImpStoneEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}