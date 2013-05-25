package view;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;
import utils.Navigation;

import model.Bullet;

public class BulletView {

	private Bullet model;
	private int id;
	private Image bulletR;
	private Image bulletL;
	
	public BulletView(Bullet model){
		this.model = model;
		this.id = model.getID();
		try{
			this.bulletR = new Image("res/bullet/bulletR.png");
			this.bulletL = new Image("res/bullet/bulletL.png");
		} catch(SlickException e){}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		Vec2 temp = Camera.entityRender(model.getPosPixels());
		if(model.getNavigation() == Navigation.EAST){
			this.bulletR.draw(temp.x, temp.y);
		} else{
			this.bulletL.draw(temp.x, temp.y);
		}
	}
	
	public int getID(){
		return this.id;
	}
}
