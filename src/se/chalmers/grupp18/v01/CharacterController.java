package se.chalmers.grupp18.v01;

import java.util.HashMap;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Version: 1
 * A controller for the Character
 * @author Marcus
 *
 */

public class CharacterController {
	
	static final String CMD_LEFT = "left";
	static final String CMD_RIGHT = "right";
	static final String CMD_JUMP = "jump";
	static final String CMD_FIGHT = "fight";
	Input input;
	CharacterEntity hero;
	String name;
	PlayerAnimation pa;
	HashMap<String, Integer>keys = new HashMap<String, Integer>();
	
	
	public CharacterController(CharacterEntity ce, String name){
		hero = ce;
		this.name = name;
		pa = new PlayerAnimation(name);
		setControls();
	}
	
	public void setControls(){
		bindToKey(CMD_LEFT, Input.KEY_A);
		bindToKey(CMD_RIGHT, Input.KEY_D);
		bindToKey(CMD_JUMP, Input.KEY_W);
		bindToKey(CMD_FIGHT, Input.KEY_SPACE);
	}
	
	public void removeKeySettings(){
		keys.clear();
	}
	
	public void bindToKey(String s, int key){
		String k = s.toLowerCase();
		switch(s){
		case "left":
			keys.put(CMD_LEFT, key);
			break;
		case "right":
			keys.put(CMD_RIGHT, key);
			break;
		case "jump":
			keys.put(CMD_JUMP, key);
			break;
		case "fight":
			keys.put(CMD_FIGHT, key);
			break;
		default:
			System.out.println("Cant name the key like that");
		}
	}
	
	public boolean check(String s){
		return input.isKeyDown(keys.get(s));
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		input = gc.getInput();
		
		Vec2 heroPos = hero.body.getWorldPoint(hero.body.getLocalCenter()); // Get the point you want to apply the force at
		Vec2 heroVec = hero.body.getWorldVector(new Vec2(-100.0f, 0.0f)); // Get the vector that is used to apply force to the character
		
		if(check(CMD_LEFT)){
			hero.body.applyLinearImpulse(heroVec.mul(-1), heroPos);
		}
		if(check(CMD_RIGHT)){
			hero.body.applyLinearImpulse(heroVec, heroPos);
		}
		if(check(CMD_JUMP)){
			
		}
		if(check(CMD_FIGHT)){
			
		}
	}

}
