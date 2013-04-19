package se.chalmers.grupp18.v01;

public class Gun extends Weapon{
	private Bullet bullet;

	public int damage(){
		return bullet.damage();
	}
}
