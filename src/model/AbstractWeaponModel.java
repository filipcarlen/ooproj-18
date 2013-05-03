package model;

import org.jbox2d.common.Vec2;

public abstract class AbstractWeaponModel {
	
	private int damage;
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
	
	public boolean isWithinRange(Vec2 enemy, Vec2 character){
		
		if(Vec2.abs(enemy.sub(character)).length() < this.range){
			return true;
		} else{
			return false;
		}
		
	}
	
	
	public abstract void fight();

}
