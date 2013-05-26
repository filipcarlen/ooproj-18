package model;

import static org.junit.Assert.*;

import model.EnergyDrink;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.junit.Test;

public class EnergyDrinkTest {

	@Test
	public void testGethpBoost() {
		EnergyDrink ed = new EnergyDrink(new World(new Vec2(0.0f, 9.8f),new Vec2(0,0),0);
		ed.setHpBoost(5);
		assertTrue(ed.gethpBoost() == 5);
	}
}
