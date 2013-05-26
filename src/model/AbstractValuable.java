package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
/**
 * Abstract class representing a collectible object with ability to give character points
 * @author Filip Carlén
 *
 */
public abstract class AbstractValuable extends AbstractCollectible {
	
	private int value;
	
	public AbstractValuable(World w, Vec2 pixelPos, int id, float width, float height) {
		super(w, pixelPos, id, width, height);
	}
	
	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
		
	}
}
