package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	
	private int stateID = -1;
	private final String PATH = "res/MainMenu/";
	private Sound soundtrack = null;
	
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
	
	private static int startMenuX = 330;
	private static int startMenuY = 170;
	private static int optionsMenuX = 330;
	private static int optionsMenuY = 370;
	private static int loadGameMenuX = 330;
	private static int loadGameMenuY = 270;
	private static int endGameX = 330;
	private static int endGameY = 470;
	
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
		soundtrack = new Sound(PATH+"Soundtrack.wav");
		soundtrack.loop();
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
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
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
}
