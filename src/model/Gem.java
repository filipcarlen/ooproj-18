package model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

/** A class representing a gem. A gem is a collectible object, that is
 * more worth than a coin 
 * @author filipcarlen
 *
 */

public class Gem extends AbstractPoints {
	
	
	/**  A array cointaining vertices for a gem */
	Vec2[] vertices = {new Vec2(0.0f,0.5f), new Vec2(0.5f,0.0f),
			new Vec2(0.5f,0.5f), new Vec2(0.5f,0.5f)};
	
	private static final float WIDTH = 1f;
	private static final float HEIGHT = 1f;

	/**
	 * Constructor for a gem.
	 * 
	 * @param World w
	 * @param Vec2 pixelPos (position)
	 * @param int id
	 */
	public Gem(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id, WIDTH, HEIGHT);
		super.setValue(5);
		
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices, 4);
		getFixtureDef().shape = polygon;
		this.getBody().createFixture(this.getFixtureDef());
		
	}

	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.getBody().getPosition().sub(new Vec2(WIDTH/2, HEIGHT/2)));
	}
	
	/** 
	 * Get method for array of vertices. Converts vertices to Pixels
	 * @return Array of vertices
	 */
	public Vec2[] getVertices(){
		Vec2[] tmp = new Vec2[4];
		for(int i = 0; i<vertices.length; i++){
			tmp[i] = new Vec2(vertices[i].x*Utils.METER_IN_PIXELS, vertices[i].y*Utils.METER_IN_PIXELS);
		}
		return tmp;
	}
}
