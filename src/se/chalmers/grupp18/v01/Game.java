package se.chalmers.grupp18.v01;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{
	private static final int play = 0;
	
	public Game(String gamename) {
		super(gamename);
		this.addState(new PlayState(play));
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(play).init(gc, this);
		this.enterState(play);
	}

}
