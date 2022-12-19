package mod.azure.doom.client.render;

import mod.azure.doom.client.models.Hellknight2016Model;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class Hellknight2016Render extends GeoEntityRenderer<Hellknight2016Entity> {

	public Hellknight2016Render(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new Hellknight2016Model());
	}

	@Override
	protected float getDeathMaxRotation(Hellknight2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}