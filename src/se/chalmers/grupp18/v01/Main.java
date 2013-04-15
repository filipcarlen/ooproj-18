package se.chalmers.grupp18.v01;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame{

	private static final String gamename = "The Saga";
	private static final int play = 0;
	
	public Main(String gamename) {
		super(gamename);
		this.addState(new Play(play));
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(play).init(gc, this);
		this.enterState(play);
	}
	
	public static void main(String []args){
		AppGameContainer appgc;
		try{
			appgc = new AppGameContainer(new Main(gamename));
			appgc.setDisplayMode(600, 600, false);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

}
