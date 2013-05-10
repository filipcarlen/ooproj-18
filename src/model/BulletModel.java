package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;


import utils.Utils;

/** A class representing a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class BulletModel implements IEntityModel{

	private float range;
	private int damage;
	private World world;
	private Body bulletBody;
	private Vec2 firstPos;
	private Vec2 targetPos;
	private Body fighterBody;
	
	public final float RADIUS = 10f;

	public BulletModel(World world, Vec2 fighterPos, Vec2 targetPos, float range, int damage){
		this.range = range;
		this.damage = damage;
		this.world = world;
		this.firstPos = fighterPos;
		this.targetPos = targetPos;
		this.fighterBody = AbstractWeaponModel.getFighterBody(this.world, fighterPos);
		init(fighterPos);
		
	}
	
	public void init(Vec2 fighterPos) throws NullPointerException{
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		bd.position.set(Utils.pixelsToMeters(fighterPos.x - RADIUS), Utils.pixelsToMeters(fighterPos.y - RADIUS));
		
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
	
	public void destroyEntity(){
		this.world.destroyBody(bulletBody);
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
	public Vec2 getTargetPos(){
		return this.targetPos;
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
	public Body getFighterBody(){
		return this.fighterBody;
	}
	
}
