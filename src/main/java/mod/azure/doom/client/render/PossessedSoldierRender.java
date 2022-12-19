package mod.azure.doom.client.render;

import mod.azure.doom.client.models.PossessedSoldierModel;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PossessedSoldierRender extends GeoEntityRenderer<PossessedSoldierEntity> {

	public PossessedSoldierRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new PossessedSoldierModel());
	}

	@Override
	protected float getDeathMaxRotation(PossessedSoldierEntity entityLivingBaseIn) {
		return 0.0F;
	}
}