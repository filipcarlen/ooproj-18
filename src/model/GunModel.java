package model;

<<<<<<< HEAD
=======
import org.jbox2d.dynamics.World;

import states.PlayState;
import utils.Utils;

/** A class representing a Gun
 * 
 * @author elinljunggren
 * @version 1.0 
 */


public class GunModel extends AbstractWeaponModel{
	
	private BulletModel bullet;
	
	public GunModel(World world){
		this(world, 20, 400f);
	}
	public GunModel(World world,int damage){
		this(world, damage, 400f);
		
	}
	public GunModel(World world, int damage, float range){
		super(world, damage, range);
		this.bullet = new BulletModel(super.getDamage(), super.getRange(), super.getWorld(), PlayState.getHeroModel().getPosPixels());
	}

	public int getDamage(){
		return this.bullet.getDamage();
	}
	public void setDamage(int damage){
		this.bullet.setDamage(damage);
	}
	public float getRange(){
		return this.bullet.getRange();
	}
	public void setRange(float range){
		this.bullet.setRange(range);
	}
	
	public void fight(){
		this.bullet.fight();
	}
}
