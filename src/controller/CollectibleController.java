package controller;

import model.CollectibleModel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import view.CollectibleView;

/**
 * A controller class for a Collectible Object
 * @author filipcarlen
 *
 */

public class CollectibleController implements IEntityController {
	
	private CollectibleModel model;
	private CollectibleView view;
	
	public CollectibleController(CollectibleModel model){
		
		this.model = model;
		view = new CollectibleView(model);
	} 

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
			view.render(container, game, g);
	}	
}
