package view;



import model.StaticFoePlant;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;

public class StaticFoePlantView implements IStaticFoeView {
	
	private final String PATH = "res/foe/static_foe/plant_animation/";
	private Animation plant;
	private StaticFoePlant model;
	
	public StaticFoePlantView(StaticFoePlant plant) {
		this.model = plant;
		try {
			this.initAnimation();
		} catch (SlickException e) {
			System.out.println("Couldn't initiate the plant animation in PlantView.");
		}
	}
	
	public void initAnimation() throws SlickException {
		Image[] images = new Image[5];
		int duration = 100;
		
		for(int i = 0; i < images.length; i++) {
			images[i] = new Image(this.PATH + "plant1_" + (i+1) + ".png");
		}
		this.plant = new Animation(images, duration);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		Vec2 cameraPos = Camera.entityRender(this.model.getPosPixels());
		this.plant.draw(cameraPos.x, cameraPos.y);
	}

}
