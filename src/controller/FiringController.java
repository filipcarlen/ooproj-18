package controller;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Navigation;
import view.BulletView;
import model.BulletModel;
import model.GunModel;

/** A controller class for a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class FiringController implements IEntityController{

	/** The model connected to this controller */
	private ArrayList<BulletModel> models ;
	/** The view connected to this controller */
	private ArrayList<BulletView> views = new ArrayList<BulletView>();;
	/** The Distance the bullet has moved */
	private float distance;
	private int id;

	
	public FiringController(GunModel gunModel, int id){
		this.id = id;
		this.models = gunModel.getBulletModels();
	}
	

	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		for(int i = 0; i < models.size(); i++){
			BulletModel currentModel = models.get(i);
			Body currentBody = currentModel.getBody();
			if(currentBody != null){
			if(currentBody.isActive()){

				this.views.add(new BulletView(currentModel));
				// To get the distance we take the position from where the bullet was fired minus the current position
				this.distance = Math.abs(currentModel.getPosMeters().x - currentModel.getFirstPos().x);
		
				if(!currentModel.isMoving()){
					if(currentModel.getNavigation() == Navigation.WEST){
						System.out.println("WEST");

						currentBody.applyForce(currentBody.getWorldVector(new Vec2(-10.0f, 0.0f)), currentBody.getPosition());
						models.get(i).setMoving(true);
					}else if(currentModel.getNavigation() == Navigation.EAST){
						System.out.println("EAST");

						currentBody.applyForce(currentBody.getWorldVector(new Vec2(10.0f, 0.0f)), currentBody.getPosition());
						models.get(i).setMoving(true);
					}
		
				} else if(currentModel.getRange() < this.distance){
						currentModel.destroyEntity();
						
				} else if(!currentModel.isAlive()){
					currentModel.destroyEntity();

				}
			} 
			}else{
				for(int j = 0; j < views.size(); j++){
					if((views.get(j)).getID() == currentModel.getID()){
						views.remove(j);
						return ;

					}
					
				}
			}
		}
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		for(int i = 0; i < views.size(); i++){
			try{
				this.views.get(i).render(container, game, g);
			} catch(NullPointerException e){}
		
		}
	}

	@Override
	public int getID() {
		return this.id;
	}
	
	
}
