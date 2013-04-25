package se.chalmers.grupp18.v01;

import java.util.HashMap;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Version: 1
 * A controller for the Character
 * @author Marcus
 *
 */

public class CharacterController implements IEntityController{
	
	static final String CMD_LEFT = "left";
	static final String CMD_RIGHT = "right";
	static final String CMD_JUMP = "jump";
	static final String CMD_FIGHT = "fight";
	Input input;
	boolean jump;
	int jumpCount = 20;
	CharacterModel hero;
	String name;
	CharacterView pa;
	HashMap<String, Integer>keys = new HashMap<String, Integer>();
	
	
	public CharacterController(CharacterModel ce, String name){
		hero = ce;
		this.name = name;
		pa = new CharacterView(name);
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
	
	public boolean check(String s){
		if(!s.equals(CMD_JUMP))
			return input.isKeyDown(keys.get(s));
		return input.isKeyPressed(keys.get(s));
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta){
		input = gc.getInput();
		//Controls.input = gc.getInput();
		Vec2 heroVec = hero.body.getWorldVector(new Vec2(-5.0f, 0)); // Get the vector that is used to apply force to the character
		
		hero.body.setLinearDamping(2.0f);
		//if(Controls.check(CMD_LEFT)){
		if(check(CMD_LEFT)){
			hero.body.applyForce(heroVec.mul(-1), hero.body.getPosition());
		}
		//if(Controls.check(CMD_RIGHT)){
		if(check(CMD_RIGHT)){
			hero.body.applyForce(heroVec, hero.body.getPosition());
		}
		//if(Controls.check(CMD_JUMP)){
		if(check(CMD_JUMP)){
			jump = true;
		}
		//if(Controls.check(CMD_FIGHT)){
		if(check(CMD_FIGHT)){
			
		}
		if(jump){
			hero.body.applyForce(hero.body.getWorldVector(new Vec2(.0f, 30.0f)), hero.body.getPosition());
			jumpCount -= 1;
			if(jumpCount <= 0 ){
				jump =false;
				jumpCount = 20;
			}
		}
	}

	@Override
	public void init() {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		Vec2 tmp = hero.body.getPosition().add(new Vec2(-.5f,-.5f)).mul(50f);
		g.drawAnimation(pa.listOfAnimation().get(0), tmp.x, tmp.y );
		
	}

}
