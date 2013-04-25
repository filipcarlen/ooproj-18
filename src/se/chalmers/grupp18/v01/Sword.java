package se.chalmers.grupp18.v01;

import org.jbox2d.common.Vec2;

public class Sword extends Weapon{

	
	public Sword(){
		super();
	}
	public Sword(int damage){
		this();
		super.setDamage(damage);
	}
	public Sword(int damage, int range){
		this(damage);
		super.setRange(range);
	}

	public void fight(){
		
	}

}
