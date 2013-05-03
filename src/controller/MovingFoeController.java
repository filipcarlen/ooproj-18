package controller;

import model.MovingFoeModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

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
		
		
		
		// Somehow get a hold of the players coordinates...
		/*Vec2 heroPos = PlayState.getHeroPos();
		Vec2 diffVec = this.model.getPosition().sub(heroPos);
		
		if(diffVec.length() < 600)){
			if(heroPos.x < this.body.getPosition().x){
				this.body.getPosition().x -= 0.4f*delta;
				if(this.weapon.isWithinRange(this.body.getPosition(), heroPos)){
					this.weapon.fight(heroPos);
				}
			}
			if(heroPos.x > this.body.getPosition().x){
				this.body.getPosition().x += 0.4f*delta;
				if(this.weapon.isWithinRange(this.body.getPosition(), heroPos)){
					this.weapon.fight(heroPos);
				}
			}
		}*/
	}

	

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		this.view.render(gc, sbg, g);
	}
}
