package se.chalmers.grupp18.v02;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import it.marteEngine.entity.Entity;


public class Wall extends Entity{
	
	private Image img;
	
	public Wall(float x, float y) throws SlickException {
		super(x, y);
		
		// load Image from disk and associate it as wall image
		img = new Image("res/testwall.jpg");
		setGraphic(img);
		
		// define basic collision info
		// hitbox is a rectangle around entity, in this case it is exactly the size of the wall image
		setHitBox(0, 0, img.getWidth(), img.getHeight());
		// declare type of this entity
		addType(SOLID);		
		setCentered(true);
	}
	
	@Override
	public void collisionResponse(Entity other) {
		// called when colliding with another entity
	}
}
