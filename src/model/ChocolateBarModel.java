package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class ChocolateBarModel extends AbstractPowerUpModel {

	private final float HP_BOOST = 0.2f;

	public ChocolateBarModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
		super.setHpBoost(HP_BOOST);
	}
}
