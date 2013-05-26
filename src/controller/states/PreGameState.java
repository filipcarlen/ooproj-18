package controller.states;



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Utils;
import utils.WeaponType;
/**
 * The pre game state, which is the state between main menu and playstate
 * @author group 18
 *
 */
public class PreGameState extends BasicGameState implements ActionListener {
	
	private int stateID = -1;
	private static final String PATH = "res/pre_game_menu/";
	private Image background, sword, gun, title, play, playH, gunH, swordH, gunC, swordC, mainMenu, mainMenuH;
	private Vec2 swordPos, gunPos, titlePos, playPos, mainMenuPos;
	private boolean mouseInsidePlay, mouseInsideSword, mouseInsideGun, swordClicked, gunClicked, mouseInsideMainMenu;
	private static PreGameState instance = null;
	private WeaponType weapon;
	private TextField playerNameInput;
	private AngelCodeFont font;
	private float resize;
	private Timer choiseTimer = new Timer(1000, this);
	
	
	private PreGameState(int stateID){
		this.stateID = stateID;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		resize = gc.getWidth()/ 900.0f;
		titlePos = new Vec2(gc.getWidth()/2-title.getWidth()/2,10);
		swordPos = new Vec2(gc.getWidth()*.03f, gc.getHeight()*.2f);
		gunPos = new Vec2(gc.getWidth()*.53f, gc.getHeight()*.2f);
		playPos = new Vec2(gc.getWidth()*.85f, gc.getHeight()*.85f);
		mainMenuPos = new Vec2(gc.getWidth()-playPos.x-play.getWidth(), gc.getHeight()*.85f);
		swordClicked= false;
		gunClicked = false;
		weapon = WeaponType.FIST;
		playerNameInput.setLocation((int)(gc.getWidth()*.5f), (int)(gc.getHeight()*.70f));
	}
	
	public static PreGameState getInstance(){
		if(instance == null){
			instance = new PreGameState(GameApp.PRE_GAME_STATE);
		}
		return instance;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		playerNameInput =  new TextField(gc, new AngelCodeFont("res/font/fontwhite.fnt", "res/font/fontwhite_0.png"), 
				(int)(gc.getWidth()*.5f), (int)(gc.getHeight()*.70f), 300, 50);
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
		mainMenu = new Image(PATH + "mainMenu.png");
		mainMenuH = new Image(PATH + "mainMenuH.png");
		font = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.green);
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		title.draw(titlePos.x, titlePos.y);
		font.drawString(playerNameInput.getX()-150, playerNameInput.getY(), "Player name: ");
		playerNameInput.render(gc, g);
		if(mouseInsideGun){
			gunH.draw(gunPos.x,gunPos.y, gunH.getWidth()*resize, gunH.getHeight()*resize);
		}
		else{
			gun.draw(gunPos.x, gunPos.y, gun.getWidth()*resize, gun.getHeight()*resize);
		}
		if(mouseInsidePlay){
			playH.draw(playPos.x, playPos.y);
		}
		else{
			play.draw(playPos.x, playPos.y);
		}
		if(mouseInsideSword){
			swordH.draw(swordPos.x, swordPos.y, swordH.getWidth()*resize, swordH.getHeight()*resize);
		}
		else{
			sword.draw(swordPos.x, swordPos.y, sword.getWidth()*resize, sword.getHeight()*resize);
		}
		if(mouseInsideMainMenu){
			mainMenu.draw(mainMenuPos.x, mainMenuPos.y);
		}else{
			mainMenuH.draw(mainMenuPos.x, mainMenuPos.y);
		}
		if(swordClicked){
			swordC.draw(swordPos.x, swordPos.y, swordC.getWidth()*resize, swordC.getHeight()*resize);
		}
		if(gunClicked){
			gunC.draw(gunPos.x, swordPos.y, gunC.getWidth()*resize, gunC.getHeight()*resize);
		}
		if(choiseTimer.isRunning()){
			font.drawString(gc.getWidth()*.25f, gc.getHeight()*.35f, 
					"Your choise is not possible! \nEither you have not selected a weapon\nor your name is over 10 letters");
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		boolean mouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		mouseInsideGun = Utils.isMouseInsideImage(mouseX, mouseY, gunPos, gun);
		mouseInsidePlay = Utils.isMouseInsideImage(mouseX, mouseY, playPos, play);
		mouseInsideSword = Utils.isMouseInsideImage(mouseX, mouseY, swordPos, sword);
		mouseInsideMainMenu = Utils.isMouseInsideImage(mouseX, mouseY, mainMenuPos, mainMenu);
		
		if(mouseClicked && mouseInsideGun){
			weapon = WeaponType.GUN;
			if(swordClicked){
				swordClicked = false;
			}
			gunClicked = true;
		}
		if(mouseClicked && mouseInsidePlay){
			if(weapon == WeaponType.FIST || playerNameInput.getText().length() > 10){
				choiseTimer.start();
			}else{
				choiseTimer.stop();
				PlayState.getInstance().setWeaponInUse(weapon);
				if(playerNameInput.getText().length() == 0)
					PlayState.getInstance().setPlayerName("Player");
				else
					PlayState.getInstance().setPlayerName(playerNameInput.getText());
				sbg.enterState(GameApp.PLAY_STATE);
			}
		}
		
		if(mouseClicked && mouseInsideSword){
			weapon = WeaponType.SWORD;
			if(gunClicked){
				gunClicked = false;
			}
			swordClicked = true;
		}
		if(mouseClicked && mouseInsideMainMenu){
			sbg.enterState(GameApp.MAIN_MENU_STATE);
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		choiseTimer.stop();
	}
}
