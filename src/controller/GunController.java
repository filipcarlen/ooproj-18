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
		for(int i = 0; i < this.controllers.size(); i++){
			this.controllers.get(i).update(gc, sbg, delta);
		}	
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		for(int i = 0; i < this.controllers.size(); i++){
			this.controllers.get(i).render(gc, sbg, g);
		}
	}

	@Override
	public int getID() {
		return this.model.getID();
	}	
}
