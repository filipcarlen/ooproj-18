package model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import utils.Navigation;
import utils.Utils;

/** A class representing a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class Bullet implements IEntity{

	private Gun gunModel;
	private Body bulletBody;
	
	/** The first position of the Bullet in meters */
	private Vec2 firstPos;
	private Navigation navigation;
 	
 	private boolean isMoving = false;
 	private boolean isAlive = false;
 	
 	private int ID;

	/** The width of the body in pixels */
	public final float WIDTH = 22f;
	/** The height of the body in pixels */
	public final float HEIGHT = 7f;

	public Bullet(Gun gunModel, Vec2 firstPos, Navigation navigation, int ID){
		this.ID = ID;
		this.gunModel = gunModel;
		this.isAlive = true;
		this.navigation = navigation;
		init(firstPos);
	}
	
	/**
	 * A method for initializing the body of this object
	 * @param fighterPos the position of the character firing the gun
	 * @throws NullPointerException when there is no body to find in the world on the given fighterPos
	 */
	public void init(Vec2 firstPos) throws NullPointerException{
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.gravityScale = 0;
		this.firstPos = firstPos;
		
		if(this.navigation == Navigation.EAST){
			this.firstPos.x += Utils.pixelsToMeters(this.WIDTH) + 0.1f;
			bd.position.set(firstPos.x, firstPos.y);
			
		} else if(this.navigation == Navigation.WEST){
			this.firstPos.x -= Utils.pixelsToMeters(this.WIDTH) + 0.1f;
			bd.position.set(firstPos.x, firstPos.y);
		}
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(Utils.pixelsToMeters(this.WIDTH)/2, Utils.pixelsToMeters(this.HEIGHT)/2);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.15f;
		fd.friction = 0.0f;
		fd.restitution = 0.5f;
		fd.filter.maskBits = 555;
		fd.filter.categoryBits = 4;
		
		if(gunModel.getFighterModel() instanceof MovingFoe){
			fd.filter.groupIndex = gunModel.getFighterModel().getBody().getFixtureList().getFilterData().groupIndex;
		}
		
		this.bulletBody = this.gunModel.getWorld().createBody(bd);
		this.bulletBody.createFixture(fd);
		this.bulletBody.setUserData(this);		
	}
	
	public void destroyEntity(){
		this.isMoving = false;
		this.isAlive = false;
	}
	
	public void setMoving(boolean b){
		this.isMoving = b;
	}
	
	public boolean isMoving(){
		return this.isMoving;
	}
	
	public boolean isAlive(){
		return this.isAlive;
	}
	
	/**
	 * 
	 * @return the range of this bullet
	 */
	public float getRange(){
		return this.gunModel.getRange();
	}
	/**
	 * 
	 * @return the damage made by this bullet
	 */
	public int getDamage(){
		return this.gunModel.getDamage();
	}
	/**
	 * 
	 * @return the first position of the bullet
	 */
	public Vec2 getFirstPos(){
		return this.firstPos;
	}
	/**
	 * 
	 * @return the position given on the target
	 */
	public Navigation getNavigation(){
		return this.navigation;
	}

	@Override
	public Vec2 getPosMeters() {
		return this.bulletBody.getPosition();
	}
	
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.bulletBody.getPosition()).sub(new Vec2(this.WIDTH/2, this.HEIGHT/2));
	}
	
	@Override
	public Body getBody() {
		return this.bulletBody;
	}
	
	@Override
	public int getID() {
		return this.ID;
	}
	@Override
	public float getHeight() {
		return Utils.pixelsToMeters(this.HEIGHT);
	}
	@Override
	public float getWidth() {
		return Utils.pixelsToMeters(this.WIDTH);
	}
		
}
