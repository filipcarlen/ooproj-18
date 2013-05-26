package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Navigation;
import utils.WeaponType;

/** 
 * A class representing a Gun
 * 
 * @author Project Group 18 (Chalmers, 2013)
 */

public class Gun extends AbstractWeapon implements ActionListener{
	
	private int ID;
	private int IDCount;
	private Timer reloadTimer;
	private ArrayList<Bullet> bulletModels = new ArrayList<Bullet>();

	public Gun(World world, int reloadTime, int damage, float range, int ID){
		super(world, damage, range, WeaponType.GUN);
		this.ID = ID;
		this.IDCount = 1;
		this.reloadTimer = new Timer(reloadTime, this);
	}

	@Override
	public boolean fight(IAliveEntity fighterModel, Navigation navigation){
		super.setFighterModel(fighterModel);
		Vec2 firstPos = fighterModel.getPosMeters().clone();
		
		/* Sets the first position of the bullet depending on the direction of the fighter */
		if(navigation == Navigation.EAST){
			firstPos.x += fighterModel.getWidth()/2;
		}
		
		if(navigation == Navigation.WEST){
			firstPos.x -= fighterModel.getWidth()/2;
		}
		
		/* Shoots a new bullet if the reloadTimer isn't on */
		if(!reloadTimer.isRunning()){
			
			bulletModels.add(new Bullet(this, firstPos, navigation, this.IDCount));
			
			if(IDCount >= 50){
				this.IDCount = 1;
			} else{
				this.IDCount++;
			}
			
			reloadTimer.start();
			return true;
		}
		return false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		reloadTimer.stop();
	}
	
	/**
	 * A method called to check if the Gun and all the bullets are ready to be removed from the world. 
	 * Returns true when no bullet is in action.
	 */
	public boolean isDone(){
		if(bulletModels.size() > 0){
			return false;
		}
		return true;
	}
	
	/**
	 * @param index of the bullet that should be removed
	 */
	public void removeBullet(int index){
		this.bulletModels.remove(index);
	}
	
	/**
	 * @return list of all the bullets this gun has fired that still exists in the world
	 */
	public ArrayList<Bullet> getBulletModels(){
		return this.bulletModels;
	}
	
	/**
	 * @return ID of this Gun
	 */
	public int getID(){
		return this.ID;
	}
	
}
