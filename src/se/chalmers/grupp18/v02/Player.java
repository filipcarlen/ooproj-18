package se.chalmers.grupp18.v02;
import it.marteEngine.entity.PlatformerEntity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Player extends PlatformerEntity{
	
	public Player(float x, float y) throws SlickException {
		super(x,y, 50, 50);
		
		
		PlayerAnimation pAnimation = new PlayerAnimation("Hero");
		addAnimation("Left", pAnimation.ListOfAnimation().get(0));
		addAnimation("Right", pAnimation.ListOfAnimation().get(1));
		addAnimation("Jump", pAnimation.ListOfAnimation().get(4));
		
		setHitBox(0,0,pAnimation.getWidth(), pAnimation.getHeight());
		addType("PLAYER");
		
		bindToKey("RIGHT", Input.KEY_RIGHT);
		bindToKey("LEFT", Input.KEY_LEFT);
		bindToKey("JUMP", Input.KEY_UP);
		
		setAnim("Right");
	}
	
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		
		super.update(container, delta);
		
		if(check("LEFT") && !check("RIGHT")){
			setAnim("Left");
			if(collide(SOLID,x - 5, y) == null){
				x -= 0.2f*delta;
			}
		} else if(check("RIGHT") && !check("LEFT")){
			setAnim("Right");
			if(collide(SOLID,x + 5, y) == null){
				x += 0.2f*delta;
			}
		}
		
		if(check("JUMP")){
			jump();
			setAnim("Jump");
		}
		
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		super.render(container, g);
	}

}
