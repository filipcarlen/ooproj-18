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

	Body body, bodyGround, bodyRoof, bodyWallLeft, bodyWallRight;
	int id;
	float width;
	float height;
	float diff = -.01f;
	World world;
	
	public WorldShapes(World world, float xCoordinate, float yCoordinate, int width, int height, int numberOfTiles){
		BodyDef b = new BodyDef();
		b.position = new Vec2( xCoordinate, yCoordinate);
		body = world.createBody(b);
		this.world = world;
		this.width = (width/Utils.METER_IN_PIXELS/2) * numberOfTiles;
		this.height = (height/Utils.METER_IN_PIXELS/2);
		addGround(xCoordinate + this.width, yCoordinate);
		addRoof(xCoordinate+ this.width, yCoordinate+ (this.height*2));
		addWallRight(xCoordinate + (this.width*2), yCoordinate + this.height);
		addWallLeft(xCoordinate, yCoordinate + this.height);
	}
	
	private void addRoof(float x, float y){
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x , y);
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width + diff, 0);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		bodyRoof = world.createBody(b);
		bodyRoof.createFixture(fd);
		bodyRoof.setUserData(EntityType.ROOF);
	}
	
	
	private void addGround(float x, float y){
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x, y);
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width + diff, 0);
		
		//The Fixture
		
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		bodyGround = world.createBody(b);
		bodyGround.createFixture(fd);
		bodyGround.setUserData(EntityType.GROUND);
	}
	
	private void addWallLeft(float x, float y){
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x , y);
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(0 , this.height + diff);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		bodyWallLeft = world.createBody(b);
		bodyWallLeft.createFixture(fd);
		bodyWallLeft.setUserData(EntityType.WALL);
	}
	
	private void addWallRight(float x, float y){	
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x , y);
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(0, this.height + diff);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		bodyWallRight = world.createBody(b);
		bodyWallRight.createFixture(fd);
		bodyWallRight.setUserData(EntityType.WALL);
	}
	
	public Body getBody(){
		return null;
	}
	
	public int getID(){
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
