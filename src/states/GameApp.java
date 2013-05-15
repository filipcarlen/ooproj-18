package states;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Controls;

public class GameApp extends StateBasedGame{
	public static final String gamename = "The Game";
	public static final int MAINMENUSTATE = 0;
	public static final int PLAYSTATE = 1;
	public static final int OPTIONSSTATE = 2;
	
	public GameApp(String gamename) {
		super(gamename);
		this.addState(MainMenuState.getInstance());
		this.addState(new PlayState(PLAYSTATE));
		this.addState(new OptionsState(OPTIONSSTATE));
		this.enterState(MAINMENUSTATE);
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		//this.getState(MAINMENUSTATE).init(gc, this);
		//this.getState(PLAYSTATE).init(gc, this);
		//this.getState(OPTIONSSTATE).init(gc, this);

	}
	
	@Override
	public void update(GameContainer gc, int delta){
		try {
			super.update(gc, delta);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		if(this.getCurrentStateID() == PLAYSTATE){
			if(Controls.getInstance().check("pause") && this.getState(PLAYSTATE).isUpdatePaused()){
				this.getState(PLAYSTATE).unpauseRender();
				this.getState(PLAYSTATE).unpauseUpdate();
			}
		}
	}
	
	public static void main(String []args){
		try{
			AppGameContainer appgc = new AppGameContainer(new GameApp(gamename));
			appgc.setDisplayMode(900, 600, false);
			appgc.setTargetFrameRate(200);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	} }
