package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import states.PlayState;
import utils.Utils;

/** A class representing a Sword
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class SwordModel extends AbstractWeaponModel{

	private Body body;
	
	public final float RADIUS = 0.5f;
	
	
	public SwordModel(World world, Vec2 heroPos){
		this(world, heroPos, 20, Utils.METER_IN_PIXELS);
	}
	public SwordModel(World world, Vec2 heroPos, int damage){
		this(world, heroPos, damage, Utils.METER_IN_PIXELS);
	}
	public SwordModel(World world, Vec2 heroPos, int damage, float range){
		super(world, damage, range);
		init(heroPos);
	}
	
	public void init(Vec2 heroPos){
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		bd.position.set(heroPos.x, heroPos.y);
		
		PolygonShape ps = new PolygonShape();
		ps.m_radius = RADIUS;
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.3f;
		fd.restitution = 0.2f;
		
		this.body = getWorld().createBody(bd);
		this.body.createFixture(fd);
		this.body.setUserData(this);
		this.body.setBullet(true);
		this.body.shouldCollide(PlayState.getHeroModel().getBody());
		
	}

	public void fight(){
		
	}
	@Override
	public void fight(Vec2 myPos, Vec2 targetPos) {
		
		
	}

}
