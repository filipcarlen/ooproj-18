package se.chalmers.grupp18.v01;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class CharacterEntity {
	
	Weapon weapon;
	int hp = 100;
	
	float width = .5f;
	float height = .5f;
	Body body;
	
	public CharacterEntity(World w){
		this(w, new Vec2(0,0), new Vec2(0,0));
	}

	public CharacterEntity(World w, Vec2 pos, Vec2 x){
		//Create the Body defination
		BodyDef b = new BodyDef();
		b.type = BodyType.DYNAMIC;
		b.position.set(600/50/2 , 900/50/2 );
		b.angle = MathUtils.PI;
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(width, height);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.density = 0.7f;
		fd.friction = 0.0f;
		fd.shape = pg;
		
		body = w.createBody(b);
		body.createFixture(fd);
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
