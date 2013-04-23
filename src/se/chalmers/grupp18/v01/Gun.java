package se.chalmers.grupp18.v01;

public class Gun extends Weapon{
	
	private Bullet bullet;
	
	public Gun(){
		bullet = new Bullet();
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
}
