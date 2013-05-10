package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import controller.BulletController;


/** A class representing a Gun
 * 
 * @author elinljunggren
 * @version 1.0 
 */


public class GunModel extends AbstractWeaponModel{
	
	
	public GunModel(World world){
		this(world, 20, 400f);
	}
	public GunModel(World world, int damage){
		this(world, damage, 400f);
		
	}
	public GunModel(World world, int damage, float range){
		super(world, damage, range);
	}


	public void fight(Vec2 myPos, Vec2 targetPos){
		BulletModel model = new BulletModel(super.getWorld(), myPos, targetPos, super.getRange(), super.getDamage());
		new BulletController(model);
	}
}
