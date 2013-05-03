package model;

import utils.Utils;


public class GunModel extends AbstractWeaponModel{
	
	private BulletModel bullet;
	
	public GunModel(){
		this(20, 400f);
		bullet = new BulletModel();
	}
	public GunModel(int damage){
		this(damage, 400f);
		bullet = new BulletModel();
		
	}
	public GunModel(int damage, float range){
		super(damage, range);
	}

	public int getDamage(){
		return bullet.getDamage();
	}
	public void setDamage(int damage){
		bullet.setDamage(damage);
	}
	public float getRange(){
		return bullet.getRange();
	}
	public void setRange(float range){
		bullet.setRange(range);
	}
	public void fight(){
		bullet.fight();
	}
}
