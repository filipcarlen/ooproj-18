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
	
	/** The collectible items Radius in meters */
	public final float RADIUS = .5f;
	
	/** A boolean that tells us if the body exists */
	private boolean bodyExists;
	
	int id;
	
	
	/**
	 * Constructor for creating a Collectible item 
	 * @param World w
	 * @param Position pixelPos
	 */
	 
	public AbstractCollectibleModel(World w, Vec2 pixelPos, int id){
		createCollectable(Utils.pixelsToMeters(pixelPos), w);
		this.id = id;
	}
	
	/**
	 * Method for creating a collectible object
	 * @param Vec2 position in meters
	 */
	public void createCollectable(Vec2 meterPos, World world){
	   	 
	   	 BodyDef bodydef = new BodyDef();
	   	 bodydef.type = BodyType.DYNAMIC;
	   	 bodydef.gravityScale = 0f;
	   	 bodydef.position.set(meterPos.x,meterPos.y);
	   	 
	   	 //Circle shapes are perfect for collectible objects
	   	 CircleShape circleshape = new CircleShape();
	   	 circleshape.m_radius = RADIUS;
	   	 
	   	 FixtureDef fixturedef = new FixtureDef();
	   	 fixturedef = new FixtureDef();
	   	 fixturedef.shape = circleshape;
	   	 fixturedef.density = 0.1f;
	   	 fixturedef.friction = 0.0f;
	   	 fixturedef.restitution = 0.0f;
		
		//creating body
		body = world.createBody(bodydef);
		body.setUserData(this);
		body.createFixture(fixturedef);	
		bodyExists = true;
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
	
	
    public void destroyBody(){
      	 body.getWorld().destroyBody(body);
    } 	 
	
	
    public void killBody(){
      	 bodyExists = false;
    }
    
	public boolean bodyExists(){
		return this.bodyExists;
	}
	
	public int getID(){
		return id;
	}
	
}
