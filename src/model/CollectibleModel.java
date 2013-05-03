package model;

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

import utils.Utils;

/** A class representing a collectible object
 * 
 * @author filipcarlen
 * @version 1.0 
 */



public class CollectibleModel implements IEntityModel {
	
	
	private Body body;
	private World world;
	
	/** Radius in meters */
	public final float RADIUS = .5f;
	
	private int value = 1;
	
	public CollectibleModel(World w, Vec2 pixelPos){
		world = w;
		createCollectable(Utils.pixelsToMeters(pixelPos));		
	}
	
	/**
	 * Method for creating a collectible object
	 */
	public void createCollectable(Vec2 meterPos){
		BodyDef bodydef = new BodyDef();
		bodydef.type = BodyType.DYNAMIC;
		bodydef.position.set(meterPos.x,meterPos.y);
		
		//Circle shapes are perfect for collectible objects
		CircleShape circleshape = new CircleShape();
		circleshape.m_radius = RADIUS;
		
		FixtureDef fixturedef = new FixtureDef();
		fixturedef.shape = circleshape;
		fixturedef.density = 0.5f;
		fixturedef.friction = 0.3f;
		fixturedef.restitution = 0.2f;
		
		//creating body
		Body body = world.createBody(bodydef);
		body.setUserData(this);
		body.createFixture(fixturedef);	
	}	
	
	/**
	 * 
	 * @return body
	 */
	public Body getBody(){
		return this.body;
	}

	@Override
	public Vec2 getPosMeters() {
		return body.getPosition();
	}

	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(body.getPosition().add(new Vec2(-RADIUS,-RADIUS)));
	}
	
	public float getRadius(){
		return this.RADIUS;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int value){
		this.value = value;
	}
		
}
