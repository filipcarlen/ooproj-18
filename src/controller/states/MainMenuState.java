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
import utils.Utils;

/**
 * The Main menu state.
 * @author group 18
 */
public class MainMenuState extends BasicGameState {
	
	private int stateID = -1;
	private final String PATH = "res/main_menu/";
	
	private Image title, background, startGame, startGameHighlighted, quit, quitHighlighted, highscore,
						highscoreHighlighted, options, optionsHighlighted;
	
	private Image[] leafImages;
	private Animation leafAnimation;
	private final int LEAF_ANIMATION_DURATION = 100;
	
	private Vec2 startGameImagePosition;
	private Vec2 optionsImagePosition;
	private Vec2 highscoreImagePosition;
	private Vec2 quitGameImagePosition;
	private Vec2 titleImagePosition;
		
	private float leafPositionX;;
	private float leafPositionY;
	private final float LEAF_START_POSITION_X = 0;
	private final float[] LEAF_START_POSITION_Y = {-500,-400,-300,-200,-100,0,100,200,300,400,500,600};
	
	private boolean mouseInsideStartGame,mouseinsideOptionsMenu, mouseInsideHighscore, mouseInsideQuitGame;
	
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
		titleImagePosition = new Vec2(gc.getWidth()/2-title.getWidth()/2,0);
		startGameImagePosition = new Vec2(gc.getWidth()/2-startGame.getWidth()/2,gc.getHeight()*.28f);
		highscoreImagePosition = new Vec2(gc.getWidth()/2-highscore.getWidth()/2, gc.getHeight()*.45f);
		optionsImagePosition = new Vec2(gc.getWidth()/2-options.getWidth()/2,gc.getHeight()*.65f);
		quitGameImagePosition = new Vec2(gc.getWidth()/2-quit.getWidth()/2,gc.getHeight()*.82f);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		leafPositionX = 0;
		leafPositionY = 0;
		initLeafs();
		title = new Image(PATH + "title.png");
		background = new Image("res/background.png");
		startGame = new Image(PATH+"Startgame.png");
		startGameHighlighted = new Image(PATH+"Startgamehighlighted.png");
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
		title.draw(titleImagePosition.x,titleImagePosition.y);
		
		if(mouseInsideStartGame){
			startGameHighlighted.draw(startGameImagePosition.x,startGameImagePosition.y);
		}
		else{
			startGame.draw(startGameImagePosition.x, startGameImagePosition.y);
		}
		
		if(mouseInsideQuitGame){
			quitHighlighted.draw(quitGameImagePosition.x,quitGameImagePosition.y);
		}
		else{
			quit.draw(quitGameImagePosition.x, quitGameImagePosition.y);
		}
		
		if(mouseInsideHighscore){
			highscoreHighlighted.draw(highscoreImagePosition.x,highscoreImagePosition.y);
		}
		else{
			highscore.draw(highscoreImagePosition.x, highscoreImagePosition.y);
		}
		
		if(mouseinsideOptionsMenu){
			optionsHighlighted.draw(optionsImagePosition.x,optionsImagePosition.y);
		}
		else{
			options.draw(optionsImagePosition.x,optionsImagePosition.y);
		}
		
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		boolean mouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		
		mouseInsideStartGame = Utils.isMouseInsideImage(mouseX, mouseY, startGameImagePosition, startGame);
		mouseInsideQuitGame = Utils.isMouseInsideImage(mouseX, mouseY, quitGameImagePosition, quit);
		mouseInsideHighscore= Utils.isMouseInsideImage(mouseX, mouseY, highscoreImagePosition, highscore);
		mouseinsideOptionsMenu = Utils.isMouseInsideImage(mouseX, mouseY, optionsImagePosition, options);
			
		if(mouseClicked && mouseInsideStartGame){	
			sbg.enterState(GameApp.PRE_GAME_STATE);
		}
		
		if(mouseClicked && mouseinsideOptionsMenu){
			((OptionsState)sbg.getState(GameApp.OPTIONS_STATE)).setPreviousStateID(this.stateID);
			sbg.enterState(GameApp.OPTIONS_STATE);
		}
		
		if(mouseClicked && mouseInsideHighscore){
			sbg.enterState(GameApp.HIGH_SCORE_STATE);
		}
		
		if(mouseClicked && mouseInsideQuitGame){
			gc.exit();
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	
	public void initLeafs() throws SlickException{
		leafImages = new Image[14];
		for(int i = 0; i<leafImages.length;i++){
			leafImages[i] = new Image(PATH + "leaf_animation/"+(i+1)+".png");
		}
		leafAnimation = new Animation(leafImages, LEAF_ANIMATION_DURATION);
	}
	/**
	 * Method changing the position of leaf animation
	 * @param GameContainter gc
	 */
	public void changeLeafPosition(GameContainer gc){
		Random rand = new Random();
		this.leafPositionX = this.leafPositionX + 1.5f;
		this.leafPositionY = this.leafPositionY + 1f;
		
		if(this.leafPositionX >= gc.getWidth()){
			this.leafPositionX = LEAF_START_POSITION_X;
			this.leafPositionY = LEAF_START_POSITION_Y[rand.nextInt(12)];
		}
		
		if(this.leafPositionY >= gc.getHeight()){
			this.leafPositionX = LEAF_START_POSITION_X;
			this.leafPositionY = LEAF_START_POSITION_Y[rand.nextInt(12)];
		}
	}
}
