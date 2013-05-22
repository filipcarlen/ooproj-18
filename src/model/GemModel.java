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

public class GemModel extends AbstractPointsModel {
	
	
	/**  A array cointaining vertices for a gem */
	Vec2[] vertices = {new Vec2(0.0f,0.25f), new Vec2(0.25f,0.0f),
			new Vec2(0.5f,0.25f), new Vec2(0.25f,0.5f)};

	/**
	 * Constructor for a gem.
	 * 
	 * @param World w
	 * @param Vec2 pixelPos (position)
	 * @param int id
	 */
	public GemModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
		super.setValue(5);
		
		PolygonShape polygon = new PolygonShape();
		polygon.set(vertices, 4);
		getFixtureDef().shape = polygon;
		this.getBody().createFixture(this.getFixtureDef());
		
	}

	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.getBody().getPosition().add(new Vec2(-0.25f,-0.025f)));
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

	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}
}
