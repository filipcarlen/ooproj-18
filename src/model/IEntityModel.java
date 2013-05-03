package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public interface IEntityModel {
	
	/**
	 * @return The position of the center of this entity in meters.
	 */
	public Vec2 getPosMeters();
	
	/**
	 * @return The position of the top left corner of this entity in pixels.
	 */
	public Vec2 getPosPixels();
	
	/**
<<<<<<< Updated upstream
	 *
=======
	 * 
>>>>>>> Stashed changes
	 * @return The Body in the world
	 */
	public Body getBody();

}