package view;
import model.Coin;
import model.EnergyDrink;
import model.Gem;
import model.ChocolateBar;
import model.ICollectible;

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
	private ICollectible model;
	
	/** A String representing the path to the source folder */
	private final String PATH = "res/collectibles";
	
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
	 * @param ICollectible model
	 * @throws SlickException 
	 */
	public CollectibleView(ICollectible model) throws SlickException{
		this.model = model;
		if(this.model instanceof Coin){
			initCoin();
		}
		else if(this.model instanceof Gem){
			initGem();
			
		}
		else if(this.model instanceof ChocolateBar){
			initChocolateBar();
		}
		
		else if(this.model instanceof EnergyDrink){
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
		
		if(this.model instanceof Coin){
			g.drawAnimation(coinAnimation, tmp.x, tmp.y);
		}
		
		else if(this.model instanceof Gem){
			g.drawAnimation(gemAnimation, tmp.x, tmp.y);
		}
		
		else if(this.model instanceof ChocolateBar){
			g.drawAnimation(chocolateAnimation, tmp.x, tmp.y);
		}
		
		else if(this.model instanceof EnergyDrink){
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
			coinImages[i] = new Image(PATH+"/coin/coin_"+(i+1)+".png");
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
			gemImages[i] = new Image(PATH+"/gem/gem_"+i+".png");
		}
		gemAnimation = new Animation(gemImages, gemDuaration);
	}
	
	public void initChocolateBar() throws SlickException{
		chocolateImages = new Image[5];
		for(int i = 0; i<chocolateImages.length; i++){
			chocolateImages[i] = new Image(PATH+"/chocolate_bar/chocolate_"+(i+1)+".png");
		}
		chocolateAnimation = new Animation(chocolateImages,chocolateDuration);
	}
	
	public void initEnergyDrink() throws SlickException{
		energyDrinkImages = new Image[4];
		for(int i = 0; i<energyDrinkImages.length; i++){
			energyDrinkImages[i] = new Image(PATH+"/energy_drink/energydrink_"+(i+1)+".png");
		}
		energyDrinkAnimation = new Animation(energyDrinkImages, energyDrinkDuration);
	}
}
