package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

/**
 * Class representing a collectible coin
 * @author filipcarlen
 *
 */
public class CoinModel extends AbstractPointsModel {
	
	/** What value a collectible item holds (which points you get) */
	
	/** The collectible items Radius in meters */
	public static final float RADIUS = .5f;
	
	private static final float WIDTH = 1f;
	private static final float HEIGHT = 1f;

	/**
	 * Constructor
	 * @param World w
	 * @param Vec2 pixelPos (Position)
	 * @param int id
	 */
	public CoinModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id, WIDTH, HEIGHT);
		super.setValue(1);
		super.setWidth(WIDTH);
		super.setHeight(HEIGHT);
		
	   	 CircleShape circleshape = new CircleShape();
	   	 circleshape.m_radius = RADIUS;
	   	
	   	 this.getFixtureDef().shape = circleshape;
		 this.getBody().createFixture(this.getFixtureDef());	
	}
	
	/**
	 * Get method for the radius of a coin
	 * @return radius in pixels
	 */
	public float getRadius(){
		return RADIUS * Utils.METER_IN_PIXELS;
	}
	
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.getBody().getPosition().sub(new Vec2(RADIUS,RADIUS)));
	}
}
