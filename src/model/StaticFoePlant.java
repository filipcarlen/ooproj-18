package model;


import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

public class StaticFoePlant extends AbstractStaticFoe{
	
	public StaticFoePlant(World world, Vec2 pixelPos, int damage, int ID) {
		super(world, pixelPos.clone(), damage, ID, 30, 30);
		init();
	}
	
	public void init() {
		CircleShape circleShape = new CircleShape();
		circleShape.m_radius = Utils.pixelsToMeters(super.WIDTH)/2;
		super.fixDef.shape = circleShape;
		super.body.createFixture(super.fixDef);
	}
}
