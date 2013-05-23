package controller;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import model.BulletModel;
import model.GunModel;

/** A controller class for a Bullet
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class GunController implements IEntityController{

	private GunModel model;
	private ArrayList<BulletController> controllers = new ArrayList<BulletController>();
	
	public GunController(GunModel model){
		ArrayList<BulletModel> bullets = model.getBulletModels();
		for(int i = 0; i < bullets.size(); i++){
			this.controllers.add(new BulletController(bullets.get(i)));
		}
		this.model = model;
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		ArrayList<BulletModel> bullets = this.model.getBulletModels();
		for(int i = 0; i < bullets.size(); i++){
			try{
				BulletController controller = getBulletController(bullets.get(i).getID());
				
				if(!bullets.get(i).isAlive()){
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
