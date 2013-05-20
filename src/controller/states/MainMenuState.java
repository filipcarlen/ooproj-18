package controller.states;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.SoundType;
import utils.Sounds;

public class MainMenuState extends BasicGameState {
	
	private int stateID = -1;
	private final String PATH = "res/MainMenu/";
	
	private Image title = null;
	private Image background = null;
	private Image startGame = null;
	private Image startGameHighlighted = null;
	private Image endGame = null;
	private Image endGameHighlighted = null;
	private Image loadGame = null;
	private Image loadGameHighlighted = null;
	private Image options = null;
	private Image optionsHighlighted = null;
	private Image[] leafs;
	
	private Animation leafAnimation;
	
	
	private static int startMenuX = 330;
	private static int startMenuY = 170;
	private static int optionsMenuX = 330;
	private static int optionsMenuY = 370;
	private static int loadGameMenuX = 330;
	private static int loadGameMenuY = 270;
	private static int endGameX = 330;
	private static int endGameY = 470;
	
	private float leafPositionX = 0;
	private float leafPositionY = 0;
	private float leafStartPositionX = 0;
	private float[] leafStartPositionY = {-500,-400,-300,-200,-100,0,100,200,300,400,500,600};
	
	private boolean insideStartGame = false;
	private boolean insideOptionsMenu = false;
	private boolean insideLoadGame = false;
	private boolean insideEndGame = false;
	
	private static MainMenuState instance = null;
	
	
	public MainMenuState(int stateID){
		this.stateID = stateID;
	}
	
	public static MainMenuState getInstance(){
		if(instance == null){
			instance = new MainMenuState(GameApp.MAINMENUSTATE);
		}
		return instance;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
		title = new Image("res/title.png");
		background = new Image("res/Background.png");
		startGame = new Image(PATH+"Startgame.png");
		startGameHighlighted = new Image(PATH+"StartgameHighlighted.png");
		endGame = new Image(PATH+"endgame.png");
		endGameHighlighted = new Image(PATH+"endgamehighlighted.png");
		loadGame = new Image(PATH+"loadgame.png");
		loadGameHighlighted = new Image(PATH+"loadgamehighlighted.png");
		options = new Image(PATH+"options.png");
		optionsHighlighted = new Image(PATH+"optionshighlighted.png");
		
		initLeafs();
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		changeLeafPosition();
		background.draw(0,0);
		title.draw(0,0);
		
		if(insideStartGame){
			startGameHighlighted.draw(startMenuX, startMenuY);
		}
		else{
			startGame.draw(startMenuX,startMenuY);
		}
		
		if(insideEndGame){
			endGameHighlighted.draw(endGameX,endGameY);
		}
		else{
			endGame.draw(endGameX, endGameY);
		}
		
		if(insideLoadGame){
			loadGameHighlighted.draw(loadGameMenuX,loadGameMenuY);
		}
		else{
			loadGame.draw(loadGameMenuX, loadGameMenuY);
		}
		
		if(insideOptionsMenu){
			optionsHighlighted.draw(optionsMenuX,optionsMenuY);
		}
		else{
			options.draw(optionsMenuX,optionsMenuY);
		}
		
		g.drawAnimation(leafAnimation, leafPositionX, leafPositionY);
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		insideStartGame = checkMouse(mouseX, mouseY, startMenuX, startMenuY, startGame);
		insideEndGame = checkMouse(mouseX, mouseY, endGameX, endGameY, endGame);
		insideLoadGame = checkMouse(mouseX, mouseY, loadGameMenuX, loadGameMenuY, loadGame);
		insideOptionsMenu = checkMouse(mouseX, mouseY, optionsMenuX, optionsMenuY, options);
			
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideStartGame){
			Sounds.getInstance().stopMusic();
			Sounds.getInstance().playSound(SoundType.GAME_MUSIC);
			 sbg.enterState(GameApp.PLAYSTATE);
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideOptionsMenu){
			sbg.enterState(GameApp.OPTIONSSTATE);
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideEndGame){
			gc.exit();
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public boolean checkMouse(int mouseX, int mouseY, int menuX, int menuY, Image image){
		if((mouseX >= menuX && mouseX <= menuX + image.getWidth()) &&
	            (mouseY >= menuY && mouseY <= menuY + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}
	
	public void initLeafs() throws SlickException{
		leafs = new Image[14];
		leafs[0] =  new Image(PATH + "/Leafanimation/1.png");
		leafs[1] =  new Image(PATH + "/Leafanimation/2.png");
		leafs[2] =  new Image(PATH + "/Leafanimation/3.png");
		leafs[3] =  new Image(PATH + "/Leafanimation/4.png");
		leafs[4] =  new Image(PATH + "/Leafanimation/5.png");
		leafs[5] =  new Image(PATH + "/Leafanimation/6.png");
		leafs[6] =  new Image(PATH + "/Leafanimation/7.png");
		leafs[7] =  new Image(PATH + "/Leafanimation/8.png");
		leafs[8] =  new Image(PATH + "/Leafanimation/9.png");
		leafs[9] =  new Image(PATH + "/Leafanimation/10.png");
		leafs[10] =  new Image(PATH + "/Leafanimation/11.png");
		leafs[11] =  new Image(PATH + "/Leafanimation/12.png");
		leafs[12] =  new Image(PATH + "/Leafanimation/13.png");
		leafs[13] =  new Image(PATH + "/Leafanimation/14.png");
		
		leafAnimation = new Animation(leafs, 100);
	}
	
	public void changeLeafPosition(){
		Random rand = new Random();
		this.leafPositionX = this.leafPositionX + 1.5f;
		this.leafPositionY = this.leafPositionY + 1f;
		
		if(this.leafPositionX >= 900){
			this.leafPositionX = leafStartPositionX;
			this.leafPositionY = leafStartPositionY[rand.nextInt(12)];
		}
		
		if(this.leafPositionY >= 600){
			this.leafPositionX = leafStartPositionX;
			this.leafPositionY = leafStartPositionY[rand.nextInt(12)];
		}
	}
}
