package se.chalmers.grupp18.v01;

public class Sword extends Weapon{

	private int range;
	private int damage;
	
	public Sword(){
		this.range = 10;
		this.damage = 20;
	}
	public Sword(int range){
		this();
		this.range = range;
	}
	public Sword(int range, int damage){
		this(range);
		this.damage = damage;
	}
	
	public int getDamage(){
		return damage;
	}
	public void setDamage(int damage){
		this.damage = damage;
	}
	public int getRange(){
		return range;
	}
	public void setRange(int range){
		this.range = range;
	}
}
