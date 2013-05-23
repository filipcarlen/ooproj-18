package model;




import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.Utils;

/** An abstract class representing a collectible object
 * 
 * @author filipcarlen
 * @version 1.0 
 */

public abstract class AbstractCollectibleModel implements ICollectibleModel {
	
	/** The body for a collectible item */
	private Body body;
	
	/** A boolean that tells us if the body exists */
	private boolean bodyExists;
	
	/** ID of a collectible object*/
	private int id;
	
	/** The fixture of a body */
	private FixtureDef fixturedef;
	
	private float width;
	
	private float height;
	
	
	/**
	 * Constructor for creating a Collectible item 
	 * @param World w
	 * @param Vec2 pixelPos(position)
	 */
	 
	public AbstractCollectibleModel(World w, Vec2 pixelPos, int id, float width, float height){
		this.width = width;
		this.height = height;
		createCollectible(Utils.pixelsToMeters(new Vec2(pixelPos.x+this.width/2,pixelPos.y+this.height/2)), w);
		this.id = id;
	}
	
	/**
	 * Method for creating a collectible object
	 * @param Vec2 position in meters
	 */
	public void createCollectible(Vec2 meterPos, World world){
	   	 
	   	 BodyDef bodydef = new BodyDef();
	   	 bodydef.type = BodyType.DYNAMIC;
	   	 bodydef.gravityScale = 0f;
	   	 bodydef.position.set(meterPos.x,meterPos.y);
		 body = world.createBody(bodydef);
		 
	   	 fixturedef = new FixtureDef();
	   	 fixturedef.density = 0.0001f;
	   	 fixturedef.friction = 0.0f;
	   	 fixturedef.restitution = 0.0f;
	   	 
	   	 
	   	 fixturedef.filter.groupIndex = -1;
	   	 fixturedef.filter.maskBits = 555;
	   	 fixturedef.filter.categoryBits = 4;
	   	 
	   	 body.setUserData(this);
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
    public void destroyBody(){
      	 body.getWorld().destroyBody(body);
    } 	 
	
	@Override
    public void killBody(){
      	 bodyExists = false;
    }
    
	@Override
	public boolean bodyExists(){
		return this.bodyExists;
	}
	
	@Override
	public int getID(){
		return id;
	}
	
	/**
	 * Get method for the bodies fixture
	 * @return FixtureDef fixturedef
	 */
	public FixtureDef getFixtureDef(){
		return this.fixturedef;
	}
	
	@Override
	public float getHeight() {
		return this.width;
	}

	@Override
	public float getWidth() {
		return this.height;
	}
	
	public void setWidth(float width){
		this.width = width;
	}
	
	public void setHeight(float height){
		this.height = height;
	}
}
