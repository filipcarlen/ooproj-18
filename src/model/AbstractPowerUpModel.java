package model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

public abstract class AbstractPowerUpModel extends AbstractCollectibleModel {

	private double hpBoost;
	private static final float WIDTH = 1.0f;
	private static final float HEIGHT = 1.0f;
	
	public AbstractPowerUpModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id, WIDTH, HEIGHT);
		
		PolygonShape polygon = new PolygonShape();
	   	polygon.setAsBox(WIDTH/2, HEIGHT/2);
	   	
	   	this.getFixtureDef().shape = polygon;
		this.getBody().createFixture(this.getFixtureDef());
	}
	
	public double gethpBoost() {
		return this.hpBoost;
	}

	public void setHpBoost(double hpBoost) {
		this.hpBoost = hpBoost;
		
	}
	
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.getBody().getPosition().sub(new Vec2(WIDTH/2, HEIGHT/2)));
	}
}