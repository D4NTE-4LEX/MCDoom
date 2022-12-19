package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.PlasmagunModel;
import mod.azure.doom.item.weapons.PlasmaGun;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PlasmagunRender extends GeoItemRenderer<PlasmaGun> {
	public PlasmagunRender() {
		super(new PlasmagunModel());
	}
}