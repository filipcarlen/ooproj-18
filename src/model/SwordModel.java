package model;

import org.jbox2d.common.Vec2;

import utils.Utils;

public class SwordModel extends AbstractWeaponModel{

	
	public SwordModel(){
		this(20, Utils.METER_IN_PIXELS);
	}
	public SwordModel(int damage){
		this(damage, Utils.METER_IN_PIXELS);
	}
	public SwordModel(int damage, float range){
		super(damage, range);
	}

	public void fight(){
		
	}

}
