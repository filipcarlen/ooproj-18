package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.HeroModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import view.HeroView;

/**
 * Version: 1.0
 * A controller for the Character
 * @author Marcus
 *
 */

public class HeroController implements IEntityController{
	
	static final String CMD_LEFT = "left";
	static final String CMD_RIGHT = "right";
	static final String CMD_JUMP = "jump";
	static final String CMD_FIGHT = "fight";
	Input input;
	boolean jump;
	int jumpCount = 20;
	int doubleJump = 0;
	HeroModel hero;
	HeroView pa;
	HashMap<String, List<Integer>>keys = new HashMap<String, List<Integer>>();
	
	
	public HeroController(HeroModel ce){
		hero = ce;
		pa = new HeroView(ce.getName());
		setControls();
	}
	
	/**
	 * This is a method to give the default setting to the key inputs
	 */
	public void setControls(){
		bindToKey(CMD_LEFT, Input.KEY_A, Input.KEY_LEFT);
		bindToKey(CMD_RIGHT, Input.KEY_D, Input.KEY_RIGHT);
		bindToKey(CMD_JUMP, Input.KEY_W, Input.KEY_UP);
		bindToKey(CMD_FIGHT, Input.KEY_SPACE);
	}
	
	/**
	 * this removes all the settings the key bindings
	 */
	public void removeKeySettings(){
		keys.clear();
	}
	
	/**
	 * here you bind a keys to the different functions
	 * @param s - the name of a command
	 * @param key - Gives the integer to the key 
	 */
	public void bindToKey(String s, int key){
		if(s.toLowerCase().equals("left")){
			keys.put(CMD_LEFT, new ArrayList<Integer>());
			keys.get(CMD_LEFT).add(key);
		}else if(s.toLowerCase().equals("right")){
			keys.put(CMD_RIGHT, new ArrayList<Integer>());
			keys.get(CMD_RIGHT).add(key);
		}else if(s.toLowerCase().equals("jump")){
			keys.put(CMD_JUMP, new ArrayList<Integer>());
			keys.get(CMD_JUMP).add(key);
		}else if(s.toLowerCase().equals("fight")){
			keys.put(CMD_FIGHT, new ArrayList<Integer>());
			keys.get(CMD_FIGHT).add(key);
		}else{
			System.out.println("Cant name the key like that");
		}
	}
	
	/**
	 * here you bind a keys to the different functions
	 * @param s - the name of a command
	 * @param key - Gives the integer to the key 
	 */
	public void bindToKey(String s, int key, int key1){
		if(s.toLowerCase().equals("left")){
			keys.put(CMD_LEFT, new ArrayList<Integer>());
			keys.get(CMD_LEFT).add(key);
			keys.get(CMD_LEFT).add(key1);
		}else if(s.toLowerCase().equals("right")){
			keys.put(CMD_RIGHT, new ArrayList<Integer>());
			keys.get(CMD_RIGHT).add(key);
			keys.get(CMD_RIGHT).add(key1);
		}else if(s.toLowerCase().equals("jump")){
			keys.put(CMD_JUMP, new ArrayList<Integer>());
			keys.get(CMD_JUMP).add(key);
			keys.get(CMD_JUMP).add(key1);
		}else if(s.toLowerCase().equals("fight")){
			keys.put(CMD_FIGHT, new ArrayList<Integer>());
			keys.get(CMD_FIGHT).add(key);
			keys.get(CMD_FIGHT).add(key1);
		}else{
			System.out.println("Cant name the key like that");
		}
	}
	
	/**
	 * 
	 * @param s - the command
	 * @return true- if the string is binded to a key
	 * 			false- if the string isn't binded to a key
	 */
	public boolean check(String s){
		if(!s.equals(CMD_JUMP) && !s.equals(CMD_FIGHT)){
			for(int i = 0; i < keys.get(s).size(); i++){
				if(input.isKeyDown(keys.get(s).get(i)))
					return input.isKeyDown(keys.get(s).get(i));
			}
		}
		for(int i = 0; i < keys.get(s).size(); i++){
			if(input.isKeyDown(keys.get(s).get(i)))
				return input.isKeyPressed(keys.get(s).get(i));
		}
		return false;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		input = gc.getInput();
		// Get the vector that is used to apply force to the character
		Vec2 heroVec = hero.getBody().getWorldVector(new Vec2(-1.0f, 0.0f)); 
		//Sets the linearDamping so that the body dosen't continue to go to the left, right or up
		hero.getBody().setLinearDamping(2.0f);
		//Tells the view to applay the animation for standing
		if(!jump && doubleJump <1)
			pa.setStandAnimation();
		/*
		 * All this if... is to handle the input from the keyboard
		 */
		if(check(CMD_LEFT)){
			hero.getBody().applyForce(heroVec.mul(-1), hero.getBody().getPosition());
			//if the charcter isn't jumping this will start the moving to the  left animation
			if(!jump && doubleJump <1)
				pa.setLeftAnimation();
		}
		if(check(CMD_RIGHT)){
			hero.getBody().applyForce(heroVec, hero.getBody().getPosition());
			//if the charcter isn't jumping this will start the moving to the right animation
			if(!jump && doubleJump <1)
				pa.setRightAnimation();
		}
		if(check(CMD_JUMP)){
			//This will start the jump animation
			pa.setJumpAnimation();
			if(doubleJump < 2)
				jump = true;
			doubleJump += 1;
		}
		if(check(CMD_FIGHT)){
			// if you push the jump button it will start the animation and attack with it's weapon
			// Need to change to check if it is gun or sword
			pa.setGunAnimation();
			hero.attack();
			hero.hurt(10);
		}
		if(jump){
			hero.getBody().applyForce(hero.getBody().getWorldVector(new Vec2(.0f, 10.0f)), hero.getBody().getPosition());
			//This is a jumping count and will control how far in to the air the character will move
			jumpCount -= 1;
			if(jumpCount <= 0 ){
				jump =false;
				jumpCount = 20;
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if(!hero.isDead()){
			Vec2 tmp = hero.getPosPixels();
			g.drawAnimation(pa.getAnimation(), tmp.x, tmp.y );
			g.drawString(hero.getName(), tmp.x , tmp.y- 40);
			g.drawRect(tmp.x, tmp.y - 15, 100, 10);
			g.fillRect(tmp.x, tmp.y - 15, hero.getHp(), 10);
		}
	}
	
	/**
	 * A method to call when the character has hit the ground
	 */
	public void setGroundContact(){
		doubleJump = 0;
	}
}
