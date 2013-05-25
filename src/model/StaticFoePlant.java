package model;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

public class StaticFoePlant extends AbstractStaticFoe{
	
	public StaticFoePlant(World world, Vec2 pixelPos, int damage, int ID) {
		super(world, pixelPos.clone(), damage, ID, 30, 30);
		init();
	}
	
	public void init() {
		PolygonShape polyShape = new PolygonShape();
		polyShape.setAsBox(Utils.pixelsToMeters(super.WIDTH)/2, Utils.pixelsToMeters(super.HEIGHT)/2);
		super.fixDef.shape = polyShape;
		
		super.body.createFixture(super.fixDef);
	}
}
