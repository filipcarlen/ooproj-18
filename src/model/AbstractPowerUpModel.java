package model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

public abstract class AbstractPowerUpModel extends AbstractCollectibleModel {

	private double hpBoost;
	private float width = .5f;
	private float height = .5f;
	
	public AbstractPowerUpModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
		
		PolygonShape polygon = new PolygonShape();
	   	polygon.setAsBox(width/2, height/2);
	   	
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
	public float getHeight() {
		return this.width;
	}

	@Override
	public float getWidth() {
		return this.height;
	}
	
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.getBody().getPosition().add(new Vec2(-(width/2),-(height/2))));
	}
}