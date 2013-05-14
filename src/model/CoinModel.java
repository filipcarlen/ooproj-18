package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

public class CoinModel extends AbstractCollectibleModel {
	
	/** What value a collectible item holds (which points you get) */
	private int value = 1;
	
	/** The collectible items Radius in meters */
	public final float RADIUS = .5f;

	public CoinModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
		
		 //Circle shapes are perfect for collectible objects
	   	 CircleShape circleshape = new CircleShape();
	   	 circleshape.m_radius = RADIUS;
	   	
	   	 this.getFixtureDef().shape = circleshape;
		 this.getBody().createFixture(this.getFixtureDef());	
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		this.value = value;
		
	}
	
	/**
	 * 
	 * @return radius in pixels
	 */
	public float getRadius(){
		return this.RADIUS * Utils.METER_IN_PIXELS;
	}
	
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.getBody().getPosition().add(new Vec2(-RADIUS,-RADIUS)));
	}

	@Override
	public Vec2[] getVertices() {
		return null;
	}
}
