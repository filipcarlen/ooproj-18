package model;

import static org.junit.Assert.*;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.junit.Test;

import utils.Utils;
/**
 * 
 * @author filipcarlen
 *
 */
public class CoinModelTest {
	
	@Test
	public void testCreateCollectible() {
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getBody().getType() == BodyType.DYNAMIC);
	}
	
	@Test
	public void testGetRadius(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getRadius() == 15f);
	}
	
	@Test
	public void testDestroyBody(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		int bodies = coin.getBody().getWorld().getBodyCount();
		coin.destroyBody();
		assertTrue(coin.getBody().getWorld().getBodyCount() == bodies-1);
	}
	
	@Test
	public void testKillExists(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		coin.killBody();
		assertTrue(!(coin.bodyExists()));
	}
	
	@Test
	public void testBodyExists(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.bodyExists() == true);
	}
	
	@Test
	public void testGetID(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getID() == 0);
	}
	
	@Test
	public void testGetPosMeters(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getPosMeters().equals(Utils.pixelsToMeters(new Vec2(100,100))));
	}
	
	@Test
	public void testGetPosPixels(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getPosPixels().equals
				(Utils.metersToPixels(coin.getBody().getPosition().add(new Vec2(-0.5f,-0.5f)))));
	}
	
	@Test
	public void testGetBody(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getBody().equals(coin.getBody()));
	}
	
	@Test
	public void testGetValue(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getValue() == 1);
	}
	
	@Test
	public void testSetValue(){
		CoinModel coin = new CoinModel(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		coin.setValue(4);
		assertTrue(coin.getValue() == 4);
	}
}
