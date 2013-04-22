package se.chalmers.grupp18.v01;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

/** A class representing a collectible coin. The coin has a value (points)
 * 
 * @author filipcarlen
 * @version 1.0 
 */



public abstract class Collectable extends Body implements IEntityController  {
	
	public Collectable(BodyDef bd, World world) {
		super(bd, world);
		// TODO Auto-generated constructor stub
	}

	private Vec2 position;
	private Image image;
	
	@Override
	public void setPosition(Vec2 pos) {
		// TODO Auto-generated method stub
		
	}
}
