package controller;

import model.MovingFoeModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Navigation;
import utils.Utils;
import view.MovingFoeView;

public class MovingFoeController implements IEntityController {
	
	private MovingFoeModel model;
	private MovingFoeView view;
	
	private IPlayStateController playState;
	
	public MovingFoeController(MovingFoeModel model, IPlayStateController playState){
		this.model = model;
		this.view = new MovingFoeView(model);
		
		this.playState = playState;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
		Vec2 right = this.model.getBody().getWorldVector(new Vec2(-0.05f, 0.0f));
		Vec2 left = this.model.getBody().getWorldVector(new Vec2(0.05f, 0.0f));
		
		Vec2 heroPos = Utils.metersToPixels(this.playState.getHeroModel().getPosMeters());
		Vec2 foePos = Utils.metersToPixels(this.model.getPosMeters());
		Vec2 diffVec = foePos.sub(heroPos);
			
		this.model.getBody().setLinearDamping(2.5f);
			
		//Check if the hero is visible to this foe.
		if(diffVec.length() < this.model.SIGHT_RANGE){
				
			//Make this foe walk towards the hero and set the correct animation. 
			if((heroPos.x < foePos.x) && 
					!(this.model.getWeapon().isWithinRange(this.model.getPosMeters(), playState.getHeroModel().getPosMeters()))){
				
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_LEFT);
				this.model.getBody().applyLinearImpulse(left, this.model.getPosMeters());
				
			} else if((foePos.x < heroPos.x) && 
					!(this.model.getWeapon().isWithinRange(this.model.getPosMeters(), playState.getHeroModel().getPosMeters()))){
				
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_RIGHT);
				this.model.getBody().applyLinearImpulse(right, this.model.getPosMeters());
				
			} else if((heroPos.x < foePos.x) && (Math.abs(foePos.y-heroPos.y) >= (Utils.METER_IN_PIXELS))) {
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_LEFT);
				this.model.getBody().applyLinearImpulse(left, this.model.getPosMeters());
				
			} else if((heroPos.x > foePos.x) && (Math.abs(foePos.y-heroPos.y) >= (Utils.METER_IN_PIXELS))) {
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_RIGHT);
				this.model.getBody().applyLinearImpulse(right, this.model.getPosMeters());
				
			}
			
			//Attack the hero if he/she is within the range of this foe's weapon and if this foe is not to high above or too low below the hero.
			if((this.model.getWeapon().isWithinRange(this.model.getPosMeters(), this.playState.getHeroModel().getPosMeters())) && 
					(Math.abs(foePos.y-heroPos.y) < (Utils.METER_IN_PIXELS))){
					
				if(heroPos.x < this.model.getPosPixels().x) {
					if(this.model.getWeapon().fight(this.model,Navigation.WEST)){
						this.view.setCurrentAnim(MovingFoeView.AnimationType.GUN_LEFT);
					}
				} else if(heroPos.x > this.model.getPosPixels().x) {
					if(this.model.getWeapon().fight(this.model,Navigation.EAST)){
						this.view.setCurrentAnim(MovingFoeView.AnimationType.GUN_RIGHT);
					}
				}
			}
		} else {
			this.view.setCurrentAnim(MovingFoeView.AnimationType.STAND);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if(this.model.isAlive()) {
			this.view.render(gc, sbg, g);
		} else {
			this.model.destroyEntity();
			this.playState.getHeroModel().incrementKillCount();
			this.playState.getHeroModel().incrementScore(this.model.getValue());
			this.playState.removeEntity(this.model.getID());
		}
	}

	@Override
	public int getID() {
		return this.model.getID();
	}
}
