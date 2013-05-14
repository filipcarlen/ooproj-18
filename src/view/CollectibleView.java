package view;
import model.CoinModel;
import model.GemModel;
import model.ICollectibleModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;

/**
 * A class that draw a collectible object
 * @author filipcarlen
 *
 */
public class CollectibleView {
	
	/** Variable containing a Shape of a Collectible Item */
	private Shape collectible;
	
	/** Variable containing a Collectible Model */
	private ICollectibleModel model;
	
	private final String PATH = "res/Collectibles";
	private Image[] coinImages, gemImages;
	private Animation coinAnimation, gemAnimation;
	private int duration = 100;
	
	/**
	 * Constructor for a Collectible View 
	 * @param model
	 * @throws SlickException 
	 */
	public CollectibleView(ICollectibleModel model) throws SlickException{
		this.model = model;
		if(this.model instanceof CoinModel){
			initCoin();
		}
		else if(this.model instanceof GemModel){
			initGem();
			
		}
	}
	/**
	 * 
	 * @return shape
	 */
	public Shape getShape(){
		return this.collectible;
	}
	
	/**
	 * 
	 * @param shape
	 */
	public void setShape(Shape shape){
		this.collectible = shape;
	}
	
	
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		Vec2 tmp = Camera.entityRender(model.getPosPixels());
		
		if(this.model instanceof CoinModel){
			g.drawAnimation(coinAnimation, tmp.x, tmp.y);
		}
		
		else if(this.model instanceof GemModel){
			g.drawAnimation(gemAnimation, tmp.x, tmp.y);
		}
	}
	
	public void initCoin() throws SlickException{
		coinImages = new Image[8];
		coinImages[0] = new Image(PATH + "/Coin/coin_1.png");
		coinImages[1] = new Image(PATH + "/Coin/coin_2.png");
		coinImages[2] = new Image(PATH + "/Coin/coin_3.png");
		coinImages[3] = new Image(PATH + "/Coin/coin_4.png");
		coinImages[4] = new Image(PATH + "/Coin/coin_5.png");
		coinImages[5] = new Image(PATH + "/Coin/coin_6.png");
		coinImages[6] = new Image(PATH + "/Coin/coin_7.png");
		coinImages[7] = new Image(PATH + "/Coin/coin_8.png");
		
		coinAnimation = new Animation(coinImages, 100);
	}
	
	public void initGem() throws SlickException{
		gemImages = new Image[8];
		gemImages[0] = new Image(PATH + "/Gem/gem_0.png");
		gemImages[1] = new Image(PATH + "/Gem/gem_1.png");
		gemImages[2] = new Image(PATH + "/Gem/gem_2.png");
		gemImages[3] = new Image(PATH + "/Gem/gem_3.png");
		gemImages[4] = new Image(PATH + "/Gem/gem_4.png");
		gemImages[5] = new Image(PATH + "/Gem/gem_5.png");
		gemImages[6] = new Image(PATH + "/Gem/gem_6.png");
		gemImages[7] = new Image(PATH + "/Gem/gem_7.png");
		gemAnimation = new Animation(gemImages, 150);
	}
}
