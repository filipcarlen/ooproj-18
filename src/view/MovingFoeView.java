package view;

import model.MovingFoeModel;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MovingFoeView {

	public static enum AnimationType {WALK_LEFT, WALK_RIGHT, GUN_LEFT, GUN_RIGHT, SWORD_LEFT, SWORD_RIGHT};
	
	private Animation[] animations;
	
	private final String PATH = "res/Characters/movingfoe/";
	
	private MovingFoeModel model;
	
	private Animation currentAnimation;
	
	public MovingFoeView(MovingFoeModel model) {
		this.model = model;
		try {
			initAnimations();
		} catch (SlickException e) {
			System.out.println("Couldn't initiate the animations of the moving foe at position: " + this.model.getPosPixels());
		}
	}
	
	public void initAnimations() throws SlickException {
		this.animations = new Animation[6];
		Image[] images = new Image[2];
		int duration = 500;
		
		images[0] = new Image(this.PATH + "walkleft1.jpg");
		images[1] = new Image(this.PATH + "walkleft2.jpg");
		animations[0] = (new Animation(images, duration));
			
		images[0] = new Image(this.PATH + "walkright1.jpg");
		images[1] = new Image(this.PATH + "walkright2.jpg");
		animations[1] = new Animation(images, duration);
			
		images[0] = new Image(this.PATH + "gunleft1.jpg");
		images[1] = new Image(this.PATH + "gunleft2.jpg");
		animations[2] = new Animation(images, duration);
			
		images[0] = new Image(this.PATH + "gunright1.jpg");
		images[1] = new Image(this.PATH + "gunright2.jpg");
		animations[3] = new Animation(images, duration);
		
		images[0] = new Image(this.PATH + "swordleft1.jpg");
		images[1] = new Image(this.PATH + "swordleft2.jpg");
		animations[4] = new Animation(images, duration);
			
		images[0] = new Image(this.PATH + "swordright1.jpg");
		images[1] = new Image(this.PATH + "swordright2.jpg");
		animations[5] = new Animation(images, duration);
		
		this.currentAnimation = this.animations[0];
	}
	
	public void setCurrentAnim(AnimationType type) {
		switch(type) {
		case WALK_LEFT:
			this.currentAnimation = this.animations[0];
			break;
		case WALK_RIGHT:
			this.currentAnimation = this.animations[1];
			break;
		case GUN_LEFT:
			this.currentAnimation = this.animations[2];
			break;
		case GUN_RIGHT:
			this.currentAnimation = this.animations[3];
			break;
		case SWORD_LEFT:
			this.currentAnimation = this.animations[4];
			break;
		case SWORD_RIGHT:
			this.currentAnimation = this.animations[5];
			break;
		default:
			break;
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.drawAnimation(this.currentAnimation, this.model.getPosPixels().x, this.model.getPosPixels().y);
	}
}
