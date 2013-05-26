package test;

import model.StaticFoePlant;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.junit.Test;

public class StaticFoePlantTest {

	World world = new World(new Vec2(0, 9.82f));
	
	@Test
	public void testGetPosPixels() {
		StaticFoePlant plant = new StaticFoePlant(world, new Vec2(130, 150), 20, 1);
		assert(plant.getPosPixels().x == 130 && plant.getPosPixels().y == 150);
	}

	@Test
	public void testGetPosMeters() {
		StaticFoePlant plant = new StaticFoePlant(world, new Vec2(130, 150), 20, 1);
		assert(plant.getPosMeters().x == 130f/30f && plant.getPosMeters().y == 150f/30f);
	}

	@Test
	public void testGetHeight() {
		StaticFoePlant plant = new StaticFoePlant(world, new Vec2(130, 150), 20, 1);
		assert(plant.getHeight() == 1);
	}

	@Test
	public void testGetWidth() {
		StaticFoePlant plant = new StaticFoePlant(world, new Vec2(130, 150), 20, 1);
		assert(plant.getWidth() == 1);
	}

	@Test
	public void testGetID() {
		StaticFoePlant plant = new StaticFoePlant(world, new Vec2(130, 150), 20, 1);
		assert(plant.getID() == 1);
	}

	@Test
	public void testGetDamage() {
		StaticFoePlant plant = new StaticFoePlant(world, new Vec2(130, 150), 20, 1);
		assert(plant.getDamage() == 20);
	}
}
