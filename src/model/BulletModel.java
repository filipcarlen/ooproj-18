package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/** A class representing a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class BulletModel implements IEntityModel{

	private int range;
	private int damage;
	private Body body;
	private World world;
	
<<<<<<< HEAD
	public BulletModel(){
		this.range = 400;
		this.damage = 20;
	}
	public BulletModel(int range){
		this();
=======
	public final float RADIUS = 0.7f;
	
	public BulletModel(int damage, float range, World world, Vec2 heroPos){
>>>>>>> Changed the constructors in all Weapon classes, added method init(Vec2 heroPos) to BulletModel
		this.range = range;
	}
	public BulletModel(int range, int damage){
		this(range);
		this.damage = damage;
		this.world = world;
		init(heroPos);
		
	}
	
	public void init(Vec2 heroPos){
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.position.set(heroPos.x, heroPos.y);
		
		CircleShape cs = new CircleShape();
		cs.m_radius = RADIUS;
		
		FixtureDef fd = new FixtureDef();
		fd.shape = cs;
		fd.density = 0.5f;
		fd.friction = 0.3f;
		fd.restitution = 0.2f;
		
		this.body = this.world.createBody(bd);
		this.body.createFixture(fd);
		this.body.setUserData(this);
		
	}
	
	
	public int getDamage(){
		return damage;
	}
	public void setDamage(int damage){
		this.damage = damage;
	}
	public int getRange(){
		return range;
	}
	public void setRange(int range){
		this.range = range;
	}
	public void fight(){
		
	}
	@Override
	public Vec2 getPosMeters() {
		return null;
	}
	@Override
	public Vec2 getPosPixels() {
		return null;
	}
	@Override
	public Body getBody() {
		return null;
	}
}
