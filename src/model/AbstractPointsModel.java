package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public abstract class AbstractPointsModel extends AbstractCollectibleModel {
	
	public AbstractPointsModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
	}

	private int value;
	
	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
		
	}
}
