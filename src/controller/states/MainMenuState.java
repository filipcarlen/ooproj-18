package controller.states;

import java.util.Random;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.SoundType;
import utils.Sounds;

public class MainMenuState extends BasicGameState {
	
	private int stateID = -1;
	private final String PATH = "res/MainMenu/";
	
	private Image title, background, startGame, startGameHighlighted, quit, quitHighlighted, highscore,
						highscoreHighlighted, options, optionsHighlighted;
	private Image[] leafs;
	private Animation leafAnimation;
	
	private Vec2 startGamePos;
	private Vec2 optionsPos;
	private Vec2 highscorePos;
	private Vec2 quitGamePos;
		
	
	private float leafPositionX = 0;
	private float leafPositionY = 0;
	private float leafStartPositionX = 0;
	private float[] leafStartPositionY = {-500,-400,-300,-200,-100,0,100,200,300,400,500,600};
	
	private boolean insideStartGame = false;
	private boolean insideOptionsMenu = false;
	private boolean insideHighscore = false;
	private boolean insideQuitGame = false;
	
	private static MainMenuState instance = null;
	
	
	private MainMenuState(int stateID){
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
		float screenHeight = gc.getScreenHeight();
		initLeafs();
		Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
		title = new Image("res/title.png");
		background = new Image("res/Background.png");
		startGame = new Image(PATH+"Startgame.png");
		startGameHighlighted = new Image(PATH+"StartgameHighlighted.png");
		quit = new Image(PATH+"quit.png");
		quitHighlighted = new Image(PATH+"quithighlighted.png");
		highscore = new Image(PATH+"highscore.png");
		highscoreHighlighted = new Image(PATH+"highscorehighlighted.png");
		options = new Image(PATH+"options.png");
		optionsHighlighted = new Image(PATH+"optionshighlighted.png");
		
		float spacing = (screenHeight - title.getHeight() -
				3*startGame.getHeight() - options.getHeight())/4;
		
		startGamePos = new Vec2(gc.getWidth()/2-startGame.getWidth()/2,title.getHeight());
		highscorePos = new Vec2(gc.getWidth()/2-highscore.getWidth()/2, startGamePos.y+spacing);
		optionsPos = new Vec2(gc.getWidth()/2-options.getWidth()/2,highscorePos.y+spacing);
		quitGamePos = new Vec2(gc.getWidth()/2-quit.getWidth()/2,optionsPos.y+spacing);
		
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		changeLeafPosition();
		background.draw(0,0);
		g.drawAnimation(leafAnimation, leafPositionX, leafPositionY);
		title.draw(0,0);
		
		if(insideStartGame){
			startGameHighlighted.draw(startGamePos.x,startGamePos.y);
		}
		else{
			startGame.draw(startGamePos.x, startGamePos.y);
		}
		
		if(insideQuitGame){
			quitHighlighted.draw(quitGamePos.x,quitGamePos.y);
		}
		else{
			quit.draw(quitGamePos.x, quitGamePos.y);
		}
		
		if(insideHighscore){
			highscoreHighlighted.draw(highscorePos.x,highscorePos.y);
		}
		else{
			highscore.draw(highscorePos.x, highscorePos.y);
		}
		
		if(insideOptionsMenu){
			optionsHighlighted.draw(optionsPos.x,optionsPos.y);
		}
		else{
			options.draw(optionsPos.x,optionsPos.y);
		}
		
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		insideStartGame = checkMouse(mouseX, mouseY, startGamePos, startGame);
		insideQuitGame = checkMouse(mouseX, mouseY, quitGamePos, quit);
		insideHighscore= checkMouse(mouseX, mouseY, highscorePos, highscore);
		insideOptionsMenu = checkMouse(mouseX, mouseY, optionsPos, options);
			
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideStartGame){	
			Sounds.getInstance().stopMusic();
			Sounds.getInstance().playMusic(SoundType.GAME_MUSIC);
			sbg.enterState(GameApp.PLAYSTATE);
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideOptionsMenu){
			sbg.enterState(GameApp.OPTIONSSTATE);
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideHighscore){
			sbg.enterState(GameApp.HIGHSCORESTATE);
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideQuitGame){
			gc.exit();
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
	
	public void initLeafs() throws SlickException{
		leafs = new Image[14];
		for(int i = 0; i<leafs.length;i++){
			leafs[i] = new Image(PATH + "/Leafanimation/"+(i+1)+".png");
		}
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
