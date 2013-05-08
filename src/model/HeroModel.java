package model;

import utils.Utils;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class HeroModel implements IAliveModel{
	
	AbstractWeaponModel weapon;
	static final int maxHp = 100;
	int hp = maxHp;
	int doubleJump= 0;
	
	float width;
	float height;
	
	Body body;
	
	boolean dead;
	
	String characterName;
	
	int collectedItem = 0;
	
	public HeroModel(World w, String characterName){
		this(w, characterName, new Vec2(0,0), 50, 50, null);
	}

	public HeroModel(World w, String characterName,Vec2 pos, int width, int height, AbstractWeaponModel weapon){
		this.characterName = characterName;
		this.width = width/2/Utils.METER_IN_PIXELS;
		this.height = height/2/Utils.METER_IN_PIXELS;
		if(weapon != null)
			this.weapon = weapon;
		//Create the Body defination
		BodyDef b = new BodyDef();
		b.type = BodyType.DYNAMIC;
		b.position.set(100/Utils.METER_IN_PIXELS , 100/Utils.METER_IN_PIXELS );
		b.angle = MathUtils.PI;
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width, this.height);
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.density = 0.1f;
		fd.friction = 0f;
		fd.shape = pg;
		//Creating an body in the world and a Fixtrue to the body
		body = w.createBody(b);
		body.createFixture(fd);
		body.setUserData(this);
		body.setFixedRotation(true);
		dead = false;
	}
	
	public void collectCoin(int c){
		collectedItem += c;
	}
	
	public void attack(){
		// Call to the weapon in use 
	}
	
	public int getHp(){
		return hp;
	}
	
	public void setHp(int hp){
		if(hp <= 0){
			body.getWorld().destroyBody(body);
			dead= true;
		}else if(hp >getMaxHp()){
			this.hp = getMaxHp();
		}else
			this.hp = hp;
	}
	
	public void hurt(int hpDecrement){
		setHp(getHp()-hpDecrement);
	}
	
	public int getMaxHp(){
		return maxHp;
	}
	
	public void setWeapon(AbstractWeaponModel w){
		this.weapon = w;
	}
	
	public AbstractWeaponModel getWeapon(){
		if(weapon != null){
			return weapon;
		}
		return null;
	}
	
	public Vec2 getPosMeters(){
		return body.getPosition();
	}
	
	public Vec2 getPosPixels(){
		return body.getPosition().add(new Vec2(width,height).mul(-1)).mul(Utils.METER_IN_PIXELS);
	}
	
	public Body getBody(){
		return body;
	}
	
	public float getWidth(){
		return width*2;
	}
	
	public float getHeight(){
		return height*2;
	}

	public String getName() {
		return characterName;
	}

	public boolean isDead() {
		return dead;
	}
	
	public void incrementJumps(){
		doubleJump +=1;
	}
	
	public void setGroundContact(){
		doubleJump= 0;
	}
	
	public int getDoubleJump(){
		return doubleJump;
	}
	
}
