package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.BFG;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class BFGModel extends GeoModel<BFG> {
	@Override
	public ResourceLocation getModelResource(BFG object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bfgeternal.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BFG object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/bfgeternal.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BFG animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/bfg.animation.json");
	}
}
