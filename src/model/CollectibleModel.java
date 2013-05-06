package model;

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



public class CollectibleModel implements IEntityModel {
	
	/** The body for a collectible item */
	private Body body;
	
	/** The world the body exists in */
	private World world;
	
	/** The collectible items Radius in meters */
	public final float RADIUS = .5f;
	
	/** What value a collectible item holds (which points you get) */
	private int value = 1;
	
	
	/**
	 * Constructor for creating a Collectible item 
	 * @param World w
	 * @param Position pixelPos
	 */
	 
	public CollectibleModel(World w, Vec2 pixelPos){
		world = w;
		createCollectable(Utils.pixelsToMeters(pixelPos));		
	}
	
	/**
	 * Method for creating a collectible object
	 * @param Vec2 position in meters
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
	
	/**
	 * 
	 * @return the value of a collectible item
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * 
	 * @param set a value
	 */
	public void setValue(int value){
		this.value = value;
	}
	
	public boolean bodyExists(){
		if(this.body.isAwake()){
			return true;
		}
		return false;
	}
		
}
