package se.chalmers.grupp18.v01;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameApp extends StateBasedGame{
	private static final int playState = 0;
	private static String gamename;
	
	public GameApp(String gamename) {
		super(gamename);
		GameApp.gamename = gamename;
		this.addState(new PlayState(playState));

	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(playState).init(gc, this);
		this.enterState(playState);
	}
	
	public static void main(String []args){
		AppGameContainer appgc;
		try{
			appgc = new AppGameContainer(new GameApp(gamename));
			appgc.setDisplayMode(900, 600, false);
			appgc.setTargetFrameRate(60);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
	
	

}
