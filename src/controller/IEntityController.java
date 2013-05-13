package controller;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface IEntityController {
	
	/**
	 * A method to make sure the controller updates accordingly.
	 * @param container
	 * @param game
	 * @param delta
	 */
	public void update(GameContainer container, StateBasedGame game, int delta);
	
	/**
	 * A method to make sure the controller renders accordingly.
	 * @param container
	 * @param game
	 * @param g
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g);
	
	/**
	 * A method to easily connect the controller to model.
	 * @return The ID of the entitycontroller.
	 */
	public int getID();
}
