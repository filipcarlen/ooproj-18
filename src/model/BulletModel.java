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
	
	
	private IAliveModel fighterModel;
	
	/** The position of the character firing the Gun */
	private Body fighterBody;
	
	/** The direction in which the fighter is moving */
 	private Navigation navigation;
 	
 	
 	private boolean isMoving = false;
 	
 	
 	private boolean isAlive;
 	
 	/** The ID of this Bullet */
 	private int id;

	/** The radius of the circle shaped body */
	public final float RADIUS = 5f;

	
	public BulletModel(World world, float range, int damage, int id){
		this.id = id;
		this.range = range;
		this.damage = damage;
		this.world = world;
		
	}
	/**
	 * A method for initializing the body of this object
	 * @param fighterPos the position of the character firing the gun
	 * @throws NullPointerException when there is no body to find in the world on the given fighterPos
	 */
	public void init(Vec2 fighterPos) throws NullPointerException{
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.gravityScale = 0;
		if(this.navigation == Navigation.EAST){
			// antagligen inte det bästa sättet att lösa det på, x-positionen borde gå att få på något mer logiskt sätt.
			System.out.println(fighterBody.getFixtureList().getShape().getRadius());
			bd.position.set(fighterPos.x + fighterModel.getWidth() + this.RADIUS, fighterPos.y);
		} else if(this.navigation == Navigation.WEST){
			bd.position.set(fighterPos.x - fighterModel.getWidth() - this.RADIUS, fighterPos.y);

		}
		CircleShape cs = new CircleShape();
		cs.m_radius = Utils.pixelsToMeters(RADIUS);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.1f;
		fd.friction = 0.0f;
		fd.restitution = 0.5f;
		fd.filter.maskBits = 555;
		fd.filter.categoryBits = 4;
		
		this.bulletBody = this.world.createBody(bd);
		this.bulletBody.createFixture(fd);
		this.bulletBody.setUserData(this);
		
		
		// Making the Bullet object a bullet in JBox2D, then the Bullet will disappear when it collides with another body.
		this.bulletBody.setBullet(true);
		// This is done so that the Bullet will ignore collision with the shooting character.
		//this.bulletBody.shouldCollide(this.fighterBody);
		
	}
	
	public void fight(IAliveModel fighterModel, Navigation navigation){
		System.out.println("fight() in BulletModel");
		this.isAlive = true;
		this.navigation = navigation;
		this.fighterModel = fighterModel;
		this.fighterBody = fighterModel.getBody();
		this.firstPos = this.fighterBody.getPosition().clone();
		init(firstPos);

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
	public void setAlive(boolean isAlive){
		this.isAlive = isAlive;
	}
	/**
	 * A method used to destroy the body of this bullet
	 */
	public void destroyEntity(){
		this.bulletBody.setActive(false);
		this.world.destroyBody(bulletBody);
		setMoving(false);
		this.bulletBody = null;
		System.out.println("destroyed entity");
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
		return (Utils.metersToPixels(this.bulletBody.getPosition()).add(new Vec2(-RADIUS, -RADIUS)));
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
