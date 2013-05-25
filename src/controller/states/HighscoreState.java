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
	private final String PATH = "res/highscore_menu/";
	private static HighscoreState instance = null;
	
	private Image background, title, mainmenu, mainmenuH, clear, clearH, total;
	private Animation gem, coin, foe;
	private Vec2 titlePos, clearPos,mainMenuPos, coinPos, gemPos, foePos, totalPos, stringPos;
	
	private boolean insideMainMenu = false;
	private boolean insideClear = false;
	
	private HighscoreManager highscoremanager;
	private AngelCodeFont font;
	
	private HighscoreState(int id){
		this.stateID = id;
	}
	
	public static HighscoreState getInstance(){
		if(instance == null){
			return new HighscoreState(GameApp.HIGH_SCORE_STATE);
		}
			return instance;
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg)
			throws SlickException {	
		titlePos = new Vec2(gc.getWidth()/2 - title.getWidth()/2, 10);
		clearPos = new Vec2(gc.getWidth()*.05f,gc.getHeight()*.85f);
		mainMenuPos = new Vec2(gc.getWidth()*.68f,gc.getHeight()*.85f);
		stringPos = new Vec2(gc.getWidth()/2-750/2,gc.getHeight()/2-250/2);
		coinPos = new Vec2(gc.getWidth()/2-150/2,gc.getHeight()/2-320/2);
		gemPos = new Vec2(coinPos.x+115,gc.getHeight()/2-320/2);
		foePos = new Vec2(gemPos.x+120,gc.getHeight()/2-320/2);
		totalPos = new Vec2(foePos.x+100,gc.getHeight()/2-310/2);
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		highscoremanager = new HighscoreManager();
		font = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.png");
		
		initCoin();
		initGem();
		initFoe();
		total = new Image(PATH+"total.png");
		clear = new Image(PATH+"clear.png");
		clearH = new Image(PATH+"clearhighlighted.png");
		background = new Image("res/background.png");
		title = new Image(PATH+"highscore_title.png");
		mainmenu = new Image("res/game_over_menu/MainMenu.png");
		mainmenuH = new Image("res/Game_over_menu/MainMenuH.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		title.draw(titlePos.x,titlePos.y);	
		font.drawString(stringPos.x, stringPos.y, highscoremanager.getHighscoreString());
		coin.draw(coinPos.x, coinPos.y);
		gem.draw(gemPos.x, gemPos.y);
		foe.draw(foePos.x, foePos.y);
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
		boolean mouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		
		insideMainMenu = checkMouse(mouseX,mouseY,mainMenuPos,mainmenu);
		insideClear = checkMouse(mouseX, mouseY, clearPos, clear);
		
		if(mouseClicked && insideMainMenu){
			arg1.enterState(GameApp.MAIN_MENU_STATE);
		}
		
		if(mouseClicked && insideClear){
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
		for(int i = 0; i<coinImages.length; i++){
			coinImages[i] = new Image("res/Collectibles/Coin/coin_"+(i+1) + ".png");
		}		
		coin = new Animation(coinImages, 100);
	}
	
	public void initGem() throws SlickException{
		Image[] gemImages = new Image[8];
		for(int i = 0; i<gemImages.length; i++){
			gemImages[i] = new Image("res/Collectibles/Gem/gem_"+(i)+".png");
		}
		gem = new Animation(gemImages, 150);
	}
	
	public void initFoe() throws SlickException{
		Image[] foeImages = new Image[12];
		for(int i = 0; i<foeImages.length; i++){
			foeImages[i] = new Image("res/foe/moving_foe/foe_right/foe_right_"+(i+1)+".png");
		}		
		foe = new Animation(foeImages, 150);
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
