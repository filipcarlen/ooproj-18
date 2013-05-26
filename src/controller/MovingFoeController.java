package controller;

import model.MovingFoe;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Navigation;
import utils.Utils;
import view.MovingFoeView;

public class MovingFoeController implements IEntityController {
	
	private MovingFoe model;
	private MovingFoeView view;
	
	private IPlayStateController playState;
	
	public MovingFoeController(MovingFoe model, IPlayStateController playState){
		this.model = model;
		this.view = new MovingFoeView(model);
		
		this.playState = playState;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
		Vec2 right = this.model.getBody().getWorldVector(new Vec2(-0.008f, 0.0f));
		Vec2 left = this.model.getBody().getWorldVector(new Vec2(0.008f, 0.0f));
		
		Vec2 heroPos = Utils.metersToPixels(this.playState.getHeroModel().getPosMeters());
		Vec2 foePos = Utils.metersToPixels(this.model.getPosMeters());
		float diffX = Math.abs(foePos.x - heroPos.x);
		float diffY = Math.abs(foePos.y - heroPos.y);
			
		this.model.getBody().setLinearDamping(2.5f);
			
		//Check if the hero is visible to this foe.
		if(diffX < this.model.SIGHT_RANGE_X && diffY < this.model.SIGHT_RANGE_Y){
				
			//Make this foe walk towards the hero and set the correct animation. 
			if(((heroPos.x < foePos.x) && !(this.model.getWeapon().isWithinRange(this.model.getBody(), playState.getHeroModel().getBody()))) || 
					((heroPos.x < foePos.x - Utils.METER_IN_PIXELS) && (Math.abs(foePos.y-heroPos.y) >= (Utils.METER_IN_PIXELS)))){
				
				this.moveFoe(left, MovingFoeView.AnimationType.WALK_LEFT);
				
			} else if(((foePos.x < heroPos.x) && !(this.model.getWeapon().isWithinRange(this.model.getBody(), playState.getHeroModel().getBody()))) ||
					((heroPos.x > foePos.x + Utils.METER_IN_PIXELS) && (Math.abs(foePos.y-heroPos.y) >= (Utils.METER_IN_PIXELS)))){
				
				this.moveFoe(right, MovingFoeView.AnimationType.WALK_RIGHT);
			}
			
			//Attack the hero if it's within the range of this foe's weapon and if this foe is not too high above or too low below the hero.
			else if((this.model.getWeapon().isWithinRange(this.model.getBody(), this.playState.getHeroModel().getBody())) && 
					(Math.abs(foePos.y-heroPos.y) < (Utils.METER_IN_PIXELS))){
					
				if(heroPos.x < this.model.getPosPixels().x) {
					this.makeFoeFight(Navigation.WEST, MovingFoeView.AnimationType.WALK_LEFT);
				} else if(heroPos.x > this.model.getPosPixels().x) {
					this.makeFoeFight(Navigation.EAST, MovingFoeView.AnimationType.WALK_RIGHT);
				}
			} else {
				this.view.setCurrentAnim(MovingFoeView.AnimationType.STAND);
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
	
	public void moveFoe(Vec2 impulse, MovingFoeView.AnimationType animation) {
		this.view.setCurrentAnim(animation);
		this.model.getBody().applyLinearImpulse(impulse, this.model.getPosMeters());
	}
	
	public void makeFoeFight(Navigation navigation, MovingFoeView.AnimationType animation) {
		if(this.model.getWeapon().fight(this.model, navigation)){
			this.view.setCurrentAnim(animation);
		}
	}

	@Override
	public int getID() {
		return this.model.getID();
	}
}
