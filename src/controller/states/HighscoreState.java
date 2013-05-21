package controller.states;

import java.io.IOException;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controller.HighscoreManager;


public class HighscoreState extends BasicGameState {
	
	private int stateID = -1;
	private final String PATH = "res/HighscoreMenu/";
	private static HighscoreState instance = null;
	
	private Image background, title, mainmenu, mainmenuH, clear, clearH, total;
	private Animation gem, coin, foe;
	private Vec2 titlePos, clearPos,mainMenuPos, coinPos, gemPos, foePos, totalPos;
	
	private boolean insideMainMenu = false;
	private boolean insideClear = false;
	
	private HighscoreManager highscoremanager;
	
	private AngelCodeFont acf;
	
	private HighscoreState(int id){
		this.stateID = id;
	}
	
	public static HighscoreState getInstance(){
		if(instance == null){
			return new HighscoreState(GameApp.HIGHSCORESTATE);
		}
			return instance;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		highscoremanager = new HighscoreManager();
		acf = new AngelCodeFont("res/Font/font.fnt", "res/Font/font_0.png");
		
		initCoin();
		initGem();
		initFoe();
		total = new Image(PATH+"total.png");
		clear = new Image(PATH+"clear.png");
		clearH = new Image(PATH+"clearhighlighted.png");
		background = new Image("res/background.png");
		title = new Image(PATH+"highscore_title.png");
		mainmenu = new Image("res/GameOver/MainMenu.png");
		mainmenuH = new Image("res/GameOver/MainMenuH.png");
		
		titlePos = new Vec2(background.getWidth()/2 - title.getWidth()/2, 10);
		clearPos = new Vec2(500,500);
		mainMenuPos = new Vec2(650,500);
		coinPos = new Vec2(368,135);
		gemPos = new Vec2(485,135);
		foePos = new Vec2(610,135);
		totalPos = new Vec2(720,140);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw(0, 0);
		title.draw(titlePos.x,titlePos.y);	
		acf.drawString(70, 170, highscoremanager.getHighscoreString());
		coin.draw(coinPos.x, coinPos.y);
		gem.draw(gemPos.x, gemPos.y);
		foe.draw(foePos.x,foePos.y);
		total.draw(totalPos.x,totalPos.y);
		
		if(insideMainMenu){
			mainmenuH.draw(mainMenuPos.x,mainMenuPos.y);
		}
		else{
			mainmenu.draw(mainMenuPos.x,mainMenuPos.y);
		}
		
		if(insideClear){
			clearH.draw(clearPos.x, clearPos.y);
		}
		else{
			clear.draw(clearPos.x, clearPos.y);
		}
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		Input input = arg0.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		insideMainMenu = checkMouse(mouseX,mouseY,mainMenuPos,mainmenu);
		insideClear = checkMouse(mouseX, mouseY, clearPos, clear);
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideMainMenu){
			arg1.enterState(GameApp.MAINMENUSTATE);
		}
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideClear){
			try {
				clearHighscore();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public void initCoin() throws SlickException{
		Image[] coinImages = new Image[8];
		coinImages[0] = new Image("res/Collectibles/Coin/coin_1.png");
		coinImages[1] = new Image("res/Collectibles/Coin/coin_2.png");
		coinImages[2] = new Image("res/Collectibles/Coin/coin_3.png");
		coinImages[3] = new Image("res/Collectibles/Coin/coin_4.png");
		coinImages[4] = new Image("res/Collectibles/Coin/coin_5.png");
		coinImages[5] = new Image("res/Collectibles/Coin/coin_6.png");
		coinImages[6] = new Image("res/Collectibles/Coin/coin_7.png");
		coinImages[7] = new Image("res/Collectibles/Coin/coin_8.png");
		
		coin = new Animation(coinImages, 100);
	}
	
	public void initGem() throws SlickException{
		Image[] gemImages = new Image[8];
		gemImages[0] = new Image("res/Collectibles/Gem/gem_0.png");
		gemImages[1] = new Image("res/Collectibles/Gem/gem_1.png");
		gemImages[2] = new Image("res/Collectibles/Gem/gem_2.png");
		gemImages[3] = new Image("res/Collectibles/Gem/gem_3.png");
		gemImages[4] = new Image("res/Collectibles/Gem/gem_4.png");
		gemImages[5] = new Image("res/Collectibles/Gem/gem_5.png");
		gemImages[6] = new Image("res/Collectibles/Gem/gem_6.png");
		gemImages[7] = new Image("res/Collectibles/Gem/gem_7.png");
		gem = new Animation(gemImages, 150);
	}
	
	public void initFoe() throws SlickException{
		Image[] foeImages = new Image[12];
		foeImages[0] = new Image("res/foe/moving_foe/foe_right/foe_right_1.png");
		foeImages[1] = new Image("res/foe/moving_foe/foe_right/foe_right_2.png");
		foeImages[2] = new Image("res/foe/moving_foe/foe_right/foe_right_3.png");
		foeImages[3] = new Image("res/foe/moving_foe/foe_right/foe_right_4.png");
		foeImages[4] = new Image("res/foe/moving_foe/foe_right/foe_right_5.png");
		foeImages[5] = new Image("res/foe/moving_foe/foe_right/foe_right_6.png");
		foeImages[6] = new Image("res/foe/moving_foe/foe_right/foe_right_7.png");
		foeImages[7] = new Image("res/foe/moving_foe/foe_right/foe_right_8.png");
		foeImages[8] = new Image("res/foe/moving_foe/foe_right/foe_right_9.png");
		foeImages[9] = new Image("res/foe/moving_foe/foe_right/foe_right_10.png");
		foeImages[10] = new Image("res/foe/moving_foe/foe_right/foe_right_11.png");
		foeImages[11] = new Image("res/foe/moving_foe/foe_right/foe_right_12.png");
		
		foe= new Animation(foeImages, 150);
	}
	
	public boolean checkMouse(int mouseX, int mouseY, Vec2 pos, Image image){
		if((mouseX >= pos.x && mouseX <= pos.x + image.getWidth()) &&
	            (mouseY >= pos.y && mouseY <= pos.y + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}
	
	public void clearHighscore() throws IOException{
		highscoremanager.clearFile();
	}
}
