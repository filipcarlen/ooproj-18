package test;

import model.MovingFoe;
import model.Sword;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.junit.Test;

public class MovingFoeTest {

	World world = new World(new Vec2(0, 9.82f));
	
	@Test
	public void testGetPosMeters() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		assert(foe.getPosMeters().x == 130 && foe.getPosMeters().y == 150);
	}

	@Test
	public void testGetPosPixels() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		assert(foe.getPosPixels().x == 130 && foe.getPosPixels().y == 150);
	}

	@Test
	public void testGetHp() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		assert(foe.getHp() == 50);
	}

	@Test
	public void testSetHp() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		foe.setHp(30);
		assert(foe.getHp() == 30);
	}

	@Test
	public void testGetMaxHp() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		assert(foe.getMaxHp() == 50);
	}

	@Test
	public void testHurt() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		foe.hurt(20);
		assert(foe.getMaxHp() == 30);
	}

	@Test
	public void testGetID() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		assert(foe.getID() == 1);
	}

	@Test
	public void testGetValue() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		assert(foe.getValue() == 10);
	}
	
	@Test
	public void testIsAlive() {
		MovingFoe foe = new MovingFoe(world, new Vec2(130, 150), 50, new Sword(world, 200, 20, 1), 10, 1);
		foe.hurt(60);
		assert(!foe.isAlive());
	}

}
