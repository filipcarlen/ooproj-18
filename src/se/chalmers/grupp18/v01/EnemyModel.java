package se.chalmers.grupp18.v01;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class EnemyModel implements IEntityModel{
	
	private World world;
	private int hp;
	private AbstractWeaponModel weapon;
	
	private Body body;
	
	private float width, height;
	
	/** 
	 * When the hero is within the SIGHT_RANGE from this enemy, this enemy will move
	 * closer to the hero. 
	 */
	private int SIGHT_RANGE = 300;
	
	/**
	 * @param world The World this enemy will belong to.
	 * @param pos The position of this enemy's top left corner, in pixels!
	 */
	public EnemyModel(World world, Vec2 pos, float width, float height) {
		this.world = world;
		this.width = width;
		this.height = height;
		init(pos);
	}
	
	/**
	 * @param pos The position of this enemy's top left corner, in pixels!
	 */
	public void init(Vec2 pos) {
		this.hp = 100;
		this.weapon = new SwordModel();
		
		Vec2 tmppos = pos.add(new Vec2())
		
		BodyDef bd = new BodyDef();
		bd.position.set();
		bd.type = BodyType.DYNAMIC;
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(this.pos.x, this.pos.y);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.3f;
		fd.restitution = 0.2f;
		
		body = this.world.createBody(bd);
		body.createFixture(fd);
	}
	
	@Override
	public Body getBody() {
		return this.body;
	}

	@Override
	public Vec2 getPosMeters() {
		return this.body.getPosition();
	}

	@Override
	public Vec2 getPosPixels() {
		return null;
	}
	
	
}
