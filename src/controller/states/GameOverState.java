package controller.states;

import java.util.ArrayList;

import model.HeroModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Sounds;

import controller.IPlayStateController;

public class GameOverState extends BasicGameState{

	private int stateID;
	private IPlayStateController playState;
	private float spacing, scaling;
	private float crossScaling = 0.8f;
	
	private boolean isWin;
	
	private ArrayList<Image> coinAmount = new ArrayList<Image>(); 
	private ArrayList<Image> foeAmount = new ArrayList<Image>(); 
	private ArrayList<Image> gemAmount = new ArrayList<Image>(); 
	private ArrayList<Image> totalScore = new ArrayList<Image>(); 
	
	private Vec2 coinAmountPos = new Vec2(0,0);
	private Vec2 foeAmountPos = new Vec2(0,0);
	private Vec2 gemAmountPos = new Vec2(0,0);
	private Vec2 totalScorePos = new Vec2(0,0);
	
	private String PATH = "res/Collectibles";
	
	private Animation coin;
	private Animation foe;
	private Animation gem;
	private Animation dancingHero;
	private Animation sadHero;
	private Animation discoBall;
	
	private Vec2 coinPos = new Vec2(20,0);
	private Vec2 foePos = new Vec2(20,0);
	private Vec2 gemPos = new Vec2(20,0); 
	private Vec2 dancingHeroPos = new Vec2(0,0);
	private Vec2 sadHeroPos = new Vec2(0,0);
	private Vec2 discoBallPos = new Vec2(0,0);
	
	private Image failSign;
	private Image danceFloor;
	
	private Image zero, one, two, three, four, five, six, seven, eight, nine, cross;
	private Image total, points;

	private Image playAgain, playAgainH;
	private Image tryAgain, tryAgainH;
	
	private Image mainMenu, mainMenuH;
	
	private Image quitGame, quitGameH;
	
	private Image youWin, youLose;
	
	private Image background;	
	
	private Vec2 crossPos1 = new Vec2(0,0);
	private Vec2 crossPos2 = new Vec2(0,0);
	private Vec2 crossPos3 = new Vec2(0,0);
	private Vec2 totalPos = new Vec2(0,0);
	private Vec2 pointsPos = new Vec2(0,0);
	private Vec2 playAgainPos = new Vec2(0,0); 
	private Vec2 mainMenuPos = new Vec2(0,0);
	private Vec2 quitGamePos = new Vec2(0,0);
	private Vec2 youWinPos;
	private Vec2 youLosePos;
	
	private boolean insideQuitGame = false;
	private boolean insideMainMenu = false;
	private boolean insidePlayAgain = false;


	public GameOverState(int stateID){
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		float screenWidth = arg1.getContainer().getWidth();
		float screenHeight = arg1.getContainer().getHeight();
		this.scaling = 7;
		this.spacing = 20;
		
		initCoin();
		initGem();
		
		this.background = new Image("res/background.png");
		
		this.zero = new Image("res/GameOver/zero.png");
		this.one = new Image("res/GameOver/one.png");
		this.two = new Image("res/GameOver/two.png");
		this.three = new Image("res/GameOver/three.png");
		this.four = new Image("res/GameOver/four.png");
		this.five = new Image("res/GameOver/five.png");
		this.six = new Image("res/GameOver/six.png");
		this.seven = new Image("res/GameOver/seven.png");
		this.eight = new Image("res/GameOver/eight.png");
		this.nine = new Image("res/GameOver/nine.png");
		this.cross = new Image("res/GameOver/cross.png");
		
		this.total = new Image("res/GameOver/total.png");
		this.points = new Image("res/GameOver/points.png");
		
		this.playAgain = new Image("res/GameOver/playAgain.png");
		this.playAgainH = new Image("res/GameOver/playAgainH.png");
		this.tryAgain = new Image("res/GameOver/tryAgain.png");
		this.tryAgainH = new Image("res/GameOver/tryAgainH.png");
		
		this.mainMenu = new Image("res/GameOver/mainMenu.png");
		this.mainMenuH = new Image("res/GameOver/mainMenuH.png");
		
		this.quitGame = new Image("res/GameOver/quitGame.png");
		this.quitGameH = new Image("res/GameOver/quitGameH.png");
		
		this.youWin = new Image("res/GameOver/youWin.png");
		this.youLose = new Image("res/GameOver/youLose.png");
		
		this.youWinPos = new Vec2(screenWidth/2 - youWin.getWidth()/2, this.spacing);
		this.youLosePos = new Vec2(screenWidth/2 - youLose.getWidth()/2, this.spacing);
		
		this.crossPos1 = new Vec2(this.spacing*this.scaling, youWinPos.y + youWin.getHeight() + this.spacing*2);
		this.crossPos2 = new Vec2(this.spacing*this.scaling, crossPos1.y + cross.getHeight()*this.crossScaling + this.spacing*2);
		this.crossPos3 = new Vec2(this.spacing*this.scaling, crossPos2.y + cross.getHeight()*this.crossScaling + this.spacing*2);

		this.coinAmountPos = new Vec2(this.crossPos1.x + cross.getWidth()*this.crossScaling + this.spacing*2, crossPos1.y + cross.getHeight()*this.crossScaling/2 - zero.getHeight()/2);
		this.foeAmountPos = new Vec2(this.crossPos2.x + cross.getWidth()*this.crossScaling + this.spacing*2, crossPos2.y + cross.getHeight()*this.crossScaling/2 - zero.getHeight()/2);
		this.gemAmountPos = new Vec2(this.crossPos3.x + cross.getWidth()*this.crossScaling + this.spacing*2, crossPos3.y + cross.getHeight()*this.crossScaling/2 - zero.getHeight()/2);

		this.coinPos = new Vec2(crossPos1.x/2 - coin.getWidth()/2, crossPos1.y + cross.getHeight()*this.crossScaling/2 - coin.getHeight()/2);
		this.foePos = new Vec2(crossPos1.x/2 - nine.getWidth()/2, crossPos2.y + cross.getHeight()*this.crossScaling/2 - nine.getHeight()/2);
		this.gemPos = new Vec2(crossPos1.x/2 - gem.getWidth()/2, crossPos3.y + cross.getHeight()*this.crossScaling/2 - gem.getHeight()/2);
		
		this.totalPos = new Vec2(this.spacing*2, this.crossPos3.y + this.cross.getHeight() + this.spacing);
		this.totalScorePos = new Vec2(this.totalPos.x + this.total.getWidth() + this.spacing, this.totalPos.y + this.total.getHeight()/2 - this.zero.getHeight()/2);
		this.pointsPos = new Vec2(this.totalScorePos.x + this.zero.getWidth()*3 + this.spacing, this.totalPos.y + this.total.getHeight()/2 - this.points.getHeight()/2);
		
		float tmpSpacing = (float)(screenWidth - quitGame.getWidth()*3)/4f;
		this.quitGamePos = new Vec2(tmpSpacing, screenHeight - quitGame.getHeight() - this.spacing);
		this.mainMenuPos = new Vec2(tmpSpacing*2 + quitGame.getWidth(), screenHeight - quitGame.getHeight() - this.spacing);
		this.playAgainPos = new Vec2(tmpSpacing*3 + quitGame.getWidth()*2, screenHeight - quitGame.getHeight() - this.spacing);
	}
	
	public void initCoin() throws SlickException{
		Image[] coinImages = new Image[8];
		coinImages[0] = new Image(PATH + "/Coin/coin_1.png");
		coinImages[1] = new Image(PATH + "/Coin/coin_2.png");
		coinImages[2] = new Image(PATH + "/Coin/coin_3.png");
		coinImages[3] = new Image(PATH + "/Coin/coin_4.png");
		coinImages[4] = new Image(PATH + "/Coin/coin_5.png");
		coinImages[5] = new Image(PATH + "/Coin/coin_6.png");
		coinImages[6] = new Image(PATH + "/Coin/coin_7.png");
		coinImages[7] = new Image(PATH + "/Coin/coin_8.png");
		
		coin = new Animation(coinImages, 100);
	}
	
	/**
	 * Initialize the animation of a gem
	 * @throws SlickException
	 */
	public void initGem() throws SlickException{
		Image[] gemImages = new Image[8];
		gemImages[0] = new Image(PATH + "/Gem/gem_0.png");
		gemImages[1] = new Image(PATH + "/Gem/gem_1.png");
		gemImages[2] = new Image(PATH + "/Gem/gem_2.png");
		gemImages[3] = new Image(PATH + "/Gem/gem_3.png");
		gemImages[4] = new Image(PATH + "/Gem/gem_4.png");
		gemImages[5] = new Image(PATH + "/Gem/gem_5.png");
		gemImages[6] = new Image(PATH + "/Gem/gem_6.png");
		gemImages[7] = new Image(PATH + "/Gem/gem_7.png");
		gem = new Animation(gemImages, 150);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException{
		Sounds.getInstance().musicIngame.stop();
		Sounds.getInstance().soundtrack.loop();
		HeroModel model = this.playState.getHeroModel();
		this.isWin = !model.isDead();

		this.coinAmount = numberToImages(model.getCoinAmount());
		this.foeAmount = numberToImages(model.getKills());
		this.gemAmount = numberToImages(model.getGemAmount());
		this.totalScore = numberToImages(model.getScore());
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		this.background.draw(0,0);
		
		if(isWin){
			this.youWin.draw(youWinPos.x, youWinPos.y);
		} else{
			this.youLose.draw(youLosePos.x, youLosePos.y);
		}
		
		this.coin.draw(coinPos.x, coinPos.y);
		this.nine.draw(foePos.x, foePos.y);
		this.gem.draw(gemPos.x, gemPos.y);
		this.cross.draw(crossPos1.x, crossPos1.y, crossScaling);
		this.cross.draw(crossPos2.x, crossPos2.y, crossScaling);
		this.cross.draw(crossPos3.x, crossPos3.y, crossScaling);
		for(int i = 0; i < coinAmount.size(); i++){
			this.coinAmount.get(i).draw(coinAmountPos.x + this.zero.getWidth()*i, coinAmountPos.y);
		}
		for(int i = 0; i < foeAmount.size(); i++){
			this.foeAmount.get(i).draw(foeAmountPos.x + this.zero.getWidth()*i, foeAmountPos.y);
		}
		for(int i = 0; i < gemAmount.size(); i++){
			this.gemAmount.get(i).draw(gemAmountPos.x + this.zero.getWidth()*i, gemAmountPos.y);
		}

		this.total.draw(totalPos.x, totalPos.y);
		for(int i = 0; i < totalScore.size(); i++){
			this.totalScore.get(i).draw(totalScorePos.x + this.zero.getWidth()*i, totalScorePos.y);
		}
		this.points.draw(this.pointsPos.x, this.pointsPos.y);
		
		if(insideQuitGame){
			this.quitGameH.draw(quitGamePos.x, quitGamePos.y);
		} else{
			this.quitGame.draw(quitGamePos.x, quitGamePos.y);
		}
		if(insideMainMenu){
			this.mainMenuH.draw(mainMenuPos.x, mainMenuPos.y);
		} else{
			this.mainMenu.draw(mainMenuPos.x, mainMenuPos.y);
		}
		if(isWin){
			if(insidePlayAgain){
				this.playAgainH.draw(playAgainPos.x, playAgainPos.y);
			} else{
				this.playAgain.draw(playAgainPos.x, playAgainPos.y);
			}
		} else{
			if(insidePlayAgain){
				this.tryAgainH.draw(playAgainPos.x, playAgainPos.y);
			} else{
				this.tryAgain.draw(playAgainPos.x, playAgainPos.y);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		insideQuitGame = checkMouse(mouseX, mouseY, quitGamePos.x, quitGamePos.y, quitGame);
		insideMainMenu = checkMouse(mouseX, mouseY, mainMenuPos.x, mainMenuPos.y, mainMenu);
		insidePlayAgain = checkMouse(mouseX, mouseY, playAgainPos.x, playAgainPos.y, playAgain);
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideQuitGame){
			gc.exit();
		}
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideMainMenu){
			sbg.enterState(GameApp.MAINMENUSTATE);
		}
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insidePlayAgain){
			Sounds.getInstance().soundtrack.stop();
			Sounds.getInstance().musicIngame.loop(1, 0.4f);
			sbg.enterState(GameApp.PLAYSTATE);
	}
		
		
	}
	
	public ArrayList<Image> numberToImages(int number){
		ArrayList<Image> images = new ArrayList<Image>();
		if(number < 100){
			images.add(this.zero);
			if(number < 10){
				images.add(this.zero);
			}
		}
		String numbers = Integer.toString(number);
		for(int i =0; i < numbers.length(); i++){
			if(numbers.charAt(i) == '0'){
				images.add(this.zero);
			} else if(numbers.charAt(i) == '1'){
				images.add(this.one);
			} else if(numbers.charAt(i) == '2'){
				images.add(this.two);
			} else if(numbers.charAt(i) == '3'){
				images.add(this.three);
			} else if(numbers.charAt(i) == '4'){
				images.add(this.four);
			} else if(numbers.charAt(i) == '5'){
				images.add(this.five);
			} else if(numbers.charAt(i) == '6'){
				images.add(this.six);
			} else if(numbers.charAt(i) == '7'){
				images.add(this.seven);
			} else if(numbers.charAt(i) == '8'){
				images.add(this.eight);
			} else if(numbers.charAt(i) == '9'){
				images.add(this.nine);
			}
		}
		return images;
		
	}
	
	public boolean checkMouse(float mouseX, float mouseY, float menuX, float menuY, Image image){
		if((mouseX >= menuX && mouseX <= menuX + image.getWidth()) &&
	            (mouseY >= menuY && mouseY <= menuY + image.getHeight())){
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
