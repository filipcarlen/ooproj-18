package model;

import static org.junit.Assert.*;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.junit.Test;

import utils.Navigation;

public class SwordTest {

	@Test
	public void testFight() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		sword.destroyEntity();
		sword.fight(new Hero(new World(new Vec2(0.0f, 9.8f)), "player", new Vec2(0,0)), Navigation.EAST);
		assertTrue(sword.getNavigation() == Navigation.EAST);
		assertTrue(sword.isAlive() == true);
	}

	@Test
	public void testDestroyEntity() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		sword.fight(new Hero(new World(new Vec2(0.0f, 9.8f)), "player", new Vec2(0,0)), Navigation.EAST);
		sword.setMoving(true);
		sword.destroyEntity();
		assertTrue(sword.isMoving() == false);
		assertTrue(sword.isMoving() == false);
	}

	@Test
	public void testIsAlive() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		sword.destroyEntity();
		sword.fight(new Hero(new World(new Vec2(0.0f, 9.8f)), "player", new Vec2(0,0)), Navigation.EAST);
		assertTrue(sword.isAlive() == true);
	}

	@Test
	public void testIsMoving() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		sword.setMoving(true);
		assertTrue(sword.isMoving() == true);	}

	@Test
	public void testSetMoving() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		sword.setMoving(false);
		assertTrue(sword.isMoving() == false);
	}
	@Test
	public void testGetFirstPos() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		sword.fight(new Hero(new World(new Vec2(0.0f, 9.8f)), "player", new Vec2(2,3)), Navigation.EAST);
		assertTrue(sword.getFirstPos().x == 2);
		assertTrue(sword.getFirstPos().y == 3);
	}

	@Test
	public void testGetNavigation() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		sword.fight(new Hero(new World(new Vec2(0.0f, 9.8f)), "player", new Vec2(0,0)), Navigation.EAST);
		assertTrue(sword.getNavigation() == Navigation.EAST);
	}

	@Test
	public void testGetID() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		assertTrue(sword.getID() == 76);
	}

	@Test
	public void testGetHeight() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		assertTrue(sword.getHeight() == 0.3f);
	}

	@Test
	public void testGetWidth() {
		Sword sword = new Sword(new World(new Vec2(0.0f, 9.8f)), 1000, 20, 76);
		assertTrue(sword.getWidth() == 0.3f);
	}

}
