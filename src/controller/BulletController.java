package controller;

import model.Bullet;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Navigation;
import view.BulletView;

/** 
 * A controller class for a Bullet
 * 
 * @author Project Group 18 (Chalmers, 2013)
 */

public class BulletController implements IEntityController{

	private Bullet model;
	private BulletView view;
	
	public BulletController(Bullet model){
		this.model = model;
		this.view = new BulletView(model);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		Body body = this.model.getBody();
		
		/* The distance the bullet has moved */
		float distance = Math.abs(this.model.getPosMeters().x - this.model.getFirstPos().x);

		/* First checks if the body is moving or not. If it isn't we apply force in the right direction and 
		 * tell the model that it is now moving. If it is moving we check if it has traveled as far as it can and
		 * in that case remove it */
		if(!this.model.isMoving()){
			
			if(this.model.getNavigation() == Navigation.WEST){
				body.applyForce(body.getWorldVector(new Vec2(-10.0f, 0.0f)), body.getPosition());
				this.model.setMoving(true);
				
			}else if(this.model.getNavigation() == Navigation.EAST){
				body.applyForce(body.getWorldVector(new Vec2(10.0f, 0.0f)), body.getPosition());
				this.model.setMoving(true);
			}

		} else if(this.model.getRange() < distance){
			this.model.destroyEntity();
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		if(this.model.isAlive()){
			this.view.render(gc, sbg, g);
		}
	}
	
	@Override
	public int getID(){
		return this.model.getID();
	}
}
