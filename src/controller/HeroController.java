package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.GunModel;
import model.HeroModel;
import model.SwordModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import utils.Controls;
import utils.Navigation;
import utils.SoundType;
import utils.Sounds;
import utils.WeaponType;
import view.HeroView;

/**
 * Version: 1.0 A controller for the Hero. This class will interact with the
 * View of the hero and the Model, to make the character move and fight.
 * 
 * @author Project Group 18 (Chalmers, 2213)
 * 
 */

public class HeroController implements IEntityController, ActionListener {
	/* A Boolean to check if the Character is in the air or not */
	private boolean jump;
	
	/*
	 * This is a count how many times i will apply force in the y axis, this is
	 * use to simulate a real jump
	 */
	private int jumpCount = 26;

	/*
	 * This is a integer that will be set if somebody calls fight, and will
	 * prevent that the standing animation will be active
	 */
	private Timer fightTimer;
	
	private boolean fight = false;
	
	private boolean move = false;

	/* This is the Model */
	private HeroModel model;

	/* This is the View */
	private HeroView view;
	
	/* This is the controller that controls the weapon*/
	private IEntityController controller;
	
	/* This timer controls how long time the hurt animations should continue*/
	private Timer hurtTimer = new Timer(220, this);

	public HeroController(HeroModel hm) {
		model = hm;
		view = new HeroView(hm, model.getWeaponType());
		fightTimer= new Timer(view.getAttackAnimation().getFrameCount()* 
				view.getAttackAnimation().getDuration(0),this);
		if(!model.isBodyCreated()){
			/* Sets the dimension of the hero after the slimmest animation standing*/
			model.setDimension(view.getWidth(), view.getHeight());
		}
		if(model.getWeaponType() == WeaponType.gun)
			controller = new GunController((GunModel) model.getWeapon());
		else if(model.getWeaponType() == WeaponType.sword){
			controller = new SwordController((SwordModel) model.getWeapon());
		}
		if (!Controls.getInstance().isControlsSet())
			/* Sets the Controls to the default options */
			Controls.getInstance().setDeafaultControls();
		/* Otherwise the controls already is set */
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		/* Tells the Control which key i'm pressing */
		Controls.getInstance().updateInput(gc.getInput());
		
		move = false;
		
		/* Get the vector that is used to apply force to the character */
		Vec2 heroVec = model.getBody().getWorldVector(new Vec2(-1.2f, 0.0f));
		
		/*
		 * Sets the linearDamping so that the body dosen't continue to go to the
		 * left, right or up
		 */
		model.getBody().setLinearDamping(1.5f);
	
		if(model.isHurted() && !hurtTimer.isRunning()){
			view.setAnimation(model.isHurtedFront()? HeroView.Movement.hurt: HeroView.Movement.hurtback, model.getDirection());
			hurtTimer.start();
		}
		

		
		/* This if statements handels the inputs from the keyboard */
		if (Controls.getInstance().check("left") && !Controls.getInstance().check("right")) {
			model.setDirection(Navigation.WEST);
			move = true;
			model.getBody().applyForce(heroVec.mul(-1), model.getBody().getPosition());
			/*
			 * If the character isn't jumping this will start the moving to the
			 * left animation
			 */
			if (!jump && !model.isFalling() && !model.isHurted()&& !fight)
				view.setAnimation(HeroView.Movement.run, model.getDirection());
		}
		if (Controls.getInstance().check("right") && !Controls.getInstance().check("left")) {
			model.setDirection(Navigation.EAST);
			move = true;
			model.getBody().applyForce(heroVec, model.getBody().getPosition());
			/*
			 * If the character isn't jumping this will start the moving to the
			 * right animation
			 */
			if (!jump && !model.isFalling() && !model.isHurted()&& !fight)
				view.setAnimation(HeroView.Movement.run, model.getDirection());
		}
		if (Controls.getInstance().check("jump")) {
			move = true;
			/* This will start the jump animation */
			view.setAnimation(HeroView.Movement.jump, model.getDirection());
			/* This check that we haven't jumped two times */
			if (model.getDoubleJump() < 2){
				jump = true;
				jumpCount = 26- (model.getDoubleJump()*15);
			}
			model.incrementJumps();
		}
		
		/*
		 * Tells the view to apply the animation for standing, if the character
		 * isn't jumping or fighting
		 */
		if (!jump && !model.isFalling() && !model.isHurted()&& !fight && !move && !model.isInAir()){
			view.setAnimation(HeroView.Movement.stand, model.getDirection());
			model.getBody().setLinearDamping(5f);
		}
		
		if (Controls.getInstance().check("fight")) {
			/*
			 * If you push the jump button it will start the animation and call
			 * the attack method with it's weapon
			 */
			if(model.attack()){
				if(model.getWeaponType() == WeaponType.gun)
					Sounds.getInstance().playSound(SoundType.GUN);
				else if(model.getWeaponType() == WeaponType.sword)
					Sounds.getInstance().playSound(SoundType.SWORD);
				fight = true;
				fightTimer.start();
				view.setAttackAnimation(model.getDirection());
			}
	
		}
		
		/*
		 * This if Statement decrease the jumpcount so that it will reach 0 and
		 * stop applying force in the y axis
		 */
		if (jump) {
			model.getBody().applyForce(
					model.getBody().getLocalVector(new Vec2(.0f, 10f)),
					model.getBody().getPosition());
			jumpCount -= 1;
			if (jumpCount <= 0) {
				jump = false;
			}
		}
		if(model.getBody().m_linearVelocity.y > 0.001f ){
			view.setAnimation(HeroView.Movement.fall, model.getDirection());
			model.falling();
		}
		controller.update(gc, sbg, delta);
		
		if(model.isDead()){	
			model.destroyBody();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		view.render(gc, sbg, g);
		controller.render(gc, sbg, g);
		g.drawString("\nCoins;" + model.getCoinAmount() +
				"\nGems:" + model.getGemAmount() +
				"\nScore:" + model.getScore() +
				"\nKills:" + model.getKills() + 
				"\nVector: " + model.getBody().getLinearDamping(), gc.getWidth() -400, 2);
	}

	@Override
	public int getID() {
		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(fight){
			fightTimer.stop();
			fight = false;
		}else{
			model.resetHurted();
			hurtTimer.stop();
		}
	}
}
