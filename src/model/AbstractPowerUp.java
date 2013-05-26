package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * Abstract class representing a collectible object with ability to boost characters hp
 * @author group 18
 *
 */
public abstract class AbstractPowerUp extends AbstractCollectible {

	private double hpBoost;
	
	public AbstractPowerUp(World w, Vec2 pixelPos, int id, float width, float height) {
		super(w, pixelPos, id, width, height);
	}
	
	public double gethpBoost() {
		return this.hpBoost;
	}

	public void setHpBoost(double hpBoost) {
		this.hpBoost = hpBoost;
		
	}
}