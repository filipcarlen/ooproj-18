package map;

import model.IEntityModel;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.EntityType;
import utils.Utils;

public class WorldShapes implements IEntityModel{

	Body body;
	int id;
	float width;
	float height;
	
	public WorldShapes(World world, float xCoordinate, float yCoordinate, int width, int height, int id){
		this.id = id;
		this.width = width/Utils.METER_IN_PIXELS/2;
		this.height = height/Utils.METER_IN_PIXELS/2;
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
		body.setUserData(EntityType.GROUND);
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
		return body.getPosition().add(new Vec2(-width, -height)).mul(Utils.METER_IN_PIXELS);
	}
	
}
