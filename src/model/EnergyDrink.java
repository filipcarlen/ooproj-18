package model;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
/**
 * Class representing a Chocolate bar, which gives character 50% of max hp i hp boost
 * @author Filip Carlén
 *
 */
public class EnergyDrink extends AbstractPowerUp {
	
	private final double HP_BOOST = 0.5;
	private static final float WIDTH = 1.0f;
	private static final float HEIGHT = 1.0f;

	public EnergyDrink(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id, WIDTH, HEIGHT);
		super.setHpBoost(HP_BOOST);
		
		PolygonShape polygon = new PolygonShape();
	   	polygon.setAsBox(super.getWidth()/2, super.getHeight()/2);
	   	
	   	this.getFixtureDef().shape = polygon;
		this.getBody().createFixture(this.getFixtureDef());
	}
}
