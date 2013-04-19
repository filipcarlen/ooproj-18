package se.chalmers.grupp18.v01;

public class Bullet {

	private int range;
	private int damage;
	
	public Bullet(){
		this.range = 30;
		this.damage = 20;
	}
	public Bullet(int range){
		this();
		this.range = range;
	}
	public Bullet(int range, int damage){
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
