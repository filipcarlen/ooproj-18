package model;

import org.jbox2d.common.Vec2;

public interface ICollectibleModel {
	
	public int getValue();
	public void setValue(int value);
	public Vec2 getPosPixels();
	public float getRadius();
	public boolean bodyExists();

}
