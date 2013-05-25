package controller.states;

import java.util.ArrayList;
import java.util.List;

import model.Hero;

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

public class GameOverState extends BasicGameState{

	private HighscoreManager highscoreManager;
	private int stateID;
	private float spacing;
	private float crossScaling = 0.8f;
	
	private boolean isWin;
	
	private List<Image> coinAmount = new ArrayList<Image>(); 
	private List<Image> foeAmount = new ArrayList<Image>(); 
	private List<Image> gemAmount = new ArrayList<Image>(); 
	private List<Image> totalScore = new ArrayList<Image>(); 
	
	private Vec2 coinAmountPos, foeAmountPos, gemAmountPos, totalScorePos;
	
	private final String IMAGE_PATH = "res/game_over_menu/";
	private final String DISCO_BALL_PATH = IMAGE_PATH + "disco_ball/";
	private final String DANCING_HERO_PATH = "res/characters/bluepants/";
	private final String COIN_PATH = "res/collectibles/coin/";
	private final String FOE_PATH = "res/foe/moving_foe/foe_right/";
	private final String GEM_PATH = "res/collectibles/gem/";
	
	private Animation coin, foe, gem;
	private Animation dancingHero, discoBall;
	
	private Vec2 coinPos, foePos, gemPos; 
	private Vec2 dancingHeroPos, discoBallPos;
	
	private Image grave;
	private Image danceFloor;
	private Vec2 gravePos, danceFloorPos;
	
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
		initDancingHero();
		initCoin();
		initFoe();
		initGem();
		
		this.background = new Image("res/background.png");
		
		this.danceFloor = new Image(IMAGE_PATH + "danceFloor.png");
		this.grave = new Image(IMAGE_PATH + "grave.png");
		
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
		
		this.youWinPos = new Vec2(screenWidth/2 - this.youWin.getWidth()/2, this.spacing);
		this.youLosePos = new Vec2(screenWidth/2 - this.youLose.getWidth()/2, this.spacing);
		
		float tmpSpacing = (float)(screenWidth - this.quitGame.getWidth()*3)/4f;
		this.quitGamePos = new Vec2(tmpSpacing, screenHeight - this.quitGame.getHeight() - this.spacing);
		this.mainMenuPos = new Vec2(tmpSpacing*2 + this.quitGame.getWidth(), screenHeight - this.quitGame.getHeight() - this.spacing);
		this.playAgainPos = new Vec2(tmpSpacing*3 + this.quitGame.getWidth()*2, screenHeight - this.quitGame.getHeight() - this.spacing);
		
		float spaceY = (screenHeight - this.youWinPos.y - this.youWin.getHeight() - (screenHeight - this.mainMenuPos.y) 
				- (this.zero.getHeight()*4 + this.spacing*4))/2;

		this.coinAmountPos = new Vec2(this.youLosePos.x, this.youLosePos.y + this.youLose.getHeight() + spaceY);
		this.foeAmountPos = new Vec2(this.youLosePos.x, this.coinAmountPos.y + this.zero.getHeight() + this.spacing);
		this.gemAmountPos = new Vec2(this.youLosePos.x, this.foeAmountPos.y + this.zero.getHeight() + this.spacing);

		this.danceFloorPos = new Vec2(this.youLosePos.x + this.youLose.getWidth() - this.zero.getWidth()*3, screenHeight/2 - this.danceFloor.getHeight()/2 - this.spacing);
		this.discoBallPos = new Vec2(this.danceFloorPos.x + this.danceFloor.getWidth()/2 - this.discoBall.getWidth()/2, this.danceFloorPos.y - this.spacing);
		this.dancingHeroPos = new Vec2(this.discoBallPos.x + this.discoBall.getWidth()/2 - this.dancingHero.getWidth()/2, this.discoBallPos.y + this.discoBall.getHeight()*2);
		
		this.gravePos = new Vec2(screenWidth/2 + this.spacing, screenHeight/2 - this.spacing*3);
		
		this.crossPosses[0] = new Vec2(this.coinAmountPos.x - this.cross.getWidth()*this.crossScaling - this.spacing*2, this.coinAmountPos.y + this.zero.getHeight()/2 - this.cross.getHeight()*this.crossScaling/2);
		this.crossPosses[1] = new Vec2(this.foeAmountPos.x - this.cross.getWidth()*this.crossScaling - this.spacing*2, this.foeAmountPos.y + this.zero.getHeight()/2 - this.cross.getHeight()*this.crossScaling/2);
		this.crossPosses[2] = new Vec2(this.gemAmountPos.x - this.cross.getWidth()*this.crossScaling - this.spacing*2, this.gemAmountPos.y + this.zero.getHeight()/2 - this.cross.getHeight()*this.crossScaling/2);
		
		this.coinPos = new Vec2(this.crossPosses[0].x - this.coin.getWidth() - this.spacing*3, this.crossPosses[0].y + this.cross.getHeight()*this.crossScaling/2 - this.coin.getHeight()/2);
		this.foePos = new Vec2(this.crossPosses[1].x - this.nine.getWidth() - this.spacing*3, this.crossPosses[1].y + this.cross.getHeight()*this.crossScaling/2 - this.nine.getHeight()/2);
		this.gemPos = new Vec2(this.crossPosses[2].x - this.gem.getWidth() - this.spacing*3, this.crossPosses[2].y + this.cross.getHeight()*this.crossScaling/2 - this.gem.getHeight()/2);
		
		this.totalScorePos = new Vec2(this.youLosePos.x, this.gemAmountPos.y + this.zero.getHeight() + this.spacing*2);
		this.totalPos = new Vec2(this.totalScorePos.x - this.total.getWidth() - this.spacing, this.totalScorePos.y + this.zero.getHeight()/2 - this.total.getHeight()/2);
		this.pointsPos = new Vec2(this.totalScorePos.x + this.zero.getWidth()*3 + this.spacing, this.totalPos.y + this.total.getHeight()/2 - this.points.getHeight()/2);
	}
	
	/**
	 * Initialize the animation of a coin
	 */
	public void initCoin(){
		Image[] coinImages = new Image[8];
		try{
			for(int i = 0; i < coinImages.length; i++){
				coinImages[i] = new Image(COIN_PATH + "coin_" + (i+1) + ".png");
			}
		} catch(SlickException e){}
		this.coin = new Animation(coinImages, 100);
	}
	
	/**
	 * Initialize the animation of a gem
	 */
	public void initGem(){
		Image[] gemImages = new Image[8];
		try{
			for(int i = 0; i < gemImages.length; i++){
				gemImages[i] = new Image(GEM_PATH + "gem_" + i + ".png");
			}
		} catch(SlickException e){}
		this.gem = new Animation(gemImages, 150);
	}
	
	/**
	 * Initialize the animation of a gem
	 */
	public void initFoe(){
		Image[] foeImages = new Image[12];
		try{
			for(int i = 0; i < foeImages.length; i++){
				foeImages[i] = new Image(FOE_PATH + "foe_right_" + (i+1) + ".png");
			}
		} catch(SlickException e){}
		this.foe = new Animation(foeImages, 100);
	}
	
	/**
	 * Initialize the animation of a disco ball
	 */
	public void initDiscoBall(){
		Image[] discoBallImages = new Image[12];
		try{
			for(int i = 0; i < discoBallImages.length; i++){
				discoBallImages[i] = new Image(DISCO_BALL_PATH + "discoBall" + (i+1) + ".png");
			}
		} catch(SlickException e){}
		
		this.discoBall = new Animation(discoBallImages, 150);
	}
	
	/**
	 * Initialize the animation of a dancing hero
	 */
	public void initDancingHero(){
		Image[] dancingHeroImages = new Image[6];
		try{
			for(int i = 0; i < dancingHeroImages.length; i++){
				dancingHeroImages[i] = new Image(DANCING_HERO_PATH + "run_right_" + (i+1) + ".png");
			}
		} catch(SlickException e){}
		
		this.dancingHero = new Animation(dancingHeroImages, 300);
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Hero model = ((PlayState)sbg.getState(GameApp.PLAY_STATE)).getHeroModel();
		this.isWin = !model.isDead();
		
		if(isWin){
			Sounds.getInstance().playMusic(SoundType.YOU_WIN_MUSIC);
		} else{
			Sounds.getInstance().playMusic(SoundType.YOU_LOSE_MUSIC);
		}
		initPositions(gc, sbg);
		
		int coins = model.getCoinAmount();
		int foes = model.getKills();
		int gems = model.getGemAmount();
		int score = model.getScore();
		String playerName = model.getPlayerName();
		
		this.highscoreManager = new HighscoreManager();
		this.highscoreManager.addScore(playerName, score, coins, gems, foes);
		
		this.coinAmount = numberToImages(coins);
		this.foeAmount = numberToImages(foes);
		this.gemAmount = numberToImages(gems);
		this.totalScore = numberToImages(score);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		
		if(isWin){
			this.youWin.draw(this.youWinPos.x, this.youWinPos.y);
			this.danceFloor.draw(this.danceFloorPos.x, this.danceFloorPos.y);
			this.discoBall.draw(this.discoBallPos.x, this.discoBallPos.y);
			this.dancingHero.draw(this.dancingHeroPos.x, this.dancingHeroPos.y);
		} else{
			this.youLose.draw(this.youLosePos.x, this.youLosePos.y);
			this.grave.draw(this.gravePos.x, this.gravePos.y);
		}
		
		this.coin.draw(this.coinPos.x, this.coinPos.y);
		this.foe.draw(this.foePos.x, this.foePos.y);
		this.gem.draw(this.gemPos.x, this.gemPos.y);
		
		for(int i = 0; i < this.crossPosses.length; i++){
			this.cross.draw(this.crossPosses[i].x, this.crossPosses[i].y, this.crossScaling);
		}
		
		for(int i = 0; i < this.coinAmount.size(); i++){
			this.coinAmount.get(i).draw(this.coinAmountPos.x + this.zero.getWidth()*i, this.coinAmountPos.y);
		}
		for(int i = 0; i < this.foeAmount.size(); i++){
			this.foeAmount.get(i).draw(this.foeAmountPos.x + this.zero.getWidth()*i, this.foeAmountPos.y);
		}
		for(int i = 0; i < this.gemAmount.size(); i++){
			this.gemAmount.get(i).draw(this.gemAmountPos.x + this.zero.getWidth()*i, this.gemAmountPos.y);
		}

		this.total.draw(this.totalPos.x, this.totalPos.y);
		for(int i = 0; i < this.totalScore.size(); i++){
			this.totalScore.get(i).draw(this.totalScorePos.x + this.zero.getWidth()*i, this.totalScorePos.y);
		}
		this.points.draw(this.pointsPos.x, this.pointsPos.y);
		
		if(this.insideQuitGame){
			this.quitGameH.draw(this.quitGamePos.x, this.quitGamePos.y);
		} else{
			this.quitGame.draw(this.quitGamePos.x, this.quitGamePos.y);
		}
		
		if(this.insideMainMenu){
			this.mainMenuH.draw(this.mainMenuPos.x, this.mainMenuPos.y);
		} else{
			this.mainMenu.draw(this.mainMenuPos.x, this.mainMenuPos.y);
		}
		
		if(this.isWin){
			if(this.insidePlayAgain){
				this.playAgainH.draw(this.playAgainPos.x, this.playAgainPos.y);
			} else{
				this.playAgain.draw(this.playAgainPos.x, this.playAgainPos.y);
			}
		} else{
			if(this.insidePlayAgain){
				this.tryAgainH.draw(this.playAgainPos.x, this.playAgainPos.y);
			} else{
				this.tryAgain.draw(this.playAgainPos.x, this.playAgainPos.y);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		Input input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		boolean mouseClicked = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		
		this.insideQuitGame = checkMouse(mouseX, mouseY, this.quitGamePos, this.quitGame);
		this.insideMainMenu = checkMouse(mouseX, mouseY, this.mainMenuPos, this.mainMenu);
		this.insidePlayAgain = checkMouse(mouseX, mouseY, this.playAgainPos, this.playAgain);
		
		if(mouseClicked && this.insideQuitGame){
			gc.exit();
		}
		
		if(mouseClicked && this.insideMainMenu){
			sbg.enterState(GameApp.MAIN_MENU_STATE);
		}
		
		if(mouseClicked && this.insidePlayAgain){
			sbg.enterState(GameApp.PLAY_STATE);
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
}
