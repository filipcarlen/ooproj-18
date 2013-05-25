package view;



import java.util.Random;

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
	
	private final String PLANT1_PATH = "res/foe/static_foe/plant_animation/plant1/";
	private final String PLANT2_PATH = "res/foe/static_foe/plant_animation/plant2/";
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
		int duration = 400;
		String path = "";
		
		Random r = new Random();
		int random = r.nextInt(2);
		if(random == 0) {
			path = this.PLANT1_PATH + "plant1_";
		} else {
			path = this.PLANT2_PATH + "plant2_";
		}
		
		for(int i = 0; i < images.length; i++) {
			images[i] = new Image(path + (i+1) + ".png");
		}
		this.plant = new Animation(images, duration);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		Vec2 cameraPos = Camera.entityRender(this.model.getPosPixels());
		this.plant.draw(cameraPos.x, cameraPos.y);
	}

}
