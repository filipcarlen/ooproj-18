package test;

import static org.junit.Assert.*;
import model.Coin;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.junit.Test;

import utils.Utils;

public class CoinTest {

	@Test
	public void testCreateCollectible() {
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getBody().getType() == BodyType.DYNAMIC);
	}
	
	@Test
	public void testDestroyBody(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		int bodies = coin.getBody().getWorld().getBodyCount();
		coin.destroyBody();
		assertTrue(coin.getBody().getWorld().getBodyCount() == bodies-1);
	}
	
	@Test
	public void testKillExists(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		coin.killBody();
		assertTrue(!(coin.bodyExists()));
	}
	
	@Test
	public void testBodyExists(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.bodyExists() == true);
	}
	
	@Test
	public void testGetID(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getID() == 0);
	}
	
	@Test
	public void testGetPosMeters(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getPosMeters().equals(Utils.pixelsToMeters(new Vec2(100,100))));
	}
	
	@Test
	public void testGetPosPixels(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getPosPixels().equals
				(Utils.metersToPixels(coin.getBody().getPosition().add(new Vec2(-0.5f,-0.5f)))));
	}
	
	@Test
	public void testGetBody(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getBody().equals(coin.getBody()));
	}
	
	@Test
	public void testGetValue(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		assertTrue(coin.getValue() == 1);
	}
	
	@Test
	public void testSetValue(){
		Coin coin = new Coin(new World(new Vec2(0.0f, 9.8f)), new Vec2(100,100), 0);
		coin.setValue(4);
		assertTrue(coin.getValue() == 4);
	}
}

