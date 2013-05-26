package controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface IEntityController {
	
	/**
	 * A method to make sure the controller updates accordingly.
	 * @param gc	the game container
	 * @param sbg	the state based game
	 * @param delta	the time in between updates in milliseconds
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta);
	
	/**
	 * A method to make sure the controller renders accordingly.
	 * @param gc	the game container
	 * @param sbg	the state based game
	 * @param g		the graphics
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g);
	
	/**
	 * A method to easily connect the controller to it's model.
	 * @return 	the ID of this entity controller
	 */
	public int getID();
}
