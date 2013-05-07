package view;


import org.newdawn.slick.Graphics;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import model.BulletModel;

public class BulletView {

	private BulletModel model;
	
	public BulletView(BulletModel model){
		this.model = model;
	}
	
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		g.drawOval(model.getPosPixels().x, model.getPosPixels().y, model.RADIUS*2, model.RADIUS*2);
	}
}
