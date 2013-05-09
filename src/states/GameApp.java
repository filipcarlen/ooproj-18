package states;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameApp extends StateBasedGame{
	public static final int MAINMENUSTATE = 1;
	public static final int PLAYSTATE = 0;
	private static String gamename = "The Game";
	
	public GameApp(String gamename) {
		super(gamename);
		GameApp.gamename = gamename;

	}

	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new PlayState(PLAYSTATE));
		this.getState(PLAYSTATE).init(gc, this);
		this.enterState(MAINMENUSTATE);
	}
	
	public static void main(String []args){
		AppGameContainer appgc;
		try{
			appgc = new AppGameContainer(new GameApp("The Game"));
			appgc.setDisplayMode(900, 600, false);
			appgc.setTargetFrameRate(200);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	} 
}
