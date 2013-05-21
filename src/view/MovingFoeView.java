package view;

import model.MovingFoeModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;
import utils.Utils;

public class MovingFoeView {

	public static enum AnimationType {STAND, WALK_LEFT, WALK_RIGHT, GUN_LEFT, GUN_RIGHT, SWORD_LEFT, SWORD_RIGHT};
	
	private Animation[] animations;
	
	private final String PATH_LEFT = "res/foe/moving_foe/foe_left/";
	private final String PATH_RIGHT = "res/foe/moving_foe/foe_right/";
	private final String PATH_STAND = "res/foe/moving_foe/foe_standing/";
	
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
		this.animations = new Animation[7];
		
		Image[] images = new Image[1];
		int duration = 100;
		
		images[0] = new Image(this.PATH_STAND + "foe_standing.png");
		animations[0] = new Animation(images, duration);
		
		images = new Image[12];
		
		for(int i = 0; i < images.length; i++){
			images[i] = new Image(PATH_LEFT + "foe_left_" + (i+1) + ".png");
		}
		animations[1] = (new Animation(images, duration));
		animations[3] = (new Animation(images, duration));
		animations[5] = (new Animation(images, duration));
		
		for(int i = 0; i < images.length; i++){
			images[i] = new Image(PATH_RIGHT + "foe_right_" + (i+1) + ".png");
		}
		animations[2] = (new Animation(images, duration));
		animations[4] = (new Animation(images, duration));
		animations[6] = (new Animation(images, duration));
		
		this.currentAnimation = this.animations[0];
	}
	
	public void setCurrentAnim(AnimationType type) {
		switch(type) {
		case STAND:
			this.currentAnimation = this.animations[0];
			break;
		case WALK_LEFT:
			this.currentAnimation = this.animations[1];
			break;
		case WALK_RIGHT:
			this.currentAnimation = this.animations[2];
			break;
		case GUN_LEFT:
			this.currentAnimation = this.animations[3];
			break;
		case GUN_RIGHT:
			this.currentAnimation = this.animations[4];
			break;
		case SWORD_LEFT:
			this.currentAnimation = this.animations[5];
			break;
		case SWORD_RIGHT:
			this.currentAnimation = this.animations[6];
			break;
		default:
			break;
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		Vec2 worldPos = Camera.entityRender(this.model.getPosPixels());
		g.drawAnimation(this.currentAnimation, worldPos.x, worldPos.y);
		
		Vec2 lifePos = worldPos.sub(new Vec2(0,8));
		g.setColor(Color.red);
		float life = ((float)(this.model.getHp())/(float)(this.model.getMaxHp()))*Utils.metersToPixels(this.model.getWidth());
		g.fillRect(lifePos.x, lifePos.y, life, 5);
	}
}
