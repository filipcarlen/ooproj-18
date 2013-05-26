package model;

/**
 * Interface for all collectible objects
 * @author filipcarlen
 *
 */
public interface ICollectible extends IEntity {
		
	/** Checks weather a collectible object is still alive
	 * 
	 * @return boolean 
	 */
	public boolean bodyExists();
	
	/**
	 * Destroys the body of a collectible object
	 */
	public void destroyBody();

	/**
	 * Method that sets the boolean containg life status of a collectible
	 * to false
	 */
	public void killBody();
}
