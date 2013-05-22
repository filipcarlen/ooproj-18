package model;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

public abstract class AbstractPowerUpModel extends AbstractCollectibleModel {

	private float hpBoost;
	private float width = 1.0f;
	private float height = 1.0f;
	
	public AbstractPowerUpModel(World w, Vec2 pixelPos, int id) {
		super(w, pixelPos, id);
		
		PolygonShape polygon = new PolygonShape();
	   	polygon.setAsBox(width/2, height/2);
	   	
	   	this.getFixtureDef().shape = polygon;
		this.getBody().createFixture(this.getFixtureDef());
	}
	
	public float gethpBoost() {
		return this.hpBoost;
	}

	public void setHpBoost(float hpBoost) {
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