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
 * @author group 18
 *
 */
public class CollectibleView {
	
	/** Reference to a ICollectibleModel */
	private ICollectible model;
	
	/** A String representing the path to the source folder */
	private final String PATH = "res/collectibles";
	
	/** Animation for coins and gems */
	private Animation currentAnimation;
	
	/** Duration in milliseconds for a different animations */
	private int coinDuration = 100;
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
			currentAnimation = initCollectibleAnimation(new Image[8], coinDuration, (PATH+"/coin/coin_"));
		}
		else if(this.model instanceof Gem){
			currentAnimation = initCollectibleAnimation(new Image[8], gemDuaration, (PATH+"/gem/gem_"));
		}
		else if(this.model instanceof ChocolateBar){
			currentAnimation = initCollectibleAnimation(new Image[5], chocolateDuration, (PATH+"/chocolate_bar/chocolate_"));
		}
		
		else if(this.model instanceof EnergyDrink){
			currentAnimation = initCollectibleAnimation(new Image[4], energyDrinkDuration, (PATH+"/energy_drink/energydrink_"));
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
		
		g.drawAnimation(currentAnimation, tmp.x, tmp.y);
	}
	/**
	 * Initialize a animation of a Collectible Object
	 * @param Images images
	 * @param int duration
	 * @param String path to directory
	 * @return Animation currentAnimation
	 * @throws SlickException
	 */
	
	public Animation initCollectibleAnimation(Image[] images, int duration, String path) 
			throws SlickException{
		for(int i = 0; i<images.length; i++){
			images[i] = new Image(path+(i+1)+".png");
		}
		return new Animation(images, duration);
	}
}
