package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public interface IEntity {
	
	/**
	 * @return The body of this entity
	 */
	public Body getBody();
	
	/**
	 * @return - The height of the hero in Meters
	 */
	public float getHeight();

	/**
	 * A method to easily connect the controller to model.
	 * @return The ID of the entitymodel.
	 */
	public int getID();
	
	/**
	 * @return - The width of the hero in Meter
	 */
	public float getWidth();

	/**
	 * @return The position of the center of this entity in meters.
	 */
	public Vec2 getPosMeters();
	
	/**
	 * @return The position of the top left corner of this entity in pixels.
	 */
	public Vec2 getPosPixels();

}
