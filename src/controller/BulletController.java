package controller;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
=======
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class BulletController implements IEntityController{

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
				
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
	}
>>>>>>> A bunch of small changes. Nothing praticular

import view.BulletView;
import model.BulletModel;

public class BulletController implements IEntityController{

	private BulletModel model;
	private BulletView view;
	
	public BulletController(BulletModel model){
		this.model = model;
		this.view = new BulletView(model);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
	}
	
	
}
