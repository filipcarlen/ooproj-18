package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


import utils.Navigation;
import utils.Utils;

/** A class representing a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class BulletModel implements IEntityModel{

	/** The range of how far this Bullet can reach before disappearing */
	private float range;
	
	/** The amount of damage this Bullet makes when hitting a target */
	private int damage;
	
	/** The body of this Bullet object */
	private Body bulletBody;
	
	/** The world in which this body exists */
	private World world;
	
	/** The first position of the Bullet, from where it leaves the Gun */
	private Vec2 firstPos;	
	
	/** The position of the character firing the Gun */
	private Body fighterBody;
	
	/** The direction in which the fighter is moving */
 	private Navigation navigation;
 	
 	
 	private boolean isMoving;
 	
 	/** The ID of this Bullet */
 	private int id;

	/** The radius of the circle shaped body */
	public final float RADIUS = 10f;

	
	public BulletModel(World world, float range, int damage, int id){
		this.id = id;
		this.range = range;
		this.damage = damage;
		this.world = world;
		//this.firstPos = fighterPos;
		//this.navigation = navigation;
		//this.fighterBody = AbstractWeaponModel.getFighterBody(this.world, fighterPos);
		//init(fighterPos);
		//this.bulletBody.setActive(false);
		
	}
	/**
	 * A method for initializing the body of this object
	 * @param fighterPos the position of the character firing the gun
	 * @throws NullPointerException when there is no body to find in the world on the given fighterPos
	 */
	public void init(Vec2 fighterPos) throws NullPointerException{
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		//bd.position.set(Utils.pixelsToMeters(fighterPos.x - RADIUS), Utils.pixelsToMeters(fighterPos.y - RADIUS));
		
		CircleShape cs = new CircleShape();
		cs.m_radius = Utils.pixelsToMeters(RADIUS);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.5f;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;
		
		this.bulletBody = this.world.createBody(bd);
		this.bulletBody.createFixture(fd);
		this.bulletBody.setUserData(this);
		
		
		// Making the Bullet object a bullet in JBox2D, then the Bullet will disappear when it collides with another body.
		this.bulletBody.setBullet(true);
		// This is done so that the Bullet will ignore collision with the shooting character.
		this.bulletBody.shouldCollide(this.fighterBody);
		
	}
	
	public void fight(Vec2 fighterPos, Navigation navigation){
		this.firstPos = fighterPos;
		isMoving = true;
		init(fighterPos);
	}
	
	public void setMoving(boolean b){
		this.isMoving = b;
	}
	public boolean isMoving(){
		return this.isMoving;
	}
	/**
	 * A method used to destroy the body of this bullet
	 */
	public void destroyEntity(){
		this.world.destroyBody(bulletBody);
	}
	/**
	 * 
	 * @return the range of this bullet
	 */
	public float getRange(){
		return this.range;
	}
	/**
	 * Sets the range of this bullet
	 * @param range
	 */
	public void setRange(int range){
		this.range = range;
	}
	/**
	 * 
	 * @return the damage made by this bullet
	 */
	public int getDamage(){
		return this.damage;
	}
	/**
	 *  Sets the damage of this bullet
	 * @param damage
	 */
	public void setDamage(int damage){
		this.damage = damage;
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
	/**
	 * 
	 * @return the body of the fighter (the one firing the gun)
	 */
	public Body getFighterBody(){
		return this.fighterBody;
	}
	
	@Override
	public Vec2 getPosMeters() {
		return this.bulletBody.getPosition();
	}
	
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.bulletBody.getPosition().add(new Vec2(-RADIUS, -RADIUS)));
	}
	
	@Override
	public Body getBody() {
		return this.bulletBody;
	}
	
	@Override
	public int getID() {
		return this.id;
	}
		
}
