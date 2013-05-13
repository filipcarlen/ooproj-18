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
	
	private BulletModel bulletModel;
	
	public GunModel(BulletModel bulletModel, World world){
		this(bulletModel, world, 20, 400f);
		
	}
	public GunModel(BulletModel bulletModel, World world, int damage){
		this(bulletModel, world, damage, 400f);
		
	}
	public GunModel(BulletModel bulletModel, World world, int damage, float range){
		super(world, damage, range);
		super.setWeaponType(WeaponType.gun);
		this.bulletModel = bulletModel;
		
	}


	public void fight(Vec2 myPos, Navigation navigation){
		
		bulletModel.getBody().setActive(true);
		//BulletModel model = new BulletModel(super.getWorld(), myPos, navigation, super.getRange(), super.getDamage());
		//new BulletController(model);
	}
}
