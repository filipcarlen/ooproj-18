package se.chalmers.grupp18.v01;

public abstract class Weapon {
	
	private int damage;
	
	public Weapon(){
		this.damage = 20;
	}
	
	public Weapon(int damage){
		this.damage = damage;
	}
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public int getDamage(){
		return this.damage;
	}
	
	public abstract void fight();

}
