package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import utils.Utils;

/** A class representing a Sword
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class SwordModel extends AbstractWeaponModel{

	
	public SwordModel(World world){
		this(world, 20, Utils.METER_IN_PIXELS);
	}
	public SwordModel(World world, int damage){
		this(world, damage, Utils.METER_IN_PIXELS);
	}
	public SwordModel(World world, int damage, float range){
		super(world, damage, range);
	}

	public void fight(){
		
	}

}
