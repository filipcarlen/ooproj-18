package controller.states;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Controls;

public class GameApp extends StateBasedGame{
	public static final String gamename = "The Game";
	public static final int MAINMENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int OPTIONSSTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	public static final int HIGHSCORESTATE = 4;
	public static final int PREGAMESTATE = 5;
	public static final int PAUSESTATE = 6;

	public static AppGameContainer appgc;
	public static final float DEFAULT_WIDTH = 900f;
	public static final float DEFAULT_HEIGHT = 600f;
	
	public GameApp(String gamename) {
		super(gamename);
		this.addState(MainMenuState.getInstance());
		this.addState(PlayState.getInstance());
		this.addState(new OptionsState(OPTIONSSTATE));
		GameOverState gameOverState = new GameOverState(GAMEOVERSTATE);
		gameOverState.setPlayState(PlayState.getInstance());
		this.addState(gameOverState);
		this.addState(HighscoreState.getInstance());
		this.addState(PreGameState.getInstance());
		this.addState(new PauseState(PAUSESTATE));
		this.enterState(MAINMENUSTATE);
	}

	public void initStatesList(GameContainer gc) throws SlickException {

	}
	
	@Override
	public void update(GameContainer gc, int delta){
		try {
			super.update(gc, delta);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		if(this.getCurrentStateID() == PAUSESTATE){
			if(Controls.getInstance().check("pause")){
				this.enterState(PLAYSTATE);
			}
		}
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
