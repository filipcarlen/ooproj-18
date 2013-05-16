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
	public void update(GameContainer container, StateBasedGame game, int delta) {
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
				
			} else if((heroPos.x > foePos.x) && 
					!(this.model.getWeapon().isWithinRange(this.model.getPosMeters(), playState.getHeroModel().getPosMeters()))){
				
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_RIGHT);
				this.model.getBody().applyLinearImpulse(right, this.model.getPosMeters());
				
			} else if((heroPos.x < foePos.x) && (Math.abs(foePos.y-heroPos.y)>=(Utils.METER_IN_PIXELS/2))) {
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_LEFT);
				this.model.getBody().applyLinearImpulse(left, this.model.getPosMeters());
				
			} else if((heroPos.x > foePos.x) && (Math.abs(foePos.y-heroPos.y)>=(Utils.METER_IN_PIXELS/2))) {
				this.view.setCurrentAnim(MovingFoeView.AnimationType.WALK_RIGHT);
				this.model.getBody().applyLinearImpulse(right, this.model.getPosMeters());
				
			}
			
			//Attack the hero if he/she is within the range of this foe's weapon.
			if((this.model.getWeapon().isWithinRange(this.model.getPosMeters(), this.playState.getHeroModel().getPosMeters())) && 
					(Math.abs(foePos.y-heroPos.y)<(Utils.METER_IN_PIXELS/2))){
					
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
