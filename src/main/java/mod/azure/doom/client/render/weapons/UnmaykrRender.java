package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.UnmaykrModel;
import mod.azure.doom.item.weapons.Unmaykr;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class UnmaykrRender extends GeoItemRenderer<Unmaykr> {
	public UnmaykrRender() {
		super(new UnmaykrModel());
	}
}