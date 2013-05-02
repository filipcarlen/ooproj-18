package se.chalmers.grupp18.v01;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class WorldShapes {

	Body body;
	int id;
	
	public WorldShapes(World world, float xCoordinate, float yCoordinate, float transfer, int width, int height, int id){
		this.id = id;
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(xCoordinate, yCoordinate);
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(width/transfer, height/transfer);
		
		//The Fixture
		
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		body = world.createBody(b);
		body.createFixture(fd);
	}
	
	public Body getBody(){
		return body;
	}
	
	public int getId(){
		return id;
	}
	
}
