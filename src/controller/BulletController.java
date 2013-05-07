package controller;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import view.BulletView;
import model.BulletModel;

/** A controller class for a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class BulletController implements IEntityController{

	private BulletModel model;
	private BulletView view;
	// The Distance the bullet has moved
	private Vec2 distance;
	
	public BulletController(BulletModel model){
		this.model = model;
		this.view = new BulletView(model);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// To get the distance we take the position from where the bullet was fired minus the current position
		this.distance = Vec2.abs(this.model.getPosPixels().sub(this.model.getFirstPos())); 
		if(this.model.getRange() < distance.length()){
			this.model.destroyEntity();
		}else{
			
		}
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		this.view.render(container, game, g);
	}
	
	
}
