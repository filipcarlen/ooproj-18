package model;

import org.jbox2d.common.Vec2;

public abstract class AbstractWeaponModel {
	
	private int damage;
	private float range;
	
	
	public AbstractWeaponModel(int damage, float range){
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
	
	public boolean isWithinRange(Vec2 enemy, Vec2 character){
		
		if(Vec2.abs(enemy.sub(character)).length() < this.range){
			return true;
		} else{
			return false;
		}
		
	}
	
	
	public abstract void fight();

}
