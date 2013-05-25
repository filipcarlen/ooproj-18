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
	private Image[] coinImages = new Image[8];
	private Image[] gemImages = new Image[8];
	private Image[] energyDrinkImages  = new Image[4];
	private Image[] chocolateImages = new Image[5];
	
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
			coinAnimation = initCollectibleAnimation(coinImages, coinDuration, (PATH+"/coin/coin_"));
			//initCoin();
		}
		else if(this.model instanceof Gem){
			gemAnimation = initCollectibleAnimation(gemImages, gemDuaration, (PATH+"/gem/gem_"));
			//initGem();
			
		}
		else if(this.model instanceof ChocolateBar){
			chocolateAnimation = initCollectibleAnimation(chocolateImages, chocolateDuration, (PATH+"/chocolate_bar/chocolate_"));
			//initChocolateBar();
		}
		
		else if(this.model instanceof EnergyDrink){
			energyDrinkAnimation = initCollectibleAnimation(energyDrinkImages, energyDrinkDuration, (PATH+"/energy_drink/energydrink_"));
			//initEnergyDrink();
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
	
	public Animation initCollectibleAnimation(Image[] images, int duration, String path) 
			throws SlickException{
		for(int i = 0; i<images.length; i++){
			images[i] = new Image(path+(i+1)+".png");
		}
		return new Animation(images, duration);
	}
}
