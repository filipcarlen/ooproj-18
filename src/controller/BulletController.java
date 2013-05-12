package controller;

import org.jbox2d.common.Vec2;
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

	/** The model connected to this controller */
	private BulletModel model;
	/** The view connected to this controller */
	private BulletView view;
	/** The Distance the bullet has moved */
	private Vec2 distance;
	/** Tells us if the bullet is moving or not */
	private boolean isMoving;

	
	public BulletController(BulletModel model){
		this.model = model;
		this.view = new BulletView(model);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// To get the distance we take the position from where the bullet was fired minus the current position
		this.distance = Vec2.abs(this.model.getPosPixels().sub(this.model.getFirstPos()));
		if(!isMoving){
			if(this.model.getFirstPos().x - this.model.getTargetPos().x > 0){
				this.model.getBody().applyForce(this.model.getBody().getWorldVector(new Vec2(-10.0f, 0.0f)), this.model.getBody().getPosition());
				isMoving = true;
			}else if(this.model.getFirstPos().x - this.model.getTargetPos().x <= 0){
				this.model.getBody().applyForce(this.model.getBody().getWorldVector(new Vec2(10.0f, 0.0f)), this.model.getBody().getPosition());
				isMoving = true;
			}
		
		} else{
			if(this.model.getRange() < distance.length()){
				this.model.destroyEntity();
				isMoving = false;
			}
	
		}
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		this.view.render(container, game, g);
	}
	
	
}
