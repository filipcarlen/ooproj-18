package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends BasicGameState {
	
	private int stateID = -1;
	private final String PATH = "res/MainMenu/";
	
	private Image background = null;
	private Image startGame = null;
	private Image startGameHighlighted = null;
	private Image endGame = null;
	private Image endGameHighlighted = null;
	private Image loadGame = null;
	private Image loadGameHighlighted = null;
	private Image options = null;
	private Image optionsHighlighted = null;
	
	private static int startMenuX = 230;
	private static int startMenuY = 150;
	private static int optionsMenuX = 230;
	private static int optionsMenuY = 350;
	private static int loadGameMenuX = 230;
	private static int loadGameMenuY = 250;
	private static int endGameX = 230;
	private static int endGameY = 450;
	
	private boolean insideStartGame = false;
	private boolean insideOptionsMenu = false;
	private boolean insideLoadGame = false;
	private boolean insideEndGame = false;
	
	
	public MainMenuState(int stateID){
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		background = new Image(PATH+"MainMenu.png");
		startGame = new Image(PATH+"startgame.png");
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
