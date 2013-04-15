package se.chalmers.grupp18.v01;

public class CharacterModel {
	
	private float xpos,ypos;
	private double speed;
	private int hp;
	private Weapon weapon;
	
	public CharacterModel(){
		this.hp = 100;
		this.weapon = new Sword(20);
		
	}
	
	public CharacterModel(int hp){
		
	}
	
	public CharacterModel(int hp, Weapon weapon){
		
	}
	
	public void setWeapon(Weapon weapon){
		
	}
	
	public void setXpos(float xpos){
		this.xpos = xpos;
	}
	
	public void setYpos(float ypos){
		this.ypos = ypos;
	}
	
	public float getXpos(){
		return this.xpos;
	}
	
	public float getYpos(){
		return this.ypos;
	}
	
}
