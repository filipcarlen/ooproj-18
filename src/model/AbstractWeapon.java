package model;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import utils.Navigation;
import utils.WeaponType;

/**
 *  A class representing a Weapon
 * 
 * @author Project Group 18 (Chalmers, 2013)
 */

public abstract class AbstractWeapon {
	
	private int damage;
	/* The range in meters */
	private float range;
	private World world;
	private WeaponType weaponType;
	/* The model of the character using this weapon */
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
	
	/* This method is not used now but supports a future update of the weapons */
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public float getRange(){
		return this.range;
	}
	
	/* This method is not used now but supports a future update of the weapons */
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
	
	/**
	 * This method checks if the Hero is within range for the moving foe to attack
	 * @param foeBody, heroBody
	 * @return true if the hero is within range
	 */
	public boolean isWithinRange(Body foeBody, Body heroBody){
		float tempRange = this.range + foeBody.getFixtureList().getShape().getRadius() + heroBody.getFixtureList().getShape().getRadius();
		float distance = Math.abs((foeBody.getPosition().sub(heroBody.getPosition())).length());
		if(distance < tempRange){
			return true;
		} else{
			return false;
		}
	}
	
	/**
	 * This method is called by the fighterModel(the character using the weapon) every time he/she/it wants to fight
	 * @param fighterModel, navigation
	 * @return true if the character is allowed to fight
	 */
	public abstract boolean fight(IAliveEntity fighterModel,  Navigation navigation);
}
