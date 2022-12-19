package mod.azure.doom.client.render;

import mod.azure.doom.client.models.SpiderMastermindModel;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpiderMastermindRender extends GeoEntityRenderer<SpiderMastermindEntity> {

	public SpiderMastermindRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new SpiderMastermindModel());
	}

	@Override
	protected float getDeathMaxRotation(SpiderMastermindEntity entityLivingBaseIn) {
		return 0.0F;
	}
}