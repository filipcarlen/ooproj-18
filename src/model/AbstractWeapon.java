package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Navigation;
import utils.WeaponType;

/** A class representing a Weapon
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public abstract class AbstractWeapon {
	
	private int damage;
	/* The range in meters */
	private float range;
	private World world;
	private WeaponType weaponType;
	private IAliveEntity fighterModel;
	
	public AbstractWeapon(World world, int damage, float range, WeaponType weaponType){
		this.world = world;
		this.damage = damage;
		this.range = range;
		this.weaponType = weaponType;
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
	
	
	public IAliveEntity getFighterModel(){
		return this.fighterModel;
	}
	
	public void setFighterModel(IAliveEntity fighterModel){
		this.fighterModel = fighterModel;
	}
	
	public WeaponType getWeaponType(){
		return this.weaponType;
	}
	
	public void setWeaponType(WeaponType weaponType){
		this.weaponType = weaponType;
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
	
	public abstract boolean fight(IAliveEntity fighterModel,  Navigation navigation);
}
