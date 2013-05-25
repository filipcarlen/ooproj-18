package model;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class EnergyDrink extends AbstractPowerUp {
	
	private final double HP_BOOST = 0.5;

	public EnergyDrink(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
		super.setHpBoost(HP_BOOST);
	}
}
