package model;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Navigation;
import utils.WeaponType;


/** A class representing a Gun
 * 
 * @author elinljunggren
 * @version 1.0 
 */


public class GunModel extends AbstractWeaponModel{
	
	private double reloadTime;
	private ArrayList<BulletModel> bulletModels;
	//private Vec2 firstPos;
	
	public GunModel(ArrayList<BulletModel> bulletModels, World world, double reloadTime){
		this(bulletModels, world, reloadTime, 20, 400f);
		
	}
	public GunModel(ArrayList<BulletModel> bulletModels, World world, double reloadTime, int damage){
		this(bulletModels, world, reloadTime, damage, 400f);
		
	}
	public GunModel(ArrayList<BulletModel> bulletModels, World world, double reloadTime, int damage, float range){
		super(world, damage, range);
		super.setWeaponType(WeaponType.gun);
		this.bulletModels = bulletModels;
		this.reloadTime = reloadTime;
		
	}


	public void fight(Vec2 myPos, Navigation navigation){
		// är timern igång??
		for(int i = 0; i < bulletModels.size(); i++){
			if(!bulletModels.get(i).getBody().isActive()){
				bulletModels.get(i).fight(myPos, navigation);
				// timer startas
				return ;
			}
			
		}
		
	}
	
	public double getReloadTime(){
		return this.reloadTime;
	}
	
	public ArrayList<BulletModel> getBulletModels(){
		return this.bulletModels;
		
	}
	
	/*public Vec2 getFirstPos(){
		return this.firstPos;
	}*/
}
