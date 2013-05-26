package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public interface IEntity {
	
	/**
	 * @return	the body of this entity
	 */
	public Body getBody();
	
	/**
	 * @return	the height of this entity in meters
	 */
	public float getHeight();
	
	/**
	 * @return	the width of this entity in meters
	 */
	public float getWidth();

	/**
	 * @return	the position of the center of this entity in meters
	 */
	public Vec2 getPosMeters();
	
	/**
	 * @return 	the position of the top left corner of this entity in pixels
	 */
	public Vec2 getPosPixels();
	
	/**
	 * A method to easily connect the controller to it's model.
	 * @return 	the ID of this entity
	 */
	public int getID();
}
