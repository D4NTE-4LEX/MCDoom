package mod.azure.doom.client.render;

import mod.azure.doom.client.models.Baron2016Model;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Baron2016Render extends GeoEntityRenderer<BaronEntity> {

	public Baron2016Render(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new Baron2016Model());
	}

	@Override
	protected float getDeathMaxRotation(BaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}