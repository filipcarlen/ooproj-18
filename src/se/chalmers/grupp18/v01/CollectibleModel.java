package se.chalmers.grupp18.v01;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/** A class representing a collectible object
 * 
 * @author filipcarlen
 * @version 1.0 
 */



public class CollectibleModel {
	
	//private float x;
	//private float y;
	private Vec2 position;
	
	private Body body;
	private World world;
	
	public CollectibleModel(World w, Vec2 pos){
		world = w;
		this.position = pos;
		createCollectable();
		
	}
	
	/**
	 * Method for creating a collectable object
	 */
	public void createCollectable(){
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(position.x,position.y);
		
		//Circle shapes is perfect for collectable objects
		CircleShape cs = new CircleShape();
		cs.m_radius = 10.0f;
		
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.0f;
		fd.friction = 0.0f;
		fd.restitution = 0.3f;
		
		//creating body
		Body body = world.createBody(bd);
		body.createFixture(fd);
		
	}

	/**@Override
	public Vec2 getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Vec2 pos) {
		this.position = pos;
		
	}*/
	
	
}
