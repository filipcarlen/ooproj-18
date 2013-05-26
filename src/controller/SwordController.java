package controller;

import model.Sword;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Navigation;

/** 
 * A controller class for a Sword
 * 
 * @author Project Group 18 (Chalmers, 2013)
 */

public class SwordController implements IEntityController{

	private Sword model;	
	
	public SwordController(Sword model){
		this.model = model;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {

		Body body = this.model.getBody();
		/* The distance the sword has moved this time */
		float distance;
		
		/* If the sword is alive and not moving, we apply force in the right direction and tell the model it is now moving.
		 * If the sword is alive and moving, we check if it has traveled as far as it can and
		 * in that case remove it.
		 * If the body exists but isn't alive we remove it. */
		if(body != null){
			if(this.model.isAlive()){

				distance = Math.abs(this.model.getPosMeters().x - this.model.getFirstPos().x);
	
				if(!this.model.isMoving()){
					
					if(this.model.getNavigation() == Navigation.WEST){
						body.applyForce(body.getWorldVector(new Vec2(-10.0f, 0.0f)), body.getPosition());
						this.model.setMoving(true);
						
					}else if(this.model.getNavigation() == Navigation.EAST){
						body.applyForce(body.getWorldVector(new Vec2(10.0f, 0.0f)), body.getPosition());
						this.model.setMoving(true);
					}
	
				} else if(this.model.getRange() < distance){
					body.getWorld().destroyBody(body);
					this.model.destroyEntity();
				}
				
			} else{
				body.getWorld().destroyBody(body);
				this.model.destroyEntity();
			}
		}	
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {}

	@Override
	public int getID() {
		return model.getID();
	}

}
