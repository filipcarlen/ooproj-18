package se.chalmers.grupp18.v01;

import java.util.HashMap;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

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
	String name;
	HeroView pa;
	HashMap<String, Integer>keys = new HashMap<String, Integer>();
	
	
	public HeroController(HeroModel ce, String name){
		hero = ce;
		this.name = name;
		pa = new HeroView(name);
		setControls();
	}
	
	/**
	 * This is a method to give the default setting to the key inputs
	 */
	public void setControls(){
		bindToKey(CMD_LEFT, Input.KEY_A);
		bindToKey(CMD_RIGHT, Input.KEY_D);
		bindToKey(CMD_JUMP, Input.KEY_W);
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
		if(s.toLowerCase().equals("left"))
			keys.put(CMD_LEFT, key);
		else if(s.toLowerCase().equals("right"))
			keys.put(CMD_RIGHT, key);
		else if(s.toLowerCase().equals("jump"))
			keys.put(CMD_JUMP, key);
		else if(s.toLowerCase().equals("fight"))
			keys.put(CMD_FIGHT, key);
		else
			System.out.println("Cant name the key like that");
	}
	
	/**
	 * 
	 * @param s - the command
	 * @return true- if the string is binded to a key
	 * 			false- if thhe string isn't binded to a key
	 */
	public boolean check(String s){
		if(!s.equals(CMD_JUMP) && !s.equals(CMD_FIGHT))
			return input.isKeyDown(keys.get(s));
		return input.isKeyPressed(keys.get(s));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		input = gc.getInput();
		// Get the vector that is used to apply force to the character
		Vec2 heroVec = hero.body.getWorldVector(new Vec2(-1.0f, 0)); 
		//Sets the linearDamping so that the body dosen't continue to go to the left, right or up
		hero.body.setLinearDamping(2.0f);
		//Tells the view to applay the animation for standing
		if(!jump && doubleJump <1)
			pa.standAnimation();
		/*
		 * All this if... is to handle the input from the keyboard
		 */
		if(check(CMD_LEFT)){
			hero.body.applyForce(heroVec.mul(-1), hero.body.getPosition());
			//if the charcter isn't jumping this will start the moving to the  left animation
			if(!jump && doubleJump <1)
				pa.leftAnimation();
		}
		if(check(CMD_RIGHT)){
			hero.body.applyForce(heroVec, hero.body.getPosition());
			//if the charcter isn't jumping this will start the moving to the right animation
			if(!jump && doubleJump <1)
				pa.rightAnimation();
		}
		if(check(CMD_JUMP)){
			//This will start the jump animation
			pa.jumpAnimation();
			if(doubleJump < 2)
				jump = true;
			doubleJump += 1;
		}
		if(check(CMD_FIGHT)){
			// if you push the jump button it will start the animation and attack with it's weapon
			pa.fightAnimation();
			hero.attack();
			hero.hurt(10);
		}
		if(jump){
			hero.body.applyForce(hero.body.getWorldVector(new Vec2(.0f, 10.0f)), hero.body.getPosition());
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
			Vec2 tmp = hero.body.getPosition().add(new Vec2(-hero.getWidth(),-hero.getHeight())).mul(hero.getTransfer());
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
	
	public HeroModel getModel(){
		return hero;
	}
}
