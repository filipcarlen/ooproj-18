package controller;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import com.sun.tools.javac.util.List;

import utils.Navigation;
import view.BulletView;
import model.BulletModel;
import model.GunModel;

/** A controller class for a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class BulletController implements IEntityController{

	/** The model connected to this controller */
	private List<BulletModel> models ;
	/** The view connected to this controller */
	private ArrayList<BulletView> views = new ArrayList<BulletView>();;
	/** The Distance the bullet has moved */
	private Vec2 distance;
	/** Tells us if the bullet is moving or not */
	private boolean isMoving;

	
	public BulletController(List<BulletModel> models){
		this.models = models;
		//for(int i = 0; i < models.length(); i++){
		//	this.views.add(new BulletView(this.models.get(i)));
		//}
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		for(int i = 0; i < models.length(); i++){
			if(models.get(i).getBody().isActive()){
				// To get the distance we take the position from where the bullet was fired minus the current position
				this.distance = Vec2.abs(this.models.get(i).getPosPixels().sub(this.models.get(i).getFirstPos()));
		
				if(!models.get(i).isMoving()){
					if(this.models.get(i).getNavigation() == Navigation.WEST){
						this.models.get(i).getBody().applyForce(this.models.get(i).getBody().getWorldVector(new Vec2(-10.0f, 0.0f)), this.models.get(i).getBody().getPosition());
						models.get(i).setMoving(true);
					}else if(this.models.get(i).getNavigation() == Navigation.EAST){
						this.models.get(i).getBody().applyForce(this.models.get(i).getBody().getWorldVector(new Vec2(10.0f, 0.0f)), this.models.get(i).getBody().getPosition());
						models.get(i).setMoving(true);
					}
		
				} else{
					if(this.models.get(i).getRange() < distance.length()){
						this.models.get(i).destroyEntity();
						models.get(i).setMoving(false);
						
						models.get(i).getBody().setActive(false);
					}
				}
			}
		}
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		for(int i = 0; i < views.size(); i++){
			this.views.get(i).render(container, game, g);
		}
	}

	@Override
	public int getID() {
		return -1;
	}
	
	
}
