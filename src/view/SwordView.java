package view;

import model.SwordModel;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;
import utils.Utils;

public class SwordView {

	private SwordModel model;
	private int id;
	
	public SwordView(SwordModel model){
		this.model = model;
		this.id = model.getID();
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		Vec2 temp = Camera.entityRender(model.getPosPixels());
		g.setColor(Color.white);
		g.drawOval(temp.x, temp.y, Utils.metersToPixels(model.RADIUS*2), Utils.metersToPixels(model.RADIUS*2));
	}
	
	public int getID(){
		return this.id;
	}
}
