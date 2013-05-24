package view;
import model.CoinModel;
import model.EnergyDrinkModel;
import model.GemModel;
import model.ChocolateBarModel;
import model.ICollectibleModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;

/**
 * A class that draws a collectible object
 * @author filipcarlen
 *
 */
public class CollectibleView {
	
	/** Reference to a ICollectibleModel */
	private ICollectibleModel model;
	
	/** A String representing the path to the source folder */
	private final String PATH = "res/Collectibles";
	
	/** Arrays for coinImages and gemImages. Contains Images */
	private Image[] coinImages, gemImages, energyDrinkImages, chocolateImages;
	
	/** Animation for coins and gems */
	private Animation coinAnimation, gemAnimation, energyDrinkAnimation, chocolateAnimation;
	
	/** Duration in milliseconds for a coinAnimation */
	private int coinDuration = 100;
	
	/** Duration in milliseconds for a gemAnimation */
	private int gemDuaration = 150;
	
	private int energyDrinkDuration = 150;
	
	private int chocolateDuration = 200;
	
	/**
	 * Constructor for a Collectible View 
	 * @param ICollectibleModel model
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
		else if(this.model instanceof ChocolateBarModel){
			initChocolateBar();
		}
		
		else if(this.model instanceof EnergyDrinkModel){
			initEnergyDrink();
		}
	}
	
	/**
	 * Render method for collectible items
	 * @param GameContainer container
	 * @param StateBasedGame game
	 * @param Graphics g
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g){
		Vec2 tmp = Camera.entityRender(model.getPosPixels());
		
		if(this.model instanceof CoinModel){
			g.drawAnimation(coinAnimation, tmp.x, tmp.y);
		}
		
		else if(this.model instanceof GemModel){
			g.drawAnimation(gemAnimation, tmp.x, tmp.y);
		}
		
		else if(this.model instanceof ChocolateBarModel){
			g.drawAnimation(chocolateAnimation, tmp.x, tmp.y);
		}
		
		else if(this.model instanceof EnergyDrinkModel){
			g.drawAnimation(energyDrinkAnimation, tmp.x, tmp.y);
		}
	}
	
	
	/**
	 * Initialize the animation of a coin
	 * @throws SlickException
	 */
	public void initCoin() throws SlickException{
		coinImages = new Image[8];
		for(int i = 0; i<coinImages.length; i++){
			coinImages[i] = new Image(PATH+"/Coin/coin_"+(i+1)+".png");
		}		
		coinAnimation = new Animation(coinImages, coinDuration);
	}
	
	/**
	 * Initialize the animation of a gem
	 * @throws SlickException
	 */
	public void initGem() throws SlickException{
		gemImages = new Image[8];
		for(int i = 0; i<gemImages.length; i++){
			gemImages[i] = new Image(PATH+"/Gem/gem_"+i+".png");
		}
		gemAnimation = new Animation(gemImages, gemDuaration);
	}
	
	public void initChocolateBar() throws SlickException{
		chocolateImages = new Image[5];
		for(int i = 0; i<chocolateImages.length; i++){
			chocolateImages[i] = new Image(PATH+"/ChocolateBar/chocolate_"+(i+1)+".png");
		}
		chocolateAnimation = new Animation(chocolateImages,chocolateDuration);
	}
	
	public void initEnergyDrink() throws SlickException{
		energyDrinkImages = new Image[4];
		for(int i = 0; i<energyDrinkImages.length; i++){
			energyDrinkImages[i] = new Image(PATH+"/Energydrink/energydrink_"+(i+1)+".png");
		}
		energyDrinkAnimation = new Animation(energyDrinkImages, energyDrinkDuration);
	}
}
