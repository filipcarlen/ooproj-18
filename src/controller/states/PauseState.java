package controller.states;

import model.HeroModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.SoundType;
import utils.Sounds;
import controller.HighscoreManager;

public class PauseState extends BasicGameState {

	private int stateID;
	
	private Image background;
	
	private Image resume, resumeH, restart, restartH, options, optionsH, mainMenu, mainMenuH, quitGame, quitGameH;
	private Vec2 resumePos, restartPos, optionsPos, mainMenuPos, quitGamePos;
	
	private boolean insideResume = false;	
	private boolean insideRestart = false;
	private boolean insideOptions = false;
	private boolean insideMainMenu = false;
	private boolean insideQuitGame = false;
	
	public PauseState(int stateID){
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException{
		Sounds.getInstance().stopMusic();
		Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
}
