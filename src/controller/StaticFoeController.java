package controller;

import model.AbstractStaticFoe;
import model.StaticFoeFire;
import model.StaticFoePlant;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import view.StaticFoeFireView;
import view.IStaticFoeView;
import view.StaticFoePlantView;

public class StaticFoeController implements IEntityController {
	
	private IStaticFoeView view;
	private AbstractStaticFoe model;
	
	public StaticFoeController(AbstractStaticFoe model){
		this.model = model;
		
		if(this.model instanceof StaticFoeFire) {
			this.view = new StaticFoeFireView((StaticFoeFire)this.model);
		} else if(this.model instanceof StaticFoePlant) {
			this.view = new StaticFoePlantView((StaticFoePlant)this.model);
		}
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		this.view.render(container, game, g);
	}

	@Override
	public int getID() {
		return this.model.getID();
	}
}
