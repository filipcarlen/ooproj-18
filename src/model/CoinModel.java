package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class CoinModel extends AbstractCollectibleModel {
	
	/** What value a collectible item holds (which points you get) */
	private int value = 1;

	public CoinModel(World w, Vec2 pixelPos) {
		super(w, pixelPos);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getValue() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setValue(int value) {
		// TODO Auto-generated method stub
		
	}

}
