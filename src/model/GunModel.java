package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Navigation;
import utils.WeaponType;

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
		super.setWeaponType(WeaponType.gun);
		
	}


	public void fight(Vec2 myPos, Navigation navigation){
		BulletModel model = new BulletModel(super.getWorld(), myPos, navigation, super.getRange(), super.getDamage());
		new BulletController(model);
	}
}
