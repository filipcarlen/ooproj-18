package controller.states;

import java.util.ArrayList;
import java.util.List;

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

import utils.SoundType;
import utils.Sounds;

import controller.HighscoreManager;
import controller.IPlayStateController;

public class GameOverState extends BasicGameState{

	private HighscoreManager highscoreManager;
	private int stateID;
	private IPlayStateController playState;
	private float spacing;
	private float crossScaling = 0.8f;
	
	private boolean isWin;
	
	private List<Image> coinAmount = new ArrayList<Image>(); 
	private List<Image> foeAmount = new ArrayList<Image>(); 
	private List<Image> gemAmount = new ArrayList<Image>(); 
	private List<Image> totalScore = new ArrayList<Image>(); 
	
	private Vec2 coinAmountPos, foeAmountPos, gemAmountPos, totalScorePos;
	
	private final String IMAGE_PATH = "res/GameOver/";
	private final String DISCO_BALL_PATH = "res/GameOver/disco_ball/";
	private final String COIN_PATH = "res/Collectibles/Coin/";
	private final String FOE_PATH = "res/foe/moving_foe/foe_right/";
	private final String GEM_PATH = "res/Collectibles/Gem/";
	
	private Animation coin, foe, gem;
	private Animation dancingHero, sadHero, discoBall;
	
	private Vec2 coinPos, foePos, gemPos; 
	private Vec2 dancingHeroPos, sadHeroPos, discoBallPos;
	
	private Image failSign;
	private Image danceFloor;
	private Vec2 failSignPos, danceFloorPos;
	
	private Image zero, one, two, three, four, five, six, seven, eight, nine, cross;
	private Image total, points;

	private Image playAgain, playAgainH;
	private Image tryAgain, tryAgainH;
	
	private Image mainMenu, mainMenuH;
	
	private Image quitGame, quitGameH;
	
	private Image youWin, youLose;
	
	private Image background;	
	
	private Vec2 youWinPos, youLosePos;
	
	private Vec2[] crossPosses = new Vec2[3];
	private Vec2 totalPos, pointsPos;
	private Vec2 playAgainPos, mainMenuPos, quitGamePos;
	
	private boolean insideQuitGame = false;
	private boolean insideMainMenu = false;
	private boolean insidePlayAgain = false;


	public GameOverState(int stateID){
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {

		this.spacing = 20;
		
		initDiscoBall();
		initCoin();
		initFoe();
		initGem();
		
		this.background = new Image("res/background.png");
		
		this.danceFloor = new Image(IMAGE_PATH + "danceFloor.png");
		
		this.zero = new Image(IMAGE_PATH + "zero.png");
		this.one = new Image(IMAGE_PATH + "one.png");
		this.two = new Image(IMAGE_PATH + "two.png");
		this.three = new Image(IMAGE_PATH + "three.png");
		this.four = new Image(IMAGE_PATH + "four.png");
		this.five = new Image(IMAGE_PATH + "five.png");
		this.six = new Image(IMAGE_PATH + "six.png");
		this.seven = new Image(IMAGE_PATH + "seven.png");
		this.eight = new Image(IMAGE_PATH + "eight.png");
		this.nine = new Image(IMAGE_PATH + "nine.png");
		this.cross = new Image(IMAGE_PATH + "cross.png");
		
		this.total = new Image(IMAGE_PATH + "total.png");
		this.points = new Image(IMAGE_PATH + "points.png");
		
		this.playAgain = new Image(IMAGE_PATH + "playAgain.png");
		this.playAgainH = new Image(IMAGE_PATH + "playAgainH.png");
		this.tryAgain = new Image(IMAGE_PATH + "tryAgain.png");
		this.tryAgainH = new Image(IMAGE_PATH + "tryAgainH.png");
		
		this.mainMenu = new Image(IMAGE_PATH + "mainMenu.png");
		this.mainMenuH = new Image(IMAGE_PATH + "mainMenuH.png");
		
		this.quitGame = new Image(IMAGE_PATH + "quitGame.png");
		this.quitGameH = new Image(IMAGE_PATH + "quitGameH.png");
		
		this.youWin = new Image(IMAGE_PATH + "youWin.png");
		this.youLose = new Image(IMAGE_PATH + "youLose.png");
		
		
	}
	
	public void initPositions(GameContainer gc, StateBasedGame sbg){
		
		float screenWidth = gc.getWidth();
		float screenHeight = gc.getHeight();
		
		this.youWinPos = new Vec2(screenWidth/2 - youWin.getWidth()/2, this.spacing);
		this.youLosePos = new Vec2(screenWidth/2 - youLose.getWidth()/2, this.spacing);
		
		float tmpSpacing = (float)(screenWidth - quitGame.getWidth()*3)/4f;
		this.quitGamePos = new Vec2(tmpSpacing, screenHeight - quitGame.getHeight() - this.spacing);
		this.mainMenuPos = new Vec2(tmpSpacing*2 + quitGame.getWidth(), screenHeight - quitGame.getHeight() - this.spacing);
		this.playAgainPos = new Vec2(tmpSpacing*3 + quitGame.getWidth()*2, screenHeight - quitGame.getHeight() - this.spacing);
		
		float spaceY = (screenHeight - youWinPos.y - youWin.getHeight() - (screenHeight - this.mainMenuPos.y) 
				- (this.zero.getHeight()*4 + this.spacing*4))/2;

		this.coinAmountPos = new Vec2(this.youLosePos.x, this.youLosePos.y + youLose.getHeight() + spaceY);
		this.foeAmountPos = new Vec2(this.youLosePos.x, coinAmountPos.y + this.zero.getHeight() + this.spacing);
		this.gemAmountPos = new Vec2(this.youLosePos.x, foeAmountPos.y + this.zero.getHeight() + this.spacing);

		this.danceFloorPos = new Vec2(this.youLosePos.x + youLose.getWidth() - this.zero.getWidth()*3, screenHeight/2 - danceFloor.getHeight()/2 - this.spacing);
		this.discoBallPos = new Vec2(this.danceFloorPos.x + this.danceFloor.getWidth()/2 - this.discoBall.getWidth()/2, this.danceFloorPos.y - this.spacing);
		
		this.crossPosses[0] = new Vec2(this.coinAmountPos.x - this.cross.getWidth()*this.crossScaling - this.spacing*2, this.coinAmountPos.y + this.zero.getHeight()/2 - this.cross.getHeight()*this.crossScaling/2);
		this.crossPosses[1] = new Vec2(this.foeAmountPos.x - this.cross.getWidth()*this.crossScaling - this.spacing*2, this.foeAmountPos.y + this.zero.getHeight()/2 - this.cross.getHeight()*this.crossScaling/2);
		this.crossPosses[2] = new Vec2(this.gemAmountPos.x - this.cross.getWidth()*this.crossScaling - this.spacing*2, this.gemAmountPos.y + this.zero.getHeight()/2 - this.cross.getHeight()*this.crossScaling/2);
		
		this.coinPos = new Vec2(crossPosses[0].x - coin.getWidth() - this.spacing*3, crossPosses[0].y + cross.getHeight()*this.crossScaling/2 - coin.getHeight()/2);
		this.foePos = new Vec2(crossPosses[1].x - nine.getWidth() - this.spacing*3, crossPosses[1].y + cross.getHeight()*this.crossScaling/2 - nine.getHeight()/2);
		this.gemPos = new Vec2(crossPosses[2].x - gem.getWidth() - this.spacing*3, crossPosses[2].y + cross.getHeight()*this.crossScaling/2 - gem.getHeight()/2);
		
		this.totalScorePos = new Vec2(this.youLosePos.x, this.gemAmountPos.y + this.zero.getHeight() + this.spacing*2);
		this.totalPos = new Vec2(this.totalScorePos.x - this.total.getWidth() - this.spacing, this.totalScorePos.y + this.zero.getHeight()/2 - this.total.getHeight()/2);
		this.pointsPos = new Vec2(this.totalScorePos.x + this.zero.getWidth()*3 + this.spacing, this.totalPos.y + this.total.getHeight()/2 - this.points.getHeight()/2);
	}
	
	public void initCoin(){
		Image[] coinImages = new Image[8];
		try{
			for(int i = 0; i < coinImages.length; i++){
				coinImages[i] = new Image(COIN_PATH + "coin_" + (i+1) + ".png");
			}
		} catch(SlickException e){}
		coin = new Animation(coinImages, 100);
	}
	
	/**
	 * Initialize the animation of a gem
	 * @throws SlickException
	 */
	public void initGem(){
		Image[] gemImages = new Image[8];
		try{
			for(int i = 0; i < gemImages.length; i++){
				gemImages[i] = new Image(GEM_PATH + "gem_" + i + ".png");
			}
		} catch(SlickException e){}
		gem = new Animation(gemImages, 150);
	}
	
	/**
	 * Initialize the animation of a gem
	 * @throws SlickException
	 */
	public void initFoe(){
		Image[] foeImages = new Image[12];
		try{
			for(int i = 0; i < foeImages.length; i++){
				foeImages[i] = new Image(FOE_PATH + "foe_right_" + (i+1) + ".png");
			}
		} catch(SlickException e){}
		foe = new Animation(foeImages, 100);
	}
	
	/**
	 * Initialize the animation of a disco ball
	 * @throws SlickException
	 */
	public void initDiscoBall(){
		Image[] discoBallImages = new Image[12];
		try{
			for(int i = 0; i < discoBallImages.length; i++){
				discoBallImages[i] = new Image(DISCO_BALL_PATH + "discoBall" + (i+1) + ".png");
			}
		} catch(SlickException e){}
		
		discoBall = new Animation(discoBallImages, 150);
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Sounds.getInstance().stopMusic();
		Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
		initPositions(gc, sbg);
		
		HeroModel model = this.playState.getHeroModel();
		this.isWin = !model.isDead();

		int coins = model.getCoinAmount();
		int foes = model.getKills();
		int gems = model.getGemAmount();
		int score = model.getScore();
		String playerName = "Player";
		
		this.highscoreManager = new HighscoreManager();
		highscoreManager.addScore(playerName, score, coins, gems, foes);
		
		this.coinAmount = numberToImages(coins);
		this.foeAmount = numberToImages(foes);
		this.gemAmount = numberToImages(gems);
		this.totalScore = numberToImages(score);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		
		if(!isWin){
			this.youWin.draw(youWinPos.x, youWinPos.y);
			this.danceFloor.draw(danceFloorPos.x, danceFloorPos.y);
			this.discoBall.draw(discoBallPos.x, discoBallPos.y);
		} else{
			this.youLose.draw(youLosePos.x, youLosePos.y);
		}
		
		this.coin.draw(coinPos.x, coinPos.y);
		this.foe.draw(foePos.x, foePos.y);
		this.gem.draw(gemPos.x, gemPos.y);
		
		for(int i = 0; i < crossPosses.length; i++){
			this.cross.draw(crossPosses[i].x, crossPosses[i].y, crossScaling);
		}
		
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
		
		insideQuitGame = checkMouse(mouseX, mouseY, quitGamePos, quitGame);
		insideMainMenu = checkMouse(mouseX, mouseY, mainMenuPos, mainMenu);
		insidePlayAgain = checkMouse(mouseX, mouseY, playAgainPos, playAgain);
		
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideQuitGame){
			gc.exit();
		}
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insideMainMenu){
			sbg.enterState(GameApp.MAINMENUSTATE);
		}
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && insidePlayAgain){
			Sounds.getInstance().stopMusic();
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
