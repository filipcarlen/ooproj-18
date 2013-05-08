package controller;

import model.ICollectibleModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import states.PlayState;
import view.CollectibleView;

/**
 * A controller class for a Collectible Object
 * @author filipcarlen
 *
 */

public class CollectibleController implements IEntityController {
	
	private ICollectibleModel model;
	private CollectibleView view;
	
	public CollectibleController(ICollectibleModel model){	
		this.model = model;
		 view = new CollectibleView(model);
	} 

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(model.bodyExists()){
			view.render(container, game, g);
		}
		
		else{
   		 model.destroyBody();
   		 PlayState.removeController();
   	 }
	}	
}
