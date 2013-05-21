package model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.SlickException;

import utils.SoundType;
import utils.Sounds;
import utils.Utils;

public class MovingFoeModel implements IAliveModel{
	
	private World world;
	private int hp;
	private AbstractWeaponModel weapon;
	
	private int ID;
	
	private int points;
	
	private Body body;
	
	private final float WIDTH = 40f; 
	private final float HEIGHT = 40f;
	
	private boolean isAlive;
	
	/** 
	 * When the hero is within the SIGHT_RANGE from this enemy, this enemy will move
	 * towards to the hero. 
	 */
	public final int SIGHT_RANGE_X = 500;
	public final int SIGHT_RANGE_Y = 200;
	
	private final int MAX_HP;
	
	/**
	 * @param world The World this enemy will belong to.
	 * @param pos The position of this enemy's top left corner, in pixels!
	 */
	public MovingFoeModel(World world, Vec2 pixelPos, int maxHP, AbstractWeaponModel weapon, int points, int ID) {
		this.world = world;
		this.hp = maxHP;
		this.weapon = weapon;
		this.isAlive = true;
		this.MAX_HP = maxHP;
		this.points = points;
		this.ID = ID;
		init(pixelPos);
	}
	
	/**
	 * @param pos The position of this enemy's top left corner, in pixels!
	 */
	public void init(Vec2 pixelPos) {
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(Utils.pixelsToMeters(pixelPos.add(new Vec2(this.WIDTH/2, this.WIDTH/2))));
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.angle = MathUtils.PI;
		
		PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(Utils.pixelsToMeters(this.WIDTH)/2, Utils.pixelsToMeters(this.HEIGHT)/2);
		
		FixtureDef fixDef = new FixtureDef();
		fixDef.shape = polyShape;
		fixDef.density = 0f;
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
	public void hurt(int hpDecrement) {
		if(!(hp - hpDecrement <= 0)){
			/*try {
				Sounds.getInstance().playSound(SoundType.ENEMY_HURT);
			} catch (SlickException e) {
				e.printStackTrace();
			}*/
			this.hp -= hpDecrement;
		} else {
			try {
				Sounds.getInstance().playMusic(SoundType.ENEMY_DIE);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			this.hp = 0;
			this.isAlive = false;
		}
	}

	@Override
	public int getMaxHp() {
		return this.MAX_HP;
	}
	
	public AbstractWeaponModel getWeapon() {
		return this.weapon;
	}
	
	public void destroyEntity(){
		this.body.getWorld().destroyBody(this.body);
	}
	
	public boolean isAlive() {
		return this.isAlive;
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
	
	public int getValue() {
		return this.points;
	}
}
