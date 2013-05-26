package test;

import model.StaticFoeFire;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.junit.Test;

public class StaticFoeFireTest {

	World world = new World(new Vec2(0, 9.82f));
	
	@Test
	public void testGetPosPixels() {
		StaticFoeFire fire = new StaticFoeFire(world, new Vec2(130, 150), 20, 1);
		assert(fire.getPosPixels().x == 130 && fire.getPosPixels().y == 150);
	}

	@Test
	public void testGetPosMeters() {
		StaticFoeFire fire = new StaticFoeFire(world, new Vec2(130, 150), 20, 1);
		assert(fire.getPosMeters().x == 130f/30f && fire.getPosMeters().y == 150f/30f);
	}

	@Test
	public void testGetHeight() {
		StaticFoeFire fire = new StaticFoeFire(world, new Vec2(130, 150), 20, 1);
		assert(fire.getHeight() == 1);
	}

	@Test
	public void testGetWidth() {
		StaticFoeFire fire = new StaticFoeFire(world, new Vec2(130, 150), 20, 1);
		assert(fire.getWidth() == 1);
	}

	@Test
	public void testGetID() {
		StaticFoeFire fire = new StaticFoeFire(world, new Vec2(130, 150), 20, 1);
		assert(fire.getID() == 1);
	}

	@Test
	public void testGetDamage() {
		StaticFoeFire fire = new StaticFoeFire(world, new Vec2(130, 150), 20, 1);
		assert(fire.getDamage() == 20);
	}
}
