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
	private final String PATH = "res/main_menu/";
	
	private Image title, background, startGame, startGameHighlighted, quit, quitHighlighted, highscore,
						highscoreHighlighted, options, optionsHighlighted;
	private Image[] leafs;
	private Animation leafAnimation;
	
	private Vec2 startGamePos;
	private Vec2 optionsPos;
	private Vec2 highscorePos;
	private Vec2 quitGamePos;
	private Vec2 titlePos;
		
	
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
			instance = new MainMenuState(GameApp.MAIN_MENU_STATE);
		}
		return instance;
	}

	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {	
		Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
		titlePos = new Vec2(gc.getWidth()/2-title.getWidth()/2,0);
		startGamePos = new Vec2(gc.getWidth()/2-startGame.getWidth()/2,gc.getHeight()*.28f);
		highscorePos = new Vec2(gc.getWidth()/2-highscore.getWidth()/2, gc.getHeight()*.45f);
		optionsPos = new Vec2(gc.getWidth()/2-options.getWidth()/2,gc.getHeight()*.65f);
		quitGamePos = new Vec2(gc.getWidth()/2-quit.getWidth()/2,gc.getHeight()*.82f);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		initLeafs();
		title = new Image(PATH + "title.png");
		background = new Image("res/Background.png");
		startGame = new Image(PATH+"Startgame.png");
		startGameHighlighted = new Image(PATH+"StartgameHighlighted.png");
		quit = new Image(PATH+"quit.png");
		quitHighlighted = new Image(PATH+"quithighlighted.png");
		highscore = new Image(PATH+"highscore.png");
		highscoreHighlighted = new Image(PATH+"highscorehighlighted.png");
		options = new Image(PATH+"options.png");
		optionsHighlighted = new Image(PATH+"optionshighlighted.png");	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		changeLeafPosition(gc);
		background.draw(0,0,gc.getWidth(),gc.getHeight());
		
		g.drawAnimation(leafAnimation, leafPositionX, leafPositionY);
		title.draw(titlePos.x,titlePos.y);
		
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
		boolean mouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		
		insideStartGame = checkMouse(mouseX, mouseY, startGamePos, startGame);
		insideQuitGame = checkMouse(mouseX, mouseY, quitGamePos, quit);
		insideHighscore= checkMouse(mouseX, mouseY, highscorePos, highscore);
		insideOptionsMenu = checkMouse(mouseX, mouseY, optionsPos, options);
			
		if(mouseClicked && insideStartGame){	
			sbg.enterState(GameApp.PRE_GAME_STATE);
		}
		
		if(mouseClicked && insideOptionsMenu){
			((OptionsState)sbg.getState(GameApp.OPTIONS_STATE)).setPreviousStateID(this.stateID);
			sbg.enterState(GameApp.OPTIONS_STATE);
		}
		
		if(mouseClicked && insideHighscore){
			sbg.enterState(GameApp.HIGH_SCORE_STATE);
		}
		
		if(mouseClicked && insideQuitGame){
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
			leafs[i] = new Image(PATH + "/leaf_animation/"+(i+1)+".png");
		}
		leafAnimation = new Animation(leafs, 100);
	}
	
	public void changeLeafPosition(GameContainer gc){
		Random rand = new Random();
		this.leafPositionX = this.leafPositionX + 1.5f;
		this.leafPositionY = this.leafPositionY + 1f;
		
		if(this.leafPositionX >= gc.getWidth()){
			this.leafPositionX = leafStartPositionX;
			this.leafPositionY = leafStartPositionY[rand.nextInt(12)];
		}
		
		if(this.leafPositionY >= gc.getHeight()){
			this.leafPositionX = leafStartPositionX;
			this.leafPositionY = leafStartPositionY[rand.nextInt(12)];
		}
	}
}
