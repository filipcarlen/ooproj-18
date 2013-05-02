package se.chalmers.grupp18.v01;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class HeroModel implements IAliveModel{
	
	Weapon weapon;
	int maxHp = 100;
	int hp = maxHp;
	
	float width = .75f;
	float height = .75f;
	
	Body body;
	
	boolean dead;
	
	String characterName;
	
	float transfer;
	
	int collectedItem = 0;
	
	public HeroModel(World w, String characterName,float transfer){
		this(w, characterName, new Vec2(0,0), 1, 1, transfer, null);
	}

	public HeroModel(World w, String characterName,Vec2 pos, int width, int height, float transfer, Weapon weapon){
		this.transfer = transfer;
		this.characterName = characterName;
		if(weapon != null)
			this.weapon = weapon;
		//Create the Body defination
		BodyDef b = new BodyDef();
		b.type = BodyType.DYNAMIC;
		b.position.set(100/transfer , 100/transfer );
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
		body.setUserData("Hero");
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
	
	public void setWeapon(Weapon w){
		this.weapon = w;
	}
	
	public Weapon getWeapon(){
		if(weapon != null){
			return weapon;
		}
		return null;
	}
	
	public Vec2 getPosMeters(){
		return body.getPosition();
	}
	
	public Vec2 getPosPixels(){
		return body.getPosition().mul(transfer);
	}
	
	public Body getBody(){
		return body;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public float getTransfer(){
		return transfer;
	}

	public String getName() {
		return characterName;
	}

	public boolean isDead() {
		return dead;
	}
	
	
}
