package controller;

import model.BulletModel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Navigation;
import view.BulletView;

public class BulletController implements IEntityController{

	private BulletModel model;
	private BulletView view;
	
	public BulletController(BulletModel model){
		this.model = model;
		this.view = new BulletView(model);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		Body body = this.model.getBody();
		float distance = Math.abs(this.model.getPosMeters().x - this.model.getFirstPos().x);

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
