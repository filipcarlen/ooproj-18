package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import model.Hero;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;
import utils.Navigation;
import utils.WeaponType;
/**
 * This class controls the view of the hero
 * 
 * @author Project Group 18 (Chalmers, 2013)
 *
 */
public class HeroView {
	
	private Hero model;
	
	private Animation currentAnimation = new Animation();
	
	private List <Animation> animations = new ArrayList<Animation>();
	
	/* Default duration on animations*/
	private int [] duration = {200, 200};
	
	/* The different movement animations the hero can  have (And Need to have to make the game work)*/
	public enum Movement{RUN, STAND, JUMP, FALL, HURT, HURTBACK, DIE};
	
	/* This makes the view load at max 8 images in on animation*/
	private String[] nbrOfAnimations = {"1", "2", "3", "4", "5", "6", "7", "8"};
	
	/* This makes it possible to add to of the same animations one to the right and one to left*/
	private String[] direction= {"right", "left"};
	
	/* This is a boolean so that you now if you have loaded a weapon as an animation*/
	private boolean loadedweapon= false;
	
	public HeroView(Hero hm, WeaponType weaponType){
		this.model = hm;
		try{
			loadMovementAnimation(hm.getHeroName());
			loadWeaponAnimation(hm.getHeroName(), weaponType);
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

	public HeroView(Hero hm) {
		this.model = hm;
		try{
			loadMovementAnimation(hm.getHeroName());
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if(model.isDead())
			setAnimation(Movement.DIE, model.getDirection());
		Vec2 tmp = Camera.entityRender(model.getPosPixels());
		/* Draws the animation */
		g.drawAnimation(currentAnimation, tmp.x, tmp.y);
	}

	/**
	 * Loads all the Animation depending on a charcter name.
	 * @param weaponType 
	 * @param s= character name.
	 * @throws SlickException= throws if the file is not found.
	 */
	private void loadMovementAnimation(String s) throws SlickException{
		for(int i = 0; i < direction.length; i++){
			for(int j = 0; j < (Movement.values().length); j++){
				ArrayList<Image> image = new ArrayList<Image>();
				for(int k = 0; k < nbrOfAnimations.length; k++){
					try{
						image.add(new Image("res/characters/"+ s + "/"+ Movement.values()[j].toString().toLowerCase() + "_" + direction[i] + "_" + nbrOfAnimations[k] + ".png"));
					}catch(RuntimeException e){
						break;
					}
				}
				if(image.size() != duration.length){
					duration = new int[image.size()];
					for(int k = 0; k < image.size(); k++){
						duration[k]= 200;
					}
				}
				animations.add(new Animation(toArray(image) , duration, true));
			}
		}
		currentAnimation = animations.get(1);
	}
	
	public void loadWeaponAnimation(String s, WeaponType weapontype){
		/* If i already have loaded the weapon animation, i will remove the current weapon animation
		 * (needed if you have to change weapon in game)*/
		if(loadedweapon){
			animations.remove(animations.size()-1);
			animations.remove(animations.size()-1);
		}
		/* Loads weapon animation */
		for(int i = 0; i < direction.length; i++){
			ArrayList<Image> image = new ArrayList<Image>();
			/* Loads all animation at one direction*/
			for(int j = 0; j < nbrOfAnimations.length; j ++){
				try{
					image.add(new Image("res/characters/"+ s + "/"+ weapontype.toString().toLowerCase() + "_" + direction[i] + "_" + nbrOfAnimations[j] + ".png"));
				}catch(RuntimeException e){
					break;
				}catch(SlickException e){}
			}
			/* Here i check if i need to change the duration so the size is equals to the length*/
			if(image.size() != duration.length){
				duration = new int[image.size()];
				for(int k = 0; k < image.size(); k++){
					duration[k]= 200;
				}
			}
			animations.add(new Animation(toArray(image), duration, true));
		}
		loadedweapon = true;
	}
	
	/**
	 * This method takes a list and turns it in to a 1 dimension array
	 * @param imageList
	 * @return
	 */
	public Image[] toArray(List<Image> imageList){
		Image[] image = new Image[imageList.size()];
		for(int i = 0; i < imageList.size(); i++){
			image[i] = imageList.get(i);
		}
		return image;
	}
	
	/**
	 * 
	 * @param m
	 * @param n
	 */
	public void setAnimation(Movement m, Navigation n){
		int i;
		if(n ==Navigation.WEST){
			i = 7;
		}else{
			i = 0;
		}
		switch(m){
		case RUN:
			currentAnimation = animations.get(0 + i);
			break;
		case STAND:
			currentAnimation = animations.get(1 + i);
			break;
		case JUMP:
			currentAnimation = animations.get(2 + i);
			break;
		case FALL:
			currentAnimation = animations.get(3 + i);
			break;
		case HURT:
			currentAnimation = animations.get(4 + i);
			break;
		case HURTBACK:
			currentAnimation = animations.get(5 + i);
			break;
		case DIE:
			if(currentAnimation != animations.get(13 - i)){
				currentAnimation = animations.get(13 - i);
				currentAnimation.setLooping(false);
			}
			break;
		}
	}
	
	public void setAttackAnimation(Navigation n){
		if(n ==Navigation.EAST){
			currentAnimation = animations.get(14);
		}else if(n == Navigation.WEST){
			currentAnimation = animations.get(15);
		}
	}
	
	public int getWidth(){
		return currentAnimation.getWidth();
	}
	
	public int getHeight(){
		return currentAnimation.getHeight();
	}

	public Dimension getDimension() {
		return new Dimension(getWidth(), getHeight());
	}

	public Animation getAttackAnimation() {
		return animations.get(14);
	}
}
