package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;


public class GemModel extends AbstractCollectibleModel {
	
	/** What value a collectible item holds (which points you get) */
	private int value = 5;

	public GemModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return this.value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
		
	}
}
