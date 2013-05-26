package model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

/** A class representing a gem which gives character 5 points
 * @author group 18
 *
 */

public class Gem extends AbstractValuable {
	
	/**  A array cointaining vertices for a gem */
	Vec2[] vertices = {new Vec2(0.0f,0.5f), new Vec2(0.5f,0.0f),
			new Vec2(0.5f,0.5f), new Vec2(0.5f,0.5f)};
	

	/**
	 * Constructor for a gem.
	 * 
	 * @param World w
	 * @param Vec2 pixelPos (position)
	 * @param int id
	 */
	public Gem(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id, 1, 1);
		super.setValue(5);
		
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices, 4);
		getFixtureDef().shape = polygon;
		this.getBody().createFixture(this.getFixtureDef());	
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
