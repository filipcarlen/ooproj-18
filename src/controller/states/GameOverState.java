package controller.states;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState{

	private int stateID;
	
	private Animation enemy;
	private Animation coin;
	private Animation gem;
	private Animation dancingHero;
	private Animation sadHero;
	private Animation discoBall;
	
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
	
	private Vec2 totalPos = new Vec2(0,0);
	private Vec2 pointsPos = new Vec2(0,0);
	private Vec2 playAgainPos = new Vec2(0,0); 
	private Vec2 mainMenuPos = new Vec2(0,0);
	private Vec2 quitGamePos = new Vec2(0,0);
	private Vec2 youWinPos;
	private Vec2 youLosePos;


	public GameOverState(int stateID){
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		float screenWidth = arg1.getContainer().getWidth();
		float screenHeight = arg1.getContainer().getHeight();
		
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
		
		this.youWinPos = new Vec2(screenWidth/2 - youWin.getWidth()/2, 20);
		this.youLosePos = new Vec2(screenWidth/2 - youLose.getWidth()/2, 20);
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		this.background.draw(0,0);
		this.youWin.draw(youWinPos.x, youWinPos.y);
		
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
