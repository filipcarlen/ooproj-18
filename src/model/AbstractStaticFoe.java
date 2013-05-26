package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.Utils;

public abstract class AbstractStaticFoe implements IEntity {
	
	private int damage;
	
	private int ID;
	
	protected final float WIDTH;
	protected final float HEIGHT;
	
	protected FixtureDef fixDef;
	protected Body body;
	
	public AbstractStaticFoe(World world, Vec2 pixelPos, int damage, int ID,  float width, float height) {
		this.damage = damage;
		this.ID = ID;
		this.WIDTH = width;
		this.HEIGHT = height;
		init(pixelPos, world);
	}
	
	public void init(Vec2 pixelPos, World world) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(Utils.pixelsToMeters(pixelPos.add(new Vec2(this.WIDTH/2, this.HEIGHT/2))));
		
		this.fixDef = new FixtureDef();

		this.fixDef.density = 1f;
		this.fixDef.friction = 0.0f;
		this.fixDef.restitution = 3f;
		
		this.fixDef.filter.groupIndex = -1;
		this.fixDef.filter.categoryBits = 4;
		this.fixDef.filter.maskBits = 555;
		
		this.body = world.createBody(bodyDef);
		this.body.setUserData(this);
	}
	
	@Override
	public Body getBody() {
		return this.body;
	}
	
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.body.getPosition()).sub(new Vec2(this.WIDTH/2, this.HEIGHT/2));
	}
	
	@Override
	public Vec2 getPosMeters() {
		return this.body.getPosition();
	}
	
	@Override
	public float getHeight() {
		return Utils.pixelsToMeters(this.HEIGHT);
	}

	@Override
	public float getWidth() {
		return Utils.pixelsToMeters(this.WIDTH);
	}

	@Override
	public int getID() {
		return this.ID;
	}
	
	public int getDamage() {
		return this.damage;
	}
}
