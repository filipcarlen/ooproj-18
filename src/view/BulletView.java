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

/** 
 * A view that draws a Bullet object
 * 
 * @author Project Group 18 (Chalmers, 2013)
 */

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
	
	/**
	 * The method for drawing
	 * @param gc, sbg, g
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		Vec2 temp = Camera.entityRender(model.getPosPixels());
		if(model.getNavigation() == Navigation.EAST){
			this.bulletR.draw(temp.x, temp.y);
		} else{
			this.bulletL.draw(temp.x, temp.y);
		}
	}
	/**
	 * @return ID of this view
	 */
	public int getID(){
		return this.id;
	}
}
