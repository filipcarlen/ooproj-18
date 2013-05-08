package model;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.Utils;

/** A class representing a collectible object
 * 
 * @author filipcarlen
 * @version 1.0 
 */



public abstract class AbstractCollectibleModel implements IEntityModel, ICollectibleModel {
	
	/** The body for a collectible item */
	private Body body;
	
	/** The world the body exists in */
	private World world;
	
	/** The collectible items Radius in meters */
	public final float RADIUS = .5f;
	
	/** Density */
	private float density = 0.7f;
	
	/** Friction */
	private float friction = 0.0f;
	
	/** Restitution */
	private float Restitution = 0.5f;
	
	/** A boolean that tells us if the body exists */
	private boolean bodyExists;
	
	private FixtureDef fixturedef;
	
	
	/**
	 * Constructor for creating a Collectible item 
	 * @param World w
	 * @param Position pixelPos
	 */
	 
	public AbstractCollectibleModel(World w, Vec2 pixelPos){
		world = w;
		bodyExists = true;
		createCollectable(Utils.pixelsToMeters(pixelPos));		
	}
	
	/**
	 * Method for creating a collectible object
	 * @param Vec2 position in meters
	 */
	public void createCollectable(Vec2 meterPos){
		BodyDef bodydef = new BodyDef();
		bodydef.type = BodyType.STATIC;
		bodydef.position.set(meterPos.x,meterPos.y);
		
		//Circle shapes are perfect for collectible objects
		CircleShape circleshape = new CircleShape();
		circleshape.m_radius = RADIUS;
		
		fixturedef = new FixtureDef();
		fixturedef.shape = circleshape;
		fixturedef.density = 0.7f;
		fixturedef.friction = 0.0f;
		fixturedef.restitution = 0.5f;
		
		//creating body
		body = world.createBody(bodydef);
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
	
	/**
	 * 
	 * @return radius in pixels
	 */
	public float getRadius(){
		return this.RADIUS * Utils.METER_IN_PIXELS;
	}
	
	public void killBody(){
		world.destroyBody(body);
		bodyExists = false;
	}
	
	public boolean bodyExists(){
		return this.bodyExists;
	}
}
