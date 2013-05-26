package model;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

/**
 * Class representing a collectible coin which gives character 1 point
 * @author Filip Carlén
 *
 */
public class Coin extends AbstractValuable {
	
	/** What value a collectible item holds (which points you get) */
	private static final int VALUE = 1;
	
	/** The collectible items Radius in meters */
	public static final float RADIUS = .5f;

	/**
	 * Constructor
	 * @param World w
	 * @param Vec2 pixelPos (Position)
	 * @param int id
	 */
	public Coin(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id, RADIUS*2, RADIUS*2);
		super.setValue(VALUE);
		
	   	 CircleShape circleshape = new CircleShape();
	   	 circleshape.m_radius = super.getWidth();
	   	
	   	 this.getFixtureDef().shape = circleshape;
		 this.getBody().createFixture(this.getFixtureDef());	
	}
	

}
