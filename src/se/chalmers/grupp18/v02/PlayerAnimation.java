package se.chalmers.grupp18.v02;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PlayerAnimation {
	
	private Animation player = new Animation();
	private List <Animation> ani = new <Animation>ArrayList();
	private int [] duration = {500, 500};
	private String[] move={"moveLeftA", "moveLeftB", "moveRightA", "moveRightB", "standA", "standB", "jumpA", "jumpB","fightA", "fightB"};
	
	public PlayerAnimation(String cName){
		try{
			loadAnimation(cName);
		}catch(SlickException e){
			e.printStackTrace();
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

	
	public List<Animation> ListOfAnimation(){
		return ani;
	}
	
	public int getWidth(){
		return player.getWidth();
	}
	
	public int getHeight(){
		return player.getHeight();
	}
}
