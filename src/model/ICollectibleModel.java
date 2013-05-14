package model;

import org.jbox2d.common.Vec2;

public interface ICollectibleModel extends IEntityModel {
	
	public int getValue();
	public void setValue(int value);
	public boolean bodyExists();
	public void destroyBody();

}
