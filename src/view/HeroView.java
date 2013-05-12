package view;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.WeaponType;

public class HeroView {
	
	private Animation currentAnimation = new Animation();
	private List <Animation> animations = new ArrayList<Animation>();
	private int [] duration = {500, 500};
	private String[] move={"moveLeftA", "moveLeftB", 
			"moveRightA", "moveRightB", 
			"standA", "standB", 
			"jumpA", "jumpB"};
	
	public HeroView(String cName, WeaponType weaponType){
		try{
			loadAnimation(cName, weaponType);
		}catch(SlickException e){
			
		}
	}

	/**
	 * Loads all the Animation depending on a charcter name.
	 * @param weaponType 
	 * @param s= character name.
	 * @throws SlickException= throws if the file is not found.
	 */
	private void loadAnimation(String s, WeaponType weapontype) throws SlickException{
		for(int i = 0; i < (move.length-1); i+= 2){
			Image [] image = {new Image("res/Characters/"+ s + "/"+ move[i] + ".png"), new Image("res/Characters/"+ s + "/"+ move[i+1]+ ".png")};
			animations.add(new Animation(image , duration, true)); 
			System.out.println(animations.size());
		}
		if(weapontype != null){
			Image [] image = {new Image("res/Characters/"+ s + "/"+ weapontype + "A.png"), new Image("res/Characters/"+ s + "/"+ weapontype+ "B.png")};
			animations.add(new Animation(image, duration, true));
		}else{
			Image [] image = {new Image("res/Characters/"+ s + "/fistA.png"), new Image("res/Characters/"+ s + "/fistB.png")};
			animations.add(new Animation(image, duration, true));
		}
		currentAnimation = animations.get(0);
	}
	
	/**
	 * returns the main animation
	 * @return Animation = the animation that is the main animation
	 */
	public Animation getAnimation(){
		return currentAnimation;
	}
	
	public void setLeftAnimation(){
		currentAnimation = animations.get(0);
	}
	
	public void setRightAnimation(){
		currentAnimation = animations.get(1);
	}
	
	public void setStandAnimation(){
		currentAnimation = animations.get(2);
	}
	
	public void setJumpAnimation(){
		currentAnimation = animations.get(3);
	}
	
	public void setAttackAnimation(){
		currentAnimation = animations.get(4);
	}
	
	public int getWidth(){
		return currentAnimation.getWidth();
	}
	
	public int getHeight(){
		return currentAnimation.getHeight();
	}
}
