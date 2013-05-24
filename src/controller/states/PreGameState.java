package controller.states;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.WeaponType;

public class PreGameState extends BasicGameState {
	
	private int stateID = -1;
	private static final String PATH = "res/Pregame/";
	private Image background, sword, gun, title, play, playH, gunH, swordH, gunC, swordC, mainMenu, mainMenuH;
	private Vec2 swordPos, gunPos, titlePos, playPos;
	private boolean insidePlay, insideSword, insideGun, swordClicked, gunClicked;
	private static PreGameState instance = null;
	private WeaponType weapon = WeaponType.FIST;
	
	
	private PreGameState(int stateID){
		this.stateID = stateID;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {	
		titlePos = new Vec2(gc.getWidth()/2-title.getWidth()/2,10);
		swordPos = new Vec2(gc.getWidth()*.25f-sword.getWidth()/2, gc.getHeight()*.5f-sword.getHeight()*.5f);
		gunPos = new Vec2(gc.getWidth()*.75f-sword.getWidth()/2, gc.getHeight()*.5f-gun.getHeight()*.5f);
		playPos = new Vec2(gc.getWidth()*.8f, gc.getHeight()*.8f);
	}
	
	public static PreGameState getInstance(){
		if(instance == null){
			instance = new PreGameState(GameApp.PREGAMESTATE);
		}
		return instance;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("res/Background.png");
		sword = new Image(PATH+"sword.png");
		swordH = new Image(PATH+"swordH.png");
		gun = new Image(PATH+"gun.png");
		gunH = new Image(PATH+"gunH.png");
		title = new Image(PATH+"choose_weapon.png");
		play = new Image(PATH+"play.png");
		playH = new Image(PATH+"playH.png");
		swordC = new Image(PATH+"swordClicked.png");
		gunC = new Image(PATH+"gunClicked.png");
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		title.draw(titlePos.x, titlePos.y);
		
		if(insideGun){
			gunH.draw(gunPos.x,gunPos.y);
		}
		else{
			gun.draw(gunPos.x, gunPos.y);
		}
		if(insidePlay){
			playH.draw(playPos.x, playPos.y);
		}
		else{
			play.draw(playPos.x, playPos.y);
		}
		if(insideSword){
			swordH.draw(swordPos.x, swordPos.y);
		}
		else{
			sword.draw(swordPos.x, swordPos.y);
		}
		if(swordClicked){
			swordC.draw(swordPos.x, swordPos.y);
		}
		if(gunClicked){
			gunC.draw(gunPos.x, swordPos.y);
		}	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		insideGun = checkMouse(mouseX, mouseY, gunPos, gun);
		insidePlay = checkMouse(mouseX, mouseY, playPos, play);
		insideSword = checkMouse(mouseX, mouseY, swordPos, sword);
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideGun){
			weapon = WeaponType.GUN;
			if(swordClicked){
				swordClicked = false;
			}
			gunClicked = true;
		}
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insidePlay){
			PlayState.getInstance().setWeaponInUse(weapon);
			sbg.enterState(GameApp.PLAYSTATE);
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideSword){
			weapon = WeaponType.SWORD;
			if(gunClicked){
				gunClicked = false;
			}
			swordClicked = true;
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public boolean checkMouse(int mouseX, int mouseY, Vec2 pos, Image image){
		if((mouseX >= pos.x && mouseX <= pos.x + image.getWidth()) &&
	            (mouseY >= pos.y && mouseY <= pos.y + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}
}
