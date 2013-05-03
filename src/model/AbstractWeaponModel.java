package model;

import org.jbox2d.common.Vec2;
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
	
	public boolean isWithinRange(Vec2 enemy, Vec2 character){
		
		if(Vec2.abs(enemy.sub(character)).length() < this.range){
			return true;
		} else{
			return false;
		}
		
	}
	
	
	public abstract void fight();

	

}
