package se.chalmers.grupp18.v01;

import org.newdawn.slick.SlickException;

public class CharacterModel {
	
	private float xpos,ypos;
	private int hp;
	private Weapon weapon;
	
	public CharacterModel(){
		this.hp = 100;
		this.weapon = new Sword(20);
	}
	
	public CharacterModel(int hp) throws SlickException{
		this();
		
		if(hp > 0 && hp <= 100){
			this.hp = hp;
		} else {
			throw new SlickException("Character can't be initiated dead or too healthy.");
		}
	}
	
	public CharacterModel(int hp, Weapon weapon) throws SlickException{
		this(hp);
		this.weapon = weapon;
	}
	
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public void fight(){
		this.weapon.fight();
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
