package controller;

import model.SwordModel;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class SwordController implements IEntityController{

	private SwordModel model;
	
	
	public SwordController(SwordModel model){
		this.model = model;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
		
	}

}
