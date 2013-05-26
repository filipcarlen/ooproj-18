package controller;

import model.Hero;

/** 
 * An interface used by the controller-classes so that they will be able to call on methods in PlayState
 * 
 * @author Project Group 18 (Chalmers, 2013)
 */

public interface IPlayStateController {
	
	/**
	 * @return the hero in this game
	 */
	public Hero getHeroModel();
		
	/**
	 * A method used to remove an entity from the world
	 * @param ID of the entity that should be removed
	 */
	public void removeEntity(int ID);
	
}
