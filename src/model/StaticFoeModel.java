package model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.Utils;

public class StaticFoeModel implements IEntityModel {

	public static enum StaticFoeType {FIRE, PLANT, WATER, SPIKES};
	
	private World world;
	
	private Body body;
	
	private final float WIDTH = 30f; 
	private final float HEIGHT = 30f;
	
	private int damage;
	
	private int ID;
	
	private StaticFoeType type;
	
	/**
	 * Creates a new static foe.
	 * @param world 	the world to which the 
	 * @param pixelPos 	the upper left corner position of this entity
	 * @param width		the width of this entity in pixels
	 * @param height 	the height of this entity in pixels
	 * @param animation the animation of this entity
	 */
	public StaticFoeModel(World world, Vec2 pixelPos, int damage, StaticFoeType type, int ID) {
		this.world = world;
		this.damage = damage;
		this.type = type;
		this.ID = ID;
		init(pixelPos);
	}
	
	/**
	 * Initiates the body of this entity in chosen position.
	 * @param pixelPos	the chosen position of this entity
	 */
	public void init(Vec2 pixelPos) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(Utils.pixelsToMeters(pixelPos.add(new Vec2(this.WIDTH/2, this.HEIGHT/2))));
		bodyDef.type = BodyType.STATIC;
		
		PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(Utils.pixelsToMeters(this.WIDTH/2), Utils.pixelsToMeters(this.HEIGHT/2));
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = polyShape;
		fixDef.density = 1f;
		fixDef.friction = 0.0f;
		fixDef.restitution = 2f;
		fixDef.filter.groupIndex = -1;
		fixDef.filter.categoryBits = 4;
		fixDef.filter.maskBits = 555;
		
		body = this.world.createBody(bodyDef);
		body.createFixture(fixDef);
		
		// This is for collision handling.
		body.setUserData(this);
	}
	
	@Override
	public Vec2 getPosMeters() {
		return this.body.getPosition();
	}

	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(body.getPosition()).sub(new Vec2(this.WIDTH/2, this.WIDTH/2));
	}

	@Override
	public Body getBody() {
		return this.body;
	}
	
	public int getDamage() {
		return this.damage;
	}

	@Override
	public int getID() {
		return this.ID;
	}
	
	@Override
	public float getWidth() {
		return Utils.pixelsToMeters(this.WIDTH);
	}
	
	@Override
	public float getHeight() {
		return Utils.pixelsToMeters(this.HEIGHT);
	}
	
	public StaticFoeType getType() {
		return this.type;
	}
}
