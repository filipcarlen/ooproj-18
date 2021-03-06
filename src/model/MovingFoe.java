package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.Utils;

public class MovingFoe implements IAliveEntity{
	
	private World world;
	private int hp;
	private AbstractWeapon weapon;
	
	private int ID;
	
	private int value;
	
	private Body body;
	
	private final float WIDTH = 23f; 
	private final float HEIGHT = 28f;
	
	private boolean isAlive;
	
	/** 
	 * The hero is visible to this foe if the hero is within SIGHT_RANGE_X along the x-axis and SIGHT_RANGE_Y along the y-axis.
	 */
	public final int SIGHT_RANGE_X = 500;
	public final int SIGHT_RANGE_Y = 200;
	
	private final int MAX_HP;
	
	/**
	 * @param world the World this foe will belong to
	 * @param pos 	the position of this foe's top left corner in pixels
	 */
	public MovingFoe(World world, Vec2 pixelPos, int maxHP, AbstractWeapon weapon, int value, int ID) {
		this.world = world;
		this.hp = maxHP;
		this.weapon = weapon;
		this.isAlive = true;
		this.MAX_HP = maxHP;
		this.value = value;
		this.ID = ID;
		init(pixelPos);
	}
	
	/**
	 * Initiates the body of this moving foe.
	 * @param pos	the position of this enemy's top left corner, in pixels!
	 */
	public void init(Vec2 pixelPos) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(Utils.pixelsToMeters(pixelPos.add(new Vec2(this.WIDTH/2, this.WIDTH/2))));
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.angle = MathUtils.PI;
		
		CircleShape circleShape = new CircleShape();
		circleShape.m_radius = Utils.pixelsToMeters(this.WIDTH)/2;
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = circleShape;
		fixDef.density = 0.15f;
		fixDef.friction = 0f;
		fixDef.filter.groupIndex = -1;
		
		this.body = this.world.createBody(bodyDef);
		this.body.createFixture(fixDef);
		
		//This is for collision handling.
		this.body.setUserData(this);
		
		this.body.setFixedRotation(true);
		
		this.isAlive = true;
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
		return Utils.metersToPixels(this.body.getPosition()).sub(new Vec2(this.WIDTH/2, this.HEIGHT/2));
	}

	@Override
	public int getHp() {
		return this.hp;
	}

	@Override
	public void setHp(int hp) {
		if(!(hp < 0) && !(hp > 100)) {
			this.hp = hp;
		}
	}
	
	@Override
	public int getMaxHp() {
		return this.MAX_HP;
	}

	@Override
	public void hurt(int hpDecrement) {
		if(!(this.hp - hpDecrement <= 0)){
			this.hp -= hpDecrement;
		} else {
			this.hp = 0;
			this.isAlive = false;
		}
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
	
	/**
	 * @return	the weapon of this foe
	 */
	public AbstractWeapon getWeapon() {
		return this.weapon;
	}
	
	/**
	 * Destroys this entity.
	 */
	public void destroyEntity(){
		this.body.getWorld().destroyBody(this.body);
	}
	
	/**
	 * @return	true if this foe is alive
	 */
	public boolean isAlive() {
		return this.isAlive;
	}
	
	/**
	 * @return	the value of this foe
	 */
	public int getValue() {
		return this.value;
	}
}
