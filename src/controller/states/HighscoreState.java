package controller.states;

import java.awt.Font;
import java.io.IOException;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import controller.HighscoreManager;


public class HighscoreState extends BasicGameState {
	
	private int stateID = -1;
	private final String PATH = "res/HighscoreMenu/";
	private static HighscoreState instance = null;
	
	private Image background, title, name, time, mob, mainmenu, mainmenuH;
	private Image one, two, three, four, five, six,
					seven, eight, nine;
	private Animation gem, coin;
	private Vec2 titlePos;
	
	private boolean insideMainMenu = false;
	private Vec2 mainMenuPos;
	
	private HighscoreManager highscoremanager;
	
	AngelCodeFont acf;
	
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
		addHighscores();
		initCoin();
		initGem();
		one = new Image(PATH+"1.png");
		two = new Image(PATH+"2.png");
		three = new Image(PATH+"3.png");
		four = new Image(PATH+"4.png");
		five = new Image(PATH+"5.png");
		six = new Image(PATH+"6.png");
		seven = new Image(PATH+"7.png");
		eight = new Image(PATH+"8.png");
		background = new Image("res/background.png");
		title = new Image(PATH+"highscore_title.png");
		mainmenu = new Image("res/GameOver/MainMenu.png");
		mainmenuH = new Image("res/GameOver/MainMenuH.png");
		
		titlePos = new Vec2(background.getWidth()/2 - title.getWidth()/2, 10);
		mainMenuPos = new Vec2(650,500);
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw(0, 0);
		title.draw(titlePos.x,titlePos.y);	
		//arg2.drawString(highscoremanager.getHighscoreString(),background.getWidth()/2,170);
		acf.drawString(70, 170, highscoremanager.getHighscoreString());
		/**one.draw(75,150);
		two.draw(75,200);
		three.draw(75,250);
		four.draw(75,300);
		five.draw(75,350);
		six.draw(75,400);
		seven.draw(75,450);
		eight.draw(75,500);*/
		
		if(insideMainMenu){
			mainmenuH.draw(mainMenuPos.x,mainMenuPos.y);
		}
		else{
			mainmenu.draw(mainMenuPos.x,mainMenuPos.y);
		}	
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		
		Input input = arg0.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		insideMainMenu = checkMouse(mouseX,mouseY,mainMenuPos,mainmenu);
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideMainMenu){
			arg1.enterState(GameApp.MAINMENUSTATE);
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
	
	public boolean checkMouse(int mouseX, int mouseY, Vec2 pos, Image image){
		if((mouseX >= pos.x && mouseX <= pos.x + image.getWidth()) &&
	            (mouseY >= pos.y && mouseY <= pos.y + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}
	
	public void addHighscores(){
		highscoremanager.addScore("CarlŽn", 100, 5, 15, 20);
		highscoremanager.addScore("Lager", 104, 7, 45, 40);
		highscoremanager.addScore("Elin", 150, 8, 59, 50);
		highscoremanager.addScore("LinnŽa", 140, 7, 13, 258);
	}
	
	public void clearHighscore() throws IOException{
		highscoremanager.clearFile();
	}
	

}
