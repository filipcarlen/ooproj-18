package view;

import model.StaticFoeModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;

import controller.StaticFoeController;

public class StaticFoeView {

	public static enum StaticFoeType {FIRE, PLANT, WATER, SPIKES};
	
	private StaticFoeModel model;
	private Animation animation;
	
	private final String PATH = "res/Characters/staticfoe/";
	
	public StaticFoeView(StaticFoeModel model, StaticFoeType type){
		this.model = model;
		try {
			initAnimation(type);
		} catch (SlickException e) {
			System.out.println("Couldn't initiate the animation of the static foe at position: " + this.model.getPosPixels());
		}
	}
	
	/**
	 * Initiates an animation based on what type of static foe was specified.
	 * @param type
	 * @throws SlickException
	 */
	public void initAnimation(StaticFoeType type) throws SlickException{
		Image[] images = new Image[2];
		int duration = 500;
		
		switch(type) {
		case FIRE:
			images[0] = new Image(this.PATH + "fire1.jpg");
			images[1] = new Image(this.PATH + "fire2.jpg");
			animation = new Animation(images, duration);
			break;
		case PLANT:
			images[0] = new Image(this.PATH + "plant1.jpg");
			images[1] = new Image(this.PATH + "plant2.jpg");
			animation = new Animation(images, duration);
			break;
		case WATER:
			images[0] = new Image(this.PATH + "water1.jpg");
			images[1] = new Image(this.PATH + "water2.jpg");
			animation = new Animation(images, duration);
			break;
		case SPIKES:
			images[0] = new Image(this.PATH + "spikes1.jpg");
			images[1] = new Image(this.PATH + "spikes2.jpg");
			animation = new Animation(images, duration);
			break;
		default:
			break;
		}
	}
	
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) {
		Vec2 worldPos = Camera.entityRender(this.model.getPosPixels());
		g.drawAnimation(animation, worldPos.x, worldPos.y);
	}
	
}
