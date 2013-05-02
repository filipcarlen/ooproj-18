package se.chalmers.grupp18.v01;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class HeroView {
	
	private Animation player = new Animation();
	private List <Animation> ani = new ArrayList<Animation>();
	private int [] duration = {500, 500};
	private String[] move={"moveLeftA", "moveLeftB", "moveRightA", "moveRightB", "standA", "standB", "jumpA", "jumpB","fightA", "fightB"};
	
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
			ani.add(new Animation(image , duration, true)); 
			System.out.println(ani.size());
		}
		player = ani.get(0);
	}
	
	/**
	 * returns the main animation
	 * @return Animation = the animation that is the main animation
	 */
	public Animation getAnimation(){
		return player;
	}
	
	public List<Animation> listOfAnimation(){
		return ani;
	}
	
	public void leftAnimation(){
		player = ani.get(0);
	}
	
	public void rightAnimation(){
		player = ani.get(1);
	}
	
	public void standAnimation(){
		player = ani.get(2);
	}
	
	public void jumpAnimation(){
		player = ani.get(3);
	}
	
	public void fightAnimation(){
		player = ani.get(4);
	}
	
	public int getWidth(){
		return player.getWidth();
	}
	
	public int getHeight(){
		return player.getHeight();
	}
}
