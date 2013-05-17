package view;

import java.util.ArrayList;
import java.util.List;

import model.HeroModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;
import utils.Navigation;
import utils.WeaponType;

public class HeroView {
	
	private HeroModel heroModel;
	private Animation currentAnimation = new Animation();
	private List <Animation> animations = new ArrayList<Animation>();
	private int [] duration = {500, 500};
	public enum Movement{move, stand, jump, fall, hurt, die};
	private String[] nbrOfAnimations = {"1", "2", "3", "4", "5", "6", "7", "8"};
	private String[] direction= {"Right", "Left"};
	
	public HeroView(HeroModel hm, WeaponType weaponType){
		this.heroModel = hm;
		try{
			loadAnimation(hm.getName(), weaponType);
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
		for(int i = 0; i < direction.length; i++){
			for(int j = 0; j < (Movement.values().length); j++){
				ArrayList<Image> image = new ArrayList<Image>();
				for(int k = 0; k < nbrOfAnimations.length; k++){
					try{
						image.add(new Image("res/Characters/"+ s + "/"+ Movement.values()[j] + direction[i]+ nbrOfAnimations[k] + ".png"));
					}catch(RuntimeException e){
						break;
					}
				}
				if(image.size() != duration.length){
					duration = new int[image.size()];
					for(int k = 0; k < image.size(); k++){
						duration[k]= 500;
					}
				}
				animations.add(new Animation(toArray(image) , duration, true));
			}
		}
		for(int i = 0; i < direction.length; i++){
			ArrayList<Image> image = new ArrayList<Image>();
			for(int j = 0; j < nbrOfAnimations.length; j ++){
				try{
					image.add(new Image("res/Characters/"+ s + "/"+ weapontype + direction[i]+ nbrOfAnimations[j] + ".png"));
				}catch(RuntimeException e){
					break;
				}
			}
			if(image.size() != duration.length){
				duration = new int[image.size()];
				for(int k = 0; k < image.size(); k++){
					duration[k]= 500;
				}
			}
			animations.add(new Animation(toArray(image), duration, true));
		}
	}
	
	public Image[] toArray(ArrayList<Image> imageList){
		Image[] image = new Image[imageList.size()];
		for(int i = 0; i < imageList.size(); i++){
			image[i] = imageList.get(i);
		}
		return image;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		Vec2 tmp = Camera.entityRender(heroModel.getPosPixels());
		/* Draws the animation */
		g.drawAnimation(currentAnimation, tmp.x, tmp.y);
		/* Draws the health bar above the hero */
		g.setColor(Color.white);
		g.drawRect(tmp.x, tmp.y - 15, 101, 11);
		g.setColor(Color.red);
		g.fillRect(tmp.x + 1, tmp.y - 14, heroModel.getHp(), 10);
		g.setColor(Color.white);
		/* Draws the name of the hero above the character and health bar */
		g.setColor(Color.white);
		g.drawString(heroModel.getName(), tmp.x, tmp.y - 40);
		/* Draws your scores*/
		g.setColor(Color.white);
	}
	
	/**
	 * returns the main animation
	 * @return Animation = the animation that is the main animation
	 */
	public Animation getAnimation(){
		return currentAnimation;
	}
	
	public void setAnimation(Movement m, Navigation n){
		int i;
		if(n ==Navigation.WEST){
			i = 6;
		}else{
			i = 0;
		}
		switch(m){
		case move:
			currentAnimation = animations.get(0 + i);
			break;
		case stand:
			currentAnimation = animations.get(1 + i);
			break;
		case jump:
			currentAnimation = animations.get(2 + i);
			break;
		case fall:
			currentAnimation = animations.get(3 + i);
			break;
		case hurt:
			currentAnimation = animations.get(4 + i);
			break;
		case die:
			currentAnimation = animations.get(5 + i);
			break;
		}
	}
	
	public void setAttackAnimation(Navigation n){
		if(n ==Navigation.EAST){
			currentAnimation = animations.get(12);
		}else{
			currentAnimation = animations.get(13);
		}
	}
	
	public int getWidth(){
		return currentAnimation.getWidth();
	}
	
	public int getHeight(){
		return currentAnimation.getHeight();
	}
}
