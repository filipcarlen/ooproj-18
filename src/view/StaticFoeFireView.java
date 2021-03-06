package view;



import model.StaticFoeFire;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;

public class StaticFoeFireView implements IStaticFoeView {
	
	private final String PATH = "res/foe/static_foe/fire_animation/";
	private Animation fire;
	private StaticFoeFire model;
	
	public StaticFoeFireView(StaticFoeFire fire) {
		this.model = fire;
		try {
			initAnimation();
		} catch (SlickException e) {
			System.out.println("Couldn't initiate the fire animation in FireView.");
		}
	}
	
	public void initAnimation() throws SlickException {
		Image[] images = new Image[5];
		int duration = 100;
		
		for(int i = 0; i < images.length; i++) {
			images[i] = new Image(this.PATH + "fire_" + (i+1) + ".png");
		}
		this.fire = new Animation(images, duration);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		Vec2 cameraPos = Camera.entityRender(this.model.getPosPixels());
		this.fire.draw(cameraPos.x, cameraPos.y);
	}

}
