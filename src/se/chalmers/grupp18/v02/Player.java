package se.chalmers.grupp18.v02;
import it.marteEngine.entity.PlatformerEntity;
import it.marteEngine.tween.Tweener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Player extends PlatformerEntity{
	
	public Player(float x, float y) throws SlickException {
		super(x,y,40,48);
		Image image = new Image("res/toadette.png");
		setGraphic(image);
		
		setHitBox(0,0,image.getWidth(), image.getHeight());
		addType("PLAYER");
		
		bindToKey("RIGHT", Input.KEY_RIGHT);
		bindToKey("LEFT", Input.KEY_LEFT);
		bindToKey("JUMP", Input.KEY_UP);
	}
	
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		super.update(container, delta);
		
		if(check("LEFT") && !check("RIGHT")){
			if(collide(SOLID,x - 5, y) == null){
				x -= 0.2f*delta;
			}
		} else if(check("RIGHT") && !check("LEFT")){
			if(collide(SOLID,x + 5, y) == null){
				x += 0.2f*delta;
			}
		}
		
		if(check("JUMP")){
			jump();
		}
		
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
	}

}
