package controller;

import model.AbstractWeaponModel;
import model.SwordModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class SwordController implements IEntityController{

	private SwordModel model;
	private boolean forward;
	// distance between the character handling the weapon and the weapon
	private int distanceCount = 20;
	
	
	public SwordController(SwordModel model){
		this.model = model;
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		// det h�r �r antagligen inte ett bra s�tt att g�ra det p�...
		// Jag vill ge Swords Body samma position som den som h�ller i sv�rdet i varje update.
		// vet inte ens om man kan g�ra s�h�r. �r positionen p� BodyDef samma som positionen p� Body?
		model.init(AbstractWeaponModel.getFighterBody(model.getWorld(), model.getPosPixels()).getPosition());
		if(model.isFighting()){
			
			if(forward){
				model.getBody().applyForce(model.getBody().getWorldVector(new Vec2(10.0f, 0.0f)), model.getBody().getPosition());
				
				distanceCount --;
				if(distanceCount <= 0){
					forward = false;
				}
				
			} else {
				
				
				distanceCount ++;
				if(distanceCount >= 20){
					model.setFighting(false);
				}
			}
			
		}
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		
		
	}

	@Override
	public int getID() {
		return model.getID();
	}

}
