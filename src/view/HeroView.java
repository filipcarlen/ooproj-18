package view;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HeroView {
	
	private Animation currentAnimation = new Animation();
	private List <Animation> animations = new ArrayList<Animation>();
	private int [] duration = {500, 500};
	private String[] move={"moveLeftA", "moveLeftB", "moveRightA", "moveRightB", "standA", "standB", "jumpA", "jumpB","gunA", "gunB", "swordA", "swordB"};
	
	public HeroView(String cName){
		try{
			loadAnimation(cName);
		}catch(SlickException e){
			
		}
	}

	/**
	 * Loads all the Animation depending on a charcter name.
	 * @param s= character name.
	 * @throws SlickException= throws if the file is not found.
	 */
	private void loadAnimation(String s) throws SlickException{
		for(int i = 0; i < (move.length-1); i+= 2){
			Image [] image = {new Image("res/Characters/"+ s + "/"+ move[i] + ".png"), new Image("res/Characters/"+ s + "/"+ move[i+1]+ ".png")};
			animations.add(new Animation(image , duration, true)); 
			System.out.println(animations.size());
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
	
	public List<Animation> listOfAnimation(){
		return animations;
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
	
	public void setGunAnimation(){
		currentAnimation = animations.get(4);
	}
	
	public void setSwordAnimation(){
		currentAnimation = animations.get(5);
	}
	
	public int getWidth(){
		return currentAnimation.getWidth();
	}
	
	public int getHeight(){
		return currentAnimation.getHeight();
	}
}
