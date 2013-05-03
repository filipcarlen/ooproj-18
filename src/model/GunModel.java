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

>>>>>>> Changed the constructors in all Weapon classes, added method init(Vec2 heroPos) to BulletModel

public class GunModel extends AbstractWeaponModel{
	
	private BulletModel bullet;
	
<<<<<<< HEAD
	public GunModel(){
		super();
		bullet = new BulletModel();
	}
	public GunModel(int damage){
		this();
		super.setDamage(damage);
	}
	public GunModel(int damage, int range){
		this();
		super.setDamage(damage);
		super.setRange(range);
=======
	public GunModel(World world){
		this(world, 20, 400f);
	}
	public GunModel(World world,int damage){
		this(world, damage, 400f);
		
	}
	public GunModel(World world, int damage, float range){
		super(world, damage, range);
		this.bullet = new BulletModel(super.getDamage(), super.getRange(), super.getWorld(), PlayState.getHeroModel().getPosPixels());
>>>>>>> Changed the constructors in all Weapon classes, added method init(Vec2 heroPos) to BulletModel
	}

	public int getDamage(){
		return this.bullet.getDamage();
	}
	public void setDamage(int damage){
		this.bullet.setDamage(damage);
	}
<<<<<<< HEAD
	public int getRange(){
		return bullet.getRange();
	}
	public void setRange(int range){
		bullet.setRange(range);
=======
	public float getRange(){
		return this.bullet.getRange();
	}
	public void setRange(float range){
		this.bullet.setRange(range);
>>>>>>> Changed the constructors in all Weapon classes, added method init(Vec2 heroPos) to BulletModel
	}
	public void fight(){
		this.bullet.fight();
	}
}
