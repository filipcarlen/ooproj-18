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
	private Image background = null;
	private Image startGame = null;
	private Image startGameHighlighted = null;
	private static int startMenuX = 230;
	private static int startMenuY = 250;
	private boolean insideStartGame = false;
	
	
	public MainMenuState(int stateID){
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("res/MainMenu/MainMenu.png");
		startGame = new Image("res/MainMenu/Startgame.png");
		startGameHighlighted = new Image("res/MainMenu/StartgameHighlighted.png");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw(0,0);
		if(insideStartGame){
			startGameHighlighted.draw(startMenuX, startMenuY);
		}
		else{
			startGame.draw(startMenuX,startMenuY);
		}	
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		Input input = arg0.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		if((mouseX >= startMenuX && mouseX <= startMenuX + startGame.getWidth()) &&
	            (mouseY >= startMenuY && mouseY <= startMenuY + startGame.getHeight())){
					insideStartGame = true;
		}	
		else{
			insideStartGame = false;
		}
			
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideStartGame){
				arg1.enterState(GameApp.PLAYSTATE);
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}

}
