package model;

import static org.junit.Assert.*;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.junit.BeforeClass;
import org.junit.Test;
import org.newdawn.slick.geom.Vector2f;

import utils.Navigation;
import utils.Utils;
/**
 * 
 * @author filipcarlen
 *
 */
public class HeroModelTest {

	/**@Test
	public void testAttack() {
		fail("Not yet implemented");
	}*/

	@Test
	public void testCollectCoin() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		hero.collectCoin(1);
		assertTrue(hero.collectedItem == 1);
	}

	@Test
	public void testGetDirection() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		hero.setDirection(Navigation.EAST);
		assertTrue(hero.navigation == Navigation.EAST);
	}

	@Test
	public void testGetDoubleJump() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.getDoubleJump() == 0);
	}

	@Test
	public void testGetHeight() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.getHeight() == 50/Utils.METER_IN_PIXELS);
	}

	@Test
	public void testGetHp() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.getHp() == 100);
	}

	@Test
	public void testGetID() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.getID() == -1);
	}

	@Test
	public void testGetMaxHp() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.getMaxHp() == 100);
	}

	@Test
	public void testGetName() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.getName().equals("Character"));
	}

	@Test
	public void testGetPosMeters() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.getPosMeters() == hero.getBody().getPosition());
	}

	@Test
	public void testGetPosPixels() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		float tmp = 50/2/Utils.METER_IN_PIXELS;
		assertTrue(hero.getPosPixels().
				equals(hero.getBody().getPosition().add(new Vec2(tmp,tmp).mul(-1)).mul(Utils.METER_IN_PIXELS)));
	}

	@Test
	public void testGetWeapon() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWeaponType() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetWidth() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.getWidth() == 50/Utils.METER_IN_PIXELS);
	}

	@Test
	public void testHurt() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		hero.hurt(5);
		assertTrue(hero.getHp() == 95);
	}

	@Test
	public void testIncrementJumps() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		hero.incrementJumps();
		assertTrue(hero.doubleJump == 1);
	}

	@Test
	public void testIsDead() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		assertTrue(hero.isDead() == false);
	}

	@Test
	public void testSetGroundContact() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		hero.incrementJumps();
		hero.setGroundContact();
		assertTrue(hero.doubleJump == 0);
		
	}

	@Test
	public void testSetDirection() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		hero.setDirection(Navigation.EAST);
		assertTrue(hero.getDirection() == Navigation.EAST);
	}

	@Test
	public void testSetHp() {
		HeroModel hero = new HeroModel(new World(new Vec2(0.0f, 9.8f)),"Character");
		hero.setHp(105);
		assertTrue(hero.getHp() == 100);
		hero.setHp(95);
		assertTrue(hero.getHp() == 95);
	}

	@Test
	public void testSetWeapon() {
		fail("not yet implemented");
	}

}
