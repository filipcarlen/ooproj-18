package se.chalmers.grupp18.v01;


public class Gun extends Weapon{
	
	private Bullet bullet;
	
	public Gun(){
		super();
		bullet = new Bullet();
	}
	public Gun(int damage){
		this();
		super.setDamage(damage);
	}
	public Gun(int damage, int range){
		this();
		super.setDamage(damage);
		super.setRange(range);
	}

	public int getDamage(){
		return bullet.getDamage();
	}
	public void setDamage(int damage){
		bullet.setDamage(damage);
	}
	public int getRange(){
		return bullet.getRange();
	}
	public void setRange(int range){
		bullet.setRange(range);
	}
	public void fight(){
		
	}
}
