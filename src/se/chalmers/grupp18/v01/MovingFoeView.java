package se.chalmers.grupp18.v01;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MovingFoeView {

	public static enum ImageType {WALK_LEFT, WALK_RIGHT, FIGHT_LEFT, FIGHT_RIGHT};
	
	private Image[] images;
	
	private String PATH_WL = "";
	private String PATH_WR = "";
	private String PATH_FL = "";
	private String PATH_FR = "";
	
	private MovingFoeModel model;
	
	private Image currentImage;
	
	public MovingFoeView(MovingFoeModel model) {
		this.model = model;
		init();
	}
	
	public void init() {
		images = new Image[4];
		try {
			images[0] = new Image(PATH_WL);
			images[1] = new Image(PATH_WR);
			images[2] = new Image(PATH_FL);
			images[3] = new Image(PATH_FR);
		} catch (SlickException e) {
			System.out.println(e.getMessage());
		}
		
		currentImage = images[0];
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
	}
	
	public void setCurrentImage(ImageType type) {
		switch(type) {
		case WALK_LEFT:
			break;
		case WALK_RIGHT:
			break;
		case FIGHT_LEFT:
			break;
		case FIGHT_RIGHT:
			break;
		default:
			break;
		}
	}
}
