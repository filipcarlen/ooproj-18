package se.chalmers.grupp18.v01;

import org.jbox2d.common.Vec2;

public class SwordModel extends AbstractWeaponModel{

	
	public SwordModel(){
		super();
	}
	public SwordModel(int damage){
		this();
		super.setDamage(damage);
	}
	public SwordModel(int damage, int range){
		this(damage);
		super.setRange(range);
	}

	public void fight(){
		
	}

}
