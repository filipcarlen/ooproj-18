package controller.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState{

	private int stateID;
	
	private Animation enemy;
	private Animation coin;
	private Animation gem;
	private Animation dancingHero;
	private Animation sadHero;
	private Animation discoBall;
	private Image failSign;
	private Image danceFloor;
	private Image zero, one, two, three, four, five, six, seven, eight, nine, cross;
	private Image playAgain, playAgainH;
	private Image tryAgain, tryAgainH;
	private Image mainMenu, mainMenuH;
	private Image quit, quitH;
	private Image total, points;
	private Image youWin, youLose;
	
	private Image background;
	
	
	
	public GameOverState(int stateID){
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		this.background.draw(0,0);
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
	}

	@Override
	public int getID() {
		return 0;
	}
		

}
