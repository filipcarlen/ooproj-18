package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import states.PlayState;
import utils.Utils;

/** A class representing a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class BulletModel implements IEntityModel{

	private float range;
	private int damage;
	private Body body;
	private World world;
	private Vec2 firstPos;
	
	public final float RADIUS = 0.3f;

	public BulletModel(World world, Vec2 myPos, Vec2 targetPos, float range, int damage){
		this.range = range;
		this.damage = damage;
		this.world = world;
		this.firstPos = myPos;
		init(myPos);
		
	}
	
	public void init(Vec2 myPos){
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		bd.position.set(myPos.x, myPos.y);
		
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
		this.body.setBullet(true);
		this.body.shouldCollide(this.world.getBodyList()); //måste loopa igenom listan och kolla vilken body som finns på positionen :(
		
	}
	
	
	public float getRange(){
		return this.range;
	}
	public void setRange(int range){
		this.range = range;
	}
	public int getDamage(){
		return this.damage;
	}
	public void setDamage(int damage){
		this.damage = damage;
	}

	public Vec2 getFirstPos(){
		return this.firstPos;
	}
	@Override
	public Vec2 getPosMeters() {
		return this.body.getPosition();
	}
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.body.getPosition());
	}
	@Override
	public Body getBody() {
		return this.body;
	}
}
