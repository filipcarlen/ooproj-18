package controller;

import model.MovingFoeModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import states.PlayState;
import utils.Navigation;
import view.MovingFoeView;

public class MovingFoeController implements IEntityController {
	
	private MovingFoeModel model;
	private MovingFoeView view;
	
	public MovingFoeController(MovingFoeModel model){
		this.model = model;
		view = new MovingFoeView(model);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		Vec2 right = this.model.getBody().getWorldVector(new Vec2(-0.05f, 0.0f));
		Vec2 left = this.model.getBody().getWorldVector(new Vec2(0.05f, 0.0f));
		
		Vec2 heroPos = PlayState.getHeroModel().getPosPixels();
		Vec2 diffVec = this.model.getPosPixels().sub(heroPos);
			
		this.model.getBody().setLinearDamping(2.5f);
			
		//Check if the hero is visible to this foe.
		if(diffVec.length() < this.model.SIGHT_RANGE){
				
			//Make this foe walk towards the hero and set the correct animation. 
			if((heroPos.x < this.model.getPosPixels().x) && 
					!(this.model.getWeapon().isWithinRange(this.model.getPosMeters(), PlayState.getHeroModel().getPosMeters()))){
				
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_LEFT);
				this.model.getBody().applyLinearImpulse(left, this.model.getPosMeters());
				
			} else if ((heroPos.x > this.model.getPosPixels().x) && 
					!(this.model.getWeapon().isWithinRange(this.model.getPosMeters(), PlayState.getHeroModel().getPosMeters()))){
				
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_RIGHT);
				this.model.getBody().applyLinearImpulse(right, this.model.getPosMeters());
			}
			
			//Attack the hero if he/she is within the range of this foe's weapon.
			if(this.model.getWeapon().isWithinRange(this.model.getPosMeters(), PlayState.getHeroModel().getPosMeters())){
					
				if(heroPos.x < this.model.getPosPixels().x) {
					this.model.getWeapon().fight(this.model.getBody(),Navigation.WEST);
					this.view.setCurrentAnim(MovingFoeView.AnimationType.SWORD_LEFT);
				} else if(heroPos.x > this.model.getPosPixels().x) {
					this.model.getWeapon().fight(this.model.getBody(),Navigation.EAST);
					this.view.setCurrentAnim(MovingFoeView.AnimationType.SWORD_RIGHT);
				}
			}
		}
	}

	

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if(this.model.isAlive()) {
			this.view.render(gc, sbg, g);
		} else {
			PlayState.removeEntity(this.model.getID());
		}
	}

	@Override
	public int getID() {
		return this.model.getID();
	}
}
