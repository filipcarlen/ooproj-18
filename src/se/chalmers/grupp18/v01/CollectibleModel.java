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
	
	private Vec2 position;
	
	private Body body;
	private World world;
	public final float RADIUS = 10.0f;
	
	public CollectibleModel(World w, Vec2 pos){
		world = w;
		this.position = pos;
		createCollectable();		
	}
	
	/**
	 * Method for creating a collectible object
	 */
	public void createCollectable(){
		BodyDef bodydef = new BodyDef();
		bodydef.type = BodyType.DYNAMIC;
		bodydef.position.set(position.x,position.y);
		
		//Circle shapes are perfect for collectible objects
		CircleShape circleshape = new CircleShape();
		circleshape.m_radius = RADIUS;
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = circleshape;
		fixturedef.density = 0.0f;
		fixturedef.friction = 0.0f;
		fixturedef.restitution = 0.3f;
		
		//creating body
		Body body = world.createBody(bodydef);
		body.createFixture(fixturedef);	
	}
	
	/**
	 * 
	 * @return position
	 */
	public Vec2 getPosition() {
		return this.position;
	}
	
	/**
	 * 
	 * @param pos
	 */
	
	public void setPosition(Vec2 pos) {
		this.position = pos;
	}	
	
	/**
	 * 
	 * @return body
	 */
	public Body getBody(){
		return this.body;
	}
	
	/**
	 * 
	 * @param body
	 */
	public void setBody(Body body){
		this.body = body;
	}
		
}
