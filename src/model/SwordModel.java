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
import utils.Navigation;
import utils.Utils;
import utils.WeaponType;

/** A class representing a Sword
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class SwordModel extends AbstractWeaponModel implements IEntityModel{

	private Body body;
	private Vec2 firstPos;
	private boolean fighting;
	
	public final float RADIUS = 0.5f;
	
	
	public SwordModel(World world, Vec2 myPos){
		this(world, myPos, 20, Utils.METER_IN_PIXELS);
	}
	public SwordModel(World world, Vec2 myPos, int damage){
		this(world, myPos, damage, Utils.METER_IN_PIXELS);
	}
	public SwordModel(World world, Vec2 myPos, int damage, float range){
		super(world, damage, range);
		super.setWeaponType(WeaponType.sword);
		init(myPos);
	}
	
	public void init(Vec2 myPos){
		BodyDef bd = new BodyDef();
		bd.type = BodyType.KINEMATIC;
		bd.position.set(Utils.pixelsToMeters(myPos.x), Utils.pixelsToMeters(myPos.y));
		
		PolygonShape ps = new PolygonShape();
		ps.m_radius = RADIUS;
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.3f;
		fd.restitution = 0.5f;
		
		this.body = getWorld().createBody(bd);
		this.body.createFixture(fd);
		this.body.setUserData(this);
		this.body.shouldCollide(AbstractWeaponModel.getFighterBody(super.getWorld(), this.firstPos));
		
	}

	public boolean isFighting(){
		return fighting;
	}
	
	public void setFighting(boolean b){
		fighting = b;
	}
	
	@Override
	public void fight(Vec2 myPos, Navigation navigation) {
		this.firstPos = myPos;
		this.fighting = true;
			
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
		return body;
	}

}
