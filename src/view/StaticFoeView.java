package view;

import model.StaticFoeModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;

public class StaticFoeView {
	
	private StaticFoeModel model;
	private Animation animation;
	
	private final String PATH_FIRE = "res/foe/static_foe/fire_animation/";
	private final String PATH_PLANT = "res/foe/static_foe/plant_animation/";
	
	public StaticFoeView(StaticFoeModel model){
		this.model = model;
		try {
			init();
		} catch (SlickException e) {
			System.out.println("Couldn't initiate the animation of the static foe at position: " + this.model.getPosPixels());
		}
	}
	
	/**
	 * Initiates an animation based on what type of static foe was specified.
	 * @param type
	 * @throws SlickException
	 */
	public void init() throws SlickException{
		Image[] images = new Image[5];
		int duration = 100;
		
		switch(this.model.getType()) {
		case FIRE:
			for(int i = 0; i < images.length; i++) {
				images[i] = new Image(PATH_FIRE + "fire_" + (i+1) + ".png");
			}
			this.animation = new Animation(images, duration);
			break;
		case PLANT:
			for(int i = 0; i < images.length; i++) {
				images[i] = new Image(PATH_PLANT + "plant_" + (i+1) + ".png");
			}
			this.animation = new Animation(images, duration);
			break;
		default:
			break;
		}
	}
	
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) {
		Vec2 worldPos = Camera.entityRender(this.model.getPosPixels());
		this.animation.draw(worldPos.x, worldPos.y);
	}
	
}
