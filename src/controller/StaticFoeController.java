package controller;

import model.StaticFoeModel;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import view.StaticFoeView;

public class StaticFoeController implements IEntityController {
	
	private StaticFoeView view;
	
	public StaticFoeController(StaticFoeModel model, StaticFoeView.StaticFoeType type){
		this.view = new StaticFoeView(model, type);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		view.render(container, game, g);
	}

}
