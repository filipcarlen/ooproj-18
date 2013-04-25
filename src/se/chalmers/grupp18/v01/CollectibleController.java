package se.chalmers.grupp18.v01;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A controller class for a Collectible Object
 * @author filipcarlen
 *
 */

public class CollectibleController implements IEntityController {
	
	private CollectibleModel model;
	private CollectibleView view;
	private Vec2 position;
	private World world;
	
	public CollectibleController(CollectibleModel model){
		
		this.model = model;
		view = new CollectibleView(model);
	} 

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
