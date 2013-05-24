package controller.states;

import model.HeroModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.SoundType;
import utils.Sounds;
import controller.HighscoreManager;
import controller.IPlayStateController;

public class PauseState extends BasicGameState {

	private int stateID;
	private IPlayStateController playState;
	
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
		this.background = new Image("res/PauseMenu/bakgrund.png");
		this.resume = new Image("res/GameOver/PlayAgain.png");
		this.resumeH = new Image("res/GameOver/PlayAgainH.png");
		
		this.resumePos = new Vec2(400, 200);

		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException{
		Sounds.getInstance().stopMusic();
		Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		this.playState.render(gc, sbg, g);
		if(gc.isFullscreen()){
			this.background.draw(0, 0, 0.7f);
		} else{
			this.background.draw(0,0, 0.5f);

		}
		this.resume.draw(resumePos.x, resumePos.y);
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int g)
			throws SlickException {
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		insideResume = checkMouse(mouseX, mouseY, resumePos, resume);
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideResume){
			gc.exit();
		}
	}
	
	public boolean checkMouse(float mouseX, float mouseY, Vec2 imagePos, Image image){
		if((mouseX >= imagePos.x && mouseX <= imagePos.x + image.getWidth()) &&
	            (mouseY >= imagePos.y && mouseY <= imagePos.y + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public void setPlayState(IPlayStateController playState){
		this.playState = playState;
	}
}
