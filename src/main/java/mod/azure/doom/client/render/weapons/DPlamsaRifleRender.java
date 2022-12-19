package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.DPlamsaRifleModel;
import mod.azure.doom.item.weapons.DPlasmaRifle;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DPlamsaRifleRender extends GeoItemRenderer<DPlasmaRifle> {
	public DPlamsaRifleRender() {
		super(new DPlamsaRifleModel());
	}
}