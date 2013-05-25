package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public abstract class AbstractPoints extends AbstractCollectible {
	
	private int value;
	
	public AbstractPoints(World w, Vec2 pixelPos, int id, float width, float height) {
		super(w, pixelPos, id, width, height);
	}
	
	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
		
	}
}
