package view;

import java.awt.Dimension;
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
	private int [] duration = {200, 200};
	public enum Movement{RUN, STAND, JUMP, FALL, HURT, HURTBACK, DIE};
	private String[] nbrOfAnimations = {"1", "2", "3", "4", "5", "6", "7", "8"};
	private String[] direction= {"right", "left"};
	private boolean loadedweapon= false;
	
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
						image.add(new Image("res/Characters/"+ s + "/"+ Movement.values()[j].toString().toLowerCase() + "_" + direction[i] + "_" + nbrOfAnimations[k] + ".png"));
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
		loadWeaponAnimation(s, weapontype);
		currentAnimation = animations.get(1);
	}
	
	public void loadWeaponAnimation(String s, WeaponType weapontype){
		if(loadedweapon){
			animations.remove(animations.size()-1);
			animations.remove(animations.size()-1);
		}
		for(int i = 0; i < direction.length; i++){
			ArrayList<Image> image = new ArrayList<Image>();
			for(int j = 0; j < nbrOfAnimations.length; j ++){
				try{
					image.add(new Image("res/Characters/"+ s + "/"+ weapontype.toString().toLowerCase() + "_" + direction[i] + "_" + nbrOfAnimations[j] + ".png"));
				}catch(RuntimeException e){
					break;
				}catch(SlickException e){}
			}
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
	
	public Image[] toArray(ArrayList<Image> imageList){
		Image[] image = new Image[imageList.size()];
		for(int i = 0; i < imageList.size(); i++){
			image[i] = imageList.get(i);
		}
		return image;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		if(heroModel.isDead())
			setAnimation(Movement.DIE, heroModel.getDirection());
		Vec2 tmp = Camera.entityRender(heroModel.getPosPixels());
		/* Draws the animation */
		g.drawAnimation(currentAnimation, tmp.x, tmp.y);
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
