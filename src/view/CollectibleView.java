package view;

import model.CollectibleModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A class that draw a collectible object
 * @author filipcarlen
 *
 */
public class CollectibleView {
	
	/** Variable containing a Shape of a Collectible Item */
	private Shape collectible;
	
	/** Variable containing a Collectible Model */
	private CollectibleModel model;
	
	/**
	 * Constructor for a Collectible View 
	 * @param model
	 */
	public CollectibleView(CollectibleModel model){
		this.model = model;
		
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
		Vec2 tmp = model.getPosPixels();
		g.drawOval(tmp.x, tmp.y, model.getRadius(), model.getRadius());
	}
}
