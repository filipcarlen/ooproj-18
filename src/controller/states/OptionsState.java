package controller.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class OptionsState extends BasicGameState {
	
	private int stateID = -1;
	private final String PATH = "res/OptionsMenu/";
	private Image background = null;
	private Image back = null;
	private Image backHighlighted = null;
	private boolean insideBack = false;
	private int backX = 50;
	private int backY = 500;

	public OptionsState(int stateID){
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("res/Background.png");
		back = new Image(PATH+"back.png");
		backHighlighted = new Image(PATH+"backhighlighted.png");
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw(0,0);
		
		if(insideBack){
			backHighlighted.draw(backX,backY);
		}
		else{
			back.draw(backX,backY);
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		insideBack = checkMouse(mouseX, mouseY, backX, backY, back);
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideBack){
			sbg.enterState(GameApp.MAINMENUSTATE);
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
