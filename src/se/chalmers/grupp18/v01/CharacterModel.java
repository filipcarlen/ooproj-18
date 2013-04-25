package se.chalmers.grupp18.v01;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class CharacterModel implements IEntityModel{
	
	Weapon weapon;
	int hp = 100;
	
	float width = .5f;
	float height = .5f;
	Body body;
	
	ArrayList<Integer> collectedItem = new ArrayList<Integer>();
	
	public CharacterModel(World w){
		this(w, new Vec2(0,0), 1, 1);
	}

	public CharacterModel(World w, Vec2 pos, int width, int height){
		//Create the Body defination
		BodyDef b = new BodyDef();
		b.type = BodyType.DYNAMIC;
		b.position.set(600/50/2 , 900/50/2 );
		b.angle = MathUtils.PI;
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width, this.height);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.density = 0.7f;
		fd.friction = 0.0f;
		fd.shape = pg;
		
		body = w.createBody(b);
		body.createFixture(fd);
	}
	
	public void collectCoin(int c){
		this.collectedItem.add(c);
	}
	
	public void fight(){
		// Call to the weapon in use 
	}
	
	public int getHp(){
		return hp;
	}
	
	public void setHp(int hp){
		if(hp <= 0){
			body.getWorld().destroyBody(body);
		}else if(hp >100){
			this.hp = 100;
		}else
			this.hp = hp;
	}
	
	public void hurt(int hpDecrement){
		setHp(getHp()-hpDecrement);
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
		body.getPosition();
	}
	
	public Vec2 getPosPixels(){
		body.getPosition().mul(50);
	}
	
	public Body getBody(){
		return body;
	}
	
	public float getX(){
		return body.getPosition().x;
	}
	
	public float getY(){
		return body.getPosition().y;
	}
}
