package controller.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PreGameState extends BasicGameState {
	
	private int stateID = -1;
	private Image background;
	private static PreGameState instance = null;
	
	private PreGameState(int stateID){
		this.stateID = stateID;
	}
	
	public static PreGameState getInstance(){
		if(instance == null){
			instance = new PreGameState(GameApp.PREGAMESTATE);
		}
		return instance;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("res/Background.png");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw(0, 0);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	

}
