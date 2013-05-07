package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

/** A class representing a Weapon
 * 
 * @author elinljunggren
 * @version 1.0 
 */


public abstract class AbstractWeaponModel {
	
	private int damage;
	private float range;
	private World world;
	
	
	public AbstractWeaponModel(World world, int damage, float range){
		this.world = world;
		this.damage = damage;
		this.range = range;
	}
	
	public int getDamage(){
		return this.damage;
	}
	public void setDamage(int damage){
		this.damage = damage;
	}
	public float getRange(){
		return this.range;
	}
	public void setRange(float range){
		this.range = range;
	}
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public boolean isWithinRange(Vec2 myPos, Vec2 targetPos){
		
		if((myPos.sub(targetPos)).length() < this.range){
			return true;
		} else{
			return false;
		}
		
	}
	
	public static Body getFighterBody(World world, Vec2 firstPos){
		// All bodies in the world
		Body worldBodyList = world.getBodyList();
		// Have to loop through all the bodies in the world and compare their position to the position 
		// from which the bullet is fired to get the body of the shooting character. 
		while(true){
			Body nextBody = worldBodyList.getNext();
			Vec2 nextBodyPos = nextBody.getPosition();
			if(nextBodyPos == firstPos){
				return nextBody;
			} else if(nextBody == null){
				throw new NullPointerException("No body in the world was found at the bullets first position");
			}
		}
	}
	
	public abstract void fight(Vec2 myPos, Vec2 targetPos);

	

}
