package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class CoinModel extends AbstractCollectibleModel {
	
	/** What value a collectible item holds (which points you get) */
	private int value = 1;

	public CoinModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
		
	}

}
