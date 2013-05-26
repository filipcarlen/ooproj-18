package controller;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import model.Bullet;
import model.Gun;

/** 
 * A controller class for a Gun
 * 
 * @author Project Group 18 (Chalmers, 2013)
 */

public class GunController implements IEntityController{

	private Gun model;
	private ArrayList<BulletController> controllers = new ArrayList<BulletController>();
	
	public GunController(Gun model){
		this.model = model;
		ArrayList<Bullet> bullets = this.model.getBulletModels();
		
		/* Adds a controller for every model in the list */
		for(int i = 0; i < bullets.size(); i++){
			this.controllers.add(new BulletController(bullets.get(i)));
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		ArrayList<Bullet> bullets = this.model.getBulletModels();
		
		/* Checks if every model in the list have a controller and if the bullet is alive.
		 * Creates a new controller if there is none, removes the bullet (body, model and controller) if the bullet is not alive
		 * and updates the controller if the bullet is alive */
		for(int i = 0; i < bullets.size(); i++){
			try{
				BulletController controller = getBulletController(bullets.get(i).getID());
				
				if(!bullets.get(i).isAlive()){
					bullets.get(i).getBody().getWorld().destroyBody(bullets.get(i).getBody());
					this.controllers.remove(controller);
					this.model.removeBullet(i);
				
				} else{
					getBulletController(bullets.get(i).getID()).update(gc, sbg, delta);
				
				}
			} catch(NullPointerException e){
				this.controllers.add(new BulletController(bullets.get(i)));
			}
		}	
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		for(int i = 0; i < this.controllers.size(); i++){
			this.controllers.get(i).render(gc, sbg, g);
		}
	}
	
	/**
	 * @param ID of the model
	 * @return controller that has the same ID as the model
	 */
	public BulletController getBulletController(int ID){
		for(int i = 0; i < this.controllers.size(); i++){
			if(this.controllers.get(i).getID() == ID){
				return this.controllers.get(i);
			}
		}
		return null;

	}
	
	@Override
	public int getID() {
		return this.model.getID();
	}	
}
