package se.chalmers.grupp18.v01;


public class GunModel extends AbstractWeaponModel{
	
	private BulletModel bullet;
	
	public GunModel(){
		super();
		bullet = new BulletModel();
	}
	public GunModel(int damage){
		this();
		super.setDamage(damage);
	}
	public GunModel(int damage, int range){
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
		bullet.fight();
	}
}
