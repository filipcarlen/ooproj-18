package model;

import utils.Navigation;
import utils.Utils;
import utils.WeaponType;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.geom.Vector2f;

public class HeroModel implements IAliveModel{
	
	AbstractWeaponModel weapon;
	int maxHp = 100;
	int hp = maxHp;
	int doubleJump= 0;
	int killCount = 0;
	float width;
	float height;
	
	Body body;
	
	Navigation navigation;
	WeaponType weapontype = null;
	
	boolean dead;
	
	String characterName;
	
	int collectedItem = 0;
	
	public HeroModel(World w, String characterName){
		this(w, characterName, new Vector2f(0,0), 50, 50, null);
	}
	
	public HeroModel(World w, String characterName, AbstractWeaponModel awm){
		this(w, characterName, new Vector2f(0,0), 50, 50, awm);
	}

	public HeroModel(World w, String characterName, Vector2f pos, int width, int height, AbstractWeaponModel weapon){
		this.characterName = characterName;
		this.width = width/2/Utils.METER_IN_PIXELS;
		this.height = height/2/Utils.METER_IN_PIXELS;
		//Create the Body defination
		BodyDef b = new BodyDef();
		b.type = BodyType.DYNAMIC;
		b.position.set(pos.x/Utils.METER_IN_PIXELS , pos.y/Utils.METER_IN_PIXELS );
		b.angle = MathUtils.PI;
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width, this.height);
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.filter.categoryBits =2;
		fd.filter.maskBits = 333;
		fd.density = 0.1f;
		fd.friction = 0.0f;
		fd.restitution = 0.0f;
		fd.shape = pg;
		//Creating an body in the world and a Fixtrue to the body
		body = w.createBody(b);
		body.createFixture(fd);
		body.setUserData(this);
		body.setFixedRotation(true);
		dead = false;
		this.weapon = weapon;
		//this.weapontype = this.weapon.getWeaponType();
	}
	
	public void attack(){
		// Call to the weapon in use
		weapon.fight(getBody(), navigation);
		
	}
	
	public void collectCoin(int c){
		collectedItem += c;
	}
	
	public Body getBody(){
		return body;
	}
	
	public Navigation getDirection(){
		return navigation;
	}

	public int getDoubleJump(){
		return doubleJump;
	}
	
	public float getHeight(){
		return height*2;
	}
	
	public int getHp(){
		return hp;
	}
	
	public int getID(){
		return -1;
	}
	
	public int getMaxHp(){
		return maxHp;
	}

	public String getName() {
		return characterName;
	}
	
	public Vec2 getPosMeters(){
		return body.getPosition();
	}
	
	public Vec2 getPosPixels(){
		return body.getPosition().add(new Vec2(width,height).mul(-1)).mul(Utils.METER_IN_PIXELS);
	}
	
	public Vec2 getFrontPosPixels(){
		if(navigation == Navigation.WEST){
			return body.getPosition().add(new Vec2(width,height).mul(-1)).mul(Utils.METER_IN_PIXELS);
		}else if(navigation == Navigation.EAST){
			return body.getPosition().add(new Vec2(-width,height).mul(-1)).mul(Utils.METER_IN_PIXELS);
		}else
			return getPosPixels();
	}
	
	public AbstractWeaponModel getWeapon(){
		if(weapon != null){
			return weapon;
		}
		return null;
	}
	
	public WeaponType getWeaponType(){
		return weapontype;
	}
	
	public float getWidth(){
		return width*2;
	}
	
	public void hurt(int hpDecrement){
		setHp(getHp()-hpDecrement);
	}
	
	public void incrementJumps(){
		doubleJump +=1;
	}
	
	public void incrementKillCount(){
		killCount += 1;
	}

	public boolean isDead() {
		return dead;
	}

	public void setGroundContact(){
		doubleJump= 0;
	}
	
	public void setDirection(Navigation n){
		this.navigation = n;
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
	
	public void setWeapon(AbstractWeaponModel w, WeaponType wt){
		this.weapon = w;
		this.weapontype = wt;
	}
	
}
