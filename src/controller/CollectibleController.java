package controller;

import model.ICollectible;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import view.CollectibleView;

/**
 * A controller class for a Collectible Object
 * @author group 18
 *
 */
public class CollectibleController implements IEntityController {
	
	/** Reference to a CollectibleModel */
	private ICollectible model;
	
	/** Reference to a CollectibleView */
	private CollectibleView view;
	
	/** ID of this CollectibleController */
	private int id;
	
	/** Reference to a PlayStateController */
	private IPlayStateController controller;
	
	/**
	 * Constructor creating a CollectibleController
	 * @param CollectibleModel model
	 * @throws SlickException
	 */
	public CollectibleController(ICollectible model, IPlayStateController controller) throws SlickException{	
		this.model = model;
		this.controller = controller;
		this.id = this.model.getID();
		 view = new CollectibleView(model);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(model.bodyExists()){
			view.render(container, game, g);
		}
		
		else{
			model.destroyBody();
   		 	controller.removeEntity(model.getID());
   	 	}
	}
	
	@Override
	public int getID(){
		return this.id;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
	}	
}
