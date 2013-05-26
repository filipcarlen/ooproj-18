package model;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.EntityType;
import utils.Utils;
/**
 * This is a class to hold the value and create a platform in the game
 * @author Project Group 18 (Chalmers, 2013)
 *
 */
public class WorldShape implements IEntity {

	/* */
	Body body, bodyGround, bodyRoof, bodyWallLeft, bodyWallRight;
	
	int id;
	
	float width;

	float height;
	
	/* This diff variable is added because of all thing that get rounded in calculations
	 * (so that the floor, roof and wall wont overlap each other)*/
	float diff = -.02f;

	public WorldShape(World world, float xCoordinate, float yCoordinate,
			int width, int height, int numberOfTiles) {
		BodyDef b = new BodyDef();
		b.position = new Vec2(xCoordinate, yCoordinate);
		body = world.createBody(b);
		this.width = (width / Utils.METER_IN_PIXELS / 2) * numberOfTiles;
		this.height = (height / Utils.METER_IN_PIXELS / 2);
		addGround(xCoordinate + this.width, yCoordinate);
		addRoof(xCoordinate + this.width, yCoordinate + (this.height * 2));
		addWallRight(xCoordinate + (this.width * 2), yCoordinate + this.height);
		addWallLeft(xCoordinate, yCoordinate + this.height);
	}

	/**
	 * This method adds the body at the bottom
	 * (so if you jump up in a platform this is the "roof" you will hit).
	 * @param x
	 * @param y
	 */
	private void addRoof(float x, float y) {
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x, y);
		// Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width + diff, 0);

		// The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		bodyRoof = body.getWorld().createBody(b);
		bodyRoof.createFixture(fd);
		bodyRoof.setUserData(EntityType.ROOF);
	}

	/**
	 *  This is the body you will walk on.
	 * @param x
	 * @param y
	 */
	private void addGround(float x, float y) {
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x, y);

		// Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width + diff, 0);

		// The Fixture

		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		bodyGround = body.getWorld().createBody(b);
		bodyGround.createFixture(fd);
		bodyGround.setUserData(EntityType.GROUND);
	}

	/**
	 * This is the Wall on the left side of the platform.
	 * @param x
	 * @param y
	 */
	private void addWallLeft(float x, float y) {
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x, y);
		// Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(0, this.height + diff);

		// The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		bodyWallLeft = body.getWorld().createBody(b);
		bodyWallLeft.createFixture(fd);
		bodyWallLeft.setUserData(EntityType.WALL);
	}

	/**
	 * This is the wall on the right side of the platform.
	 * @param x
	 * @param y
	 */
	private void addWallRight(float x, float y) {
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x, y);
		// Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(0, this.height + diff);

		// The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		bodyWallRight = body.getWorld().createBody(b);
		bodyWallRight.createFixture(fd);
		bodyWallRight.setUserData(EntityType.WALL);
	}

	public Body getBody() {
		return null;
	}
	
	public void destroyBody(){
		body.getWorld().destroyBody(bodyGround);
		body.getWorld().destroyBody(bodyRoof);
		body.getWorld().destroyBody(bodyWallLeft);
		body.getWorld().destroyBody(bodyWallRight);
		body.getWorld().destroyBody(body);
	}

	public int getID() {
		return id;
	}

	@Override
	public Vec2 getPosMeters() {
		return body.getPosition();
	}

	@Override
	public Vec2 getPosPixels() {
		return body.getPosition().add(new Vec2(-width, -height))
				.mul(Utils.METER_IN_PIXELS);
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public float getWidth() {
		return width;
	}

}
