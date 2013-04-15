package se.chalmers.grupp18.v01;

public class Sword extends Weapon {

	//Range in number of pixels
	private int range;
	
	public Sword(){
		super();
		this.range = 20;
	}
	
	public Sword(int damage){
		super.setDamage(damage);
	}
	
	@Override
	public void fight() {
		// TODO Auto-generated method stub
		
	}
	
}
