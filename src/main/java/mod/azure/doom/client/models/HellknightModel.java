package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class HellknightModel extends GeoModel<HellknightEntity> {

	@Override
	public Identifier getModelResource(HellknightEntity object) {
		return new Identifier(DoomMod.MODID, "geo/hellknight.geo.json");
	}

	@Override
	public Identifier getTextureResource(HellknightEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/hellknight-" + (object.getVariant() == 2 ? "64" : "texturemap") + ".png");
	}

	@Override
	public Identifier getAnimationResource(HellknightEntity object) {
		return new Identifier(DoomMod.MODID, "animations/baron_hell_animation.json");
	}

	@Override
	public void setCustomAnimations(HellknightEntity animatable, long instanceId,
			AnimationState<HellknightEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX((entityData.headPitch() + 20) * ((float) Math.PI / 360F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
		}
	}

	@Override
	public RenderLayer getRenderType(HellknightEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}