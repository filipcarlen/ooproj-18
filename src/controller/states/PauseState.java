package controller.states;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Controls;
import utils.SoundType;
import utils.Sounds;

/** 
 * This state is entered when the game is paused. From here you are able to resume, 
 * restart, change options, go to main menu or quit the game.
 * 
 * @author Project Group 18 (Chalmers, 2013)
 */

public class PauseState extends BasicGameState {

	private int stateID;
	private static PauseState instance = null;

	
	private final String PATH = "res/pause_menu/";
	
	private Image background;
	
	private Image resume, resumeH, restart, restartH, options, optionsH, mainMenu, mainMenuH, quitGame, quitGameH;
	private Vec2 resumePos, restartPos, optionsPos, mainMenuPos, quitGamePos;
	
	private boolean insideResume = false;	
	private boolean insideRestart = false;
	private boolean insideOptions = false;
	private boolean insideMainMenu = false;
	private boolean insideQuitGame = false;
	
	
	private PauseState(int stateID){
		this.stateID = stateID;
	}

	public static PauseState getInstance(){
		if(instance == null){
			instance = new PauseState(GameApp.PAUSE_STATE);
		}
		return instance;
	}
	
	/**
	 * In this method all the images and animations are initialized
	 */
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		this.background = new Image(PATH + "background.png");
		
		this.resume = new Image(this.PATH + "resume.png");
		this.resumeH = new Image(this.PATH + "resumeH.png");
		this.restart = new Image(this.PATH + "restart.png");
		this.restartH = new Image(this.PATH + "restartH.png");
		this.options = new Image(this.PATH + "options.png");
		this.optionsH = new Image(this.PATH + "optionsH.png");
		this.mainMenu = new Image(this.PATH + "mainMenu.png");
		this.mainMenuH = new Image(this.PATH + "mainMenuH.png");
		this.quitGame = new Image(this.PATH + "quitGame.png");
		this.quitGameH = new Image(this.PATH + "quitGameH.png");
	}
	
	/**
	 * In this method all the positions of the images and animations are initialized 
	 */
	public void initPositions(GameContainer gc, StateBasedGame sbg){
		
		float screenWidth = gc.getWidth();
		float screenHeight = gc.getHeight();
		
		this.resumePos = new Vec2(screenWidth/2 - resume.getWidth()/2, screenHeight*0.42f);
		this.restartPos = new Vec2(resumePos.x, screenHeight*0.52f);
		this.optionsPos = new Vec2(resumePos.x, screenHeight*0.62f);
		this.mainMenuPos = new Vec2(resumePos.x, screenHeight*0.72f);
		this.quitGamePos = new Vec2(resumePos.x, screenHeight*0.82f);
	}
	
	/**
	 * This method is called every time this state is entered. 
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
		initPositions(gc, sbg);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		sbg.getState(GameApp.PLAY_STATE).render(gc, sbg, g);
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		
		if(insideResume){
			this.resumeH.draw(resumePos.x, resumePos.y);
		} else{
			this.resume.draw(resumePos.x, resumePos.y);
		}
		
		if(insideRestart){
			this.restartH.draw(restartPos.x, restartPos.y);
		} else{
			this.restart.draw(restartPos.x, restartPos.y);
		}
		
		if(insideOptions){
			this.optionsH.draw(optionsPos.x, optionsPos.y);
		} else{
			this.options.draw(optionsPos.x, optionsPos.y);
		}
		
		if(insideMainMenu){
			this.mainMenuH.draw(mainMenuPos.x, mainMenuPos.y);
		} else{
			this.mainMenu.draw(mainMenuPos.x, mainMenuPos.y);
		}
		
		if(insideQuitGame){
			this.quitGameH.draw(quitGamePos.x, quitGamePos.y);
		} else{
			this.quitGame.draw(quitGamePos.x, quitGamePos.y);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		boolean mouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		
		insideResume = checkMouse(mouseX, mouseY, resumePos, resume);
		insideRestart = checkMouse(mouseX, mouseY, restartPos, restart);
		insideOptions = checkMouse(mouseX, mouseY, optionsPos, options);
		insideMainMenu = checkMouse(mouseX, mouseY, mainMenuPos, mainMenu);
		insideQuitGame = checkMouse(mouseX, mouseY, quitGamePos, quitGame);
		
		if((mouseClicked && insideResume) || Controls.getInstance().check("pause")){
			sbg.enterState(GameApp.PLAY_STATE);
		}
		if(mouseClicked && insideRestart){
			((PlayState)sbg.getState(GameApp.PLAY_STATE)).reTry();
			sbg.enterState(GameApp.PLAY_STATE);
		}
		if(mouseClicked && insideOptions){
			((OptionsState)sbg.getState(GameApp.OPTIONS_STATE)).setPreviousStateID(this.stateID);
			sbg.enterState(GameApp.OPTIONS_STATE);
		}
		if(mouseClicked && insideMainMenu){
			sbg.enterState(GameApp.MAIN_MENU_STATE);
		}
		if(mouseClicked && insideQuitGame){
			gc.exit();
		}
	}
	
	/**
	 * This method is used to check if the mouse is inside the image field specified
	 * @param mouseX, mouseY, imagePos, image
	 * @return
	 */
	public boolean checkMouse(float mouseX, float mouseY, Vec2 imagePos, Image image){
		if((mouseX >= imagePos.x && mouseX <= imagePos.x + image.getWidth()) &&
	            (mouseY >= imagePos.y && mouseY <= imagePos.y + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}
}
