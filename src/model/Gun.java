package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Navigation;
import utils.WeaponType;

/** A class representing a Gun
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class Gun extends AbstractWeapon implements ActionListener{
	
	private Timer timer;
	private int ID;
	private int IDCount;
	private ArrayList<Bullet> bulletModels = new ArrayList<Bullet>();

	public Gun(World world, int reloadTime, int damage, float range, int ID){
		super(world, damage, range, WeaponType.GUN);
		this.IDCount = 1;
		this.ID = ID;
		this.timer = new Timer(reloadTime, this);
	}

	public boolean fight(IAliveEntity fighterModel, Navigation navigation){
		super.setFighterModel(fighterModel);
		Vec2 firstPos = fighterModel.getPosMeters().clone();
		
		if(navigation == Navigation.EAST){
			firstPos.x += fighterModel.getWidth()/2;
		}
		
		if(navigation == Navigation.WEST){
			firstPos.x -= fighterModel.getWidth()/2;
		}
		
		if(!timer.isRunning()){
			
			bulletModels.add(new Bullet(this, firstPos, navigation, this.IDCount));
			
			if(IDCount >= 50){
				this.IDCount = 1;
			} else{
				this.IDCount++;
			}
			
			timer.start();
			return true;
		}
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.stop();
	}
	
	/**
	 * A method called to check if the Gun and all the bullets are ready to be removed from the world. 
	 * Returns true when no bullet is in action.
	 */
	public boolean isDone(){
		for(int i = 0; i < bulletModels.size(); i++){
			if(bulletModels.get(i).isAlive()){
				return false;
			}
		}
		return true;
	}
	
	public void removeBullet(int index){
		this.bulletModels.remove(index);
	}
	
	public ArrayList<Bullet> getBulletModels(){
		return this.bulletModels;
	}
	
	public IAliveEntity getFighterModel(){
		return super.getFighterModel();
	}
	
	public int getID(){
		return this.ID;
	}
	
}
