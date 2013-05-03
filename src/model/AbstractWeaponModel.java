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
<<<<<<< HEAD
	private int range;
	
	public AbstractWeaponModel(){
		this.damage = 20;
		this.range = 400;
		
	}
	public AbstractWeaponModel(int damage){
		this();
		this.damage = damage;
	}
	public AbstractWeaponModel(int damage, int range){
=======
	private float range;
	private World world;
	
	
	public AbstractWeaponModel(World world, int damage, float range){
		this.world = world;
>>>>>>> Changed the constructors in all Weapon classes, added method init(Vec2 heroPos) to BulletModel
		this.damage = damage;
		this.range = range;
	}
	
	public int getDamage(){
		return this.damage;
	}
	public void setDamage(int damage){
		this.damage = damage;
	}
	public int getRange(){
		return this.range;
	}
	public void setRange(int range){
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
