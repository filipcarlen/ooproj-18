package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import utils.Navigation;
import utils.WeaponType;


/** A class representing a Gun
 * 
 * @author elinljunggren
 * @version 1.0 
 */


public class GunModel extends AbstractWeaponModel implements ActionListener{
	
	private Timer timer;
	private int reloadTime;
	private ArrayList<BulletModel> bulletModels = new ArrayList<BulletModel>();
	//private Vec2 firstPos;
	
	public GunModel(World world, int reloadTime){
		this(world, reloadTime, 20, 400f);
		
	}
	public GunModel(World world, int reloadTime, int damage){
		this(world, reloadTime, damage, 400f);
		
	}
	public GunModel(World world, int reloadTime, int damage, float range){
		super(world, damage, range);
		super.setWeaponType(WeaponType.gun);
		for(int i = 1; i <= 10; i++){
			bulletModels.add(new BulletModel(super.getWorld(), super.getRange(), super.getDamage(), i));
		}
		this.reloadTime = reloadTime;
		this.timer = new Timer(reloadTime, this);
		
	}


	public void fight(Body fighterBody, Navigation navigation){
		System.out.println("fight() in GunModel");

		
		// Šr timern igŒng??
		if(!timer.isRunning()){
			for(int i = 0; i < bulletModels.size(); i++){
				if(bulletModels.get(i).getBody() == null){
					bulletModels.get(i).fight(fighterBody, navigation);
					// timer startas
					timer.start();
					return ;
				}
			
			}
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.stop();
		
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
