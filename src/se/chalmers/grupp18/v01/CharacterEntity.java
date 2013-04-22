package se.chalmers.grupp18.v01;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;

public class CharacterEntity {
	
	float x = 0;
	float y = 0;
	
	float width = 0.75f;
	float height = 0.75f;
	Body body;

	public CharacterEntity(World w, Vec2 gravity){
		//Create the Body defination
		BodyDef b = new BodyDef();
		b.type = BodyType.DYNAMIC;
		b.position.set(0 , 0 );
		b.angle = MathUtils.PI;
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(width* 30, height*30);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.density = 0.1f;
		fd.friction = 0.2f;
		fd.shape = pg;
		
		body = w.createBody(b);
		body.createFixture(fd);
	}
	
	public void update(){
		
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
