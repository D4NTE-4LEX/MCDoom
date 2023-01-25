package mod.azure.doom.client.render;

import mod.azure.doom.client.models.CueballModel;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class CueBallRender extends GeoEntityRenderer<CueBallEntity> {

	public CueBallRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new CueballModel());
	}

	@Override
	protected int getBlockLightLevel(CueBallEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	protected float getDeathMaxRotation(CueBallEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public boolean shouldShowName(CueBallEntity entity) {
		return false;
	}
}