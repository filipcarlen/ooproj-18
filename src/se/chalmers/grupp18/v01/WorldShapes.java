package se.chalmers.grupp18.v01;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class WorldShapes implements IEntityModel{

	Body body;
	int id;
	float width;
	float height;
	
	public WorldShapes(World world, float xCoordinate, float yCoordinate, float transfer, int width, int height, int id){
		this.id = id;
		this.width = width/transfer;
		this.height = height/transfer;
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(xCoordinate, yCoordinate);
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width, this.height);
		
		//The Fixture
		
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		body = world.createBody(b);
		body.createFixture(fd);
		body.setUserData("Ground");
	}
	
	public Body getBody(){
		return body;
	}
	
	public int getId(){
		return id;
	}

	@Override
	public Vec2 getPosMeters() {
		return body.getPosition();
	}

	@Override
	public Vec2 getPosPixels() {
		return body.getPosition().add(new Vec2(-width, -height)).mul(30f);
	}
	
}