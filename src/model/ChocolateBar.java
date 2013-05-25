package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class ChocolateBar extends AbstractPowerUp {

	private final double HP_BOOST = 0.2;

	public ChocolateBar(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
		super.setHpBoost(HP_BOOST);
	}
}
