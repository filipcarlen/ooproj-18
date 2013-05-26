package controller.states;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameApp extends StateBasedGame{
	public static final String gamename = "Forest Frenzy";
	public static final int MAIN_MENU_STATE = 0;
	public static final int PLAY_STATE = 1;
	public static final int OPTIONS_STATE = 2;
	public static final int GAME_OVER_STATE = 3;
	public static final int HIGH_SCORE_STATE = 4;
	public static final int PRE_GAME_STATE = 5;
	public static final int PAUSE_STATE = 6;

	public static AppGameContainer appgc;
	public static final float DEFAULT_WIDTH = 900f;
	public static final float DEFAULT_HEIGHT = 600f;
	
	public GameApp(String gamename) {
		super(gamename);
		this.addState(MainMenuState.getInstance());
		this.addState(PlayState.getInstance());
		this.addState(OptionsState.getInstance());
		this.addState(GameOverState.getInstance());
		this.addState(HighscoreState.getInstance());
		this.addState(PreGameState.getInstance());
		this.addState(PauseState.getInstance());
		this.enterState(MAIN_MENU_STATE);
	}

	public void initStatesList(GameContainer gc) throws SlickException {

	}
	
	public static void main(String []args){
		try{
			appgc = new AppGameContainer(new GameApp(gamename));
			appgc.setDisplayMode(900, 600, false);
			appgc.setTargetFrameRate(200);
			appgc.setShowFPS(false);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	} 
}
