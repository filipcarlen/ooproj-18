package controller;

import model.SwordModel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Navigation;
import view.SwordView;

public class SwordController implements IEntityController{

	private SwordModel model;	
	private SwordView view;
	private IPlayStateController playState;
	
	public SwordController(SwordModel model){
		this.model = model;
		this.view = new SwordView(model);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {

		Body body = this.model.getBody();
		float distance;
		if(body != null){
			if(this.model.isAlive()){

				// To get the distance we take the current position minus the position from where the bullet was fired
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
					this.model.setBody(null);
					this.model.destroyEntity();
				}
				
			} else{
				body.getWorld().destroyBody(body);
				this.model.setBody(null);
				this.model.destroyEntity();
			}
		}		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		if(this.model.isAlive()){
			this.view.render(container, game, g);
		}
	}

	@Override
	public int getID() {
		return model.getID();
	}

}
