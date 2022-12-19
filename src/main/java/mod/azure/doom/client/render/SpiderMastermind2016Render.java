package mod.azure.doom.client.render;

import mod.azure.doom.client.models.SpiderMastermind2016Model;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpiderMastermind2016Render extends GeoEntityRenderer<SpiderMastermind2016Entity> {

	public SpiderMastermind2016Render(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new SpiderMastermind2016Model());
	}

	@Override
	protected float getDeathMaxRotation(SpiderMastermind2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}