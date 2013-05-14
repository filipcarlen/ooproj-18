package view;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;

import model.BulletModel;

public class BulletView {

	private BulletModel model;
	private int id;
	
	public BulletView(BulletModel model){
		this.model = model;
		this.id = model.getID();
	}
	
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		Vec2 temp = Camera.entityRender(model.getPosPixels());
		g.drawOval(temp.x, temp.y, model.RADIUS*2, model.RADIUS*2);
	}
	
	public int getID(){
		return this.id;
	}
}
