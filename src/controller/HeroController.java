package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.GunModel;
import model.HeroModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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
 * @author Project Group 18 (Chalmers, 2013)
 * 
 */

public class HeroController implements IEntityController, ActionListener {
	/* A Boolean to check if the Character is in the air or not */
	private boolean jump;
	
	/*
	 * This is a count how many times i will apply force in the y axis, this is
	 * use to simulate a real jump
	 */
	private int jumpCount = 23;

	/*
	 * This is a integer that will be set if somebody calls fight, and will
	 * prevent that the standing animation will be active
	 */
	private int fightTimer = 20;
	
	private boolean fight = false;

	/* This is the Model */
	private HeroModel model;

	/* This is the View */
	private HeroView view;
	
	/* This is the controller that controls the weapon*/
	private GunController firing;
	
	/* This timer controls how long time the hurt animations should continue*/
	private Timer hurtTimer = new Timer(200, this);

	public HeroController(HeroModel hm) {
		this(hm, false);
	}

	public HeroController(HeroModel hm, boolean keyRegistrated) {
		model = hm;
		view = new HeroView(hm, model.getWeaponType());
		if(!model.isBodyCreated()){
			model.setDimension(30, view.getHeight());
		}
		System.out.println(model.getWeaponType());
		if(model.getWeaponType() == WeaponType.gun)
			firing = new GunController((GunModel) model.getWeapon());
		if (!keyRegistrated)
			/* Sets the Controls to the default options */
			Controls.getInstance().setDeafaultControls();
		/* Otherwise the controls already is set */
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		/* Tells the Control which key i'm pressing */
		Controls.getInstance().updateInput(gc.getInput());
		
		/* Get the vector that is used to apply force to the character */
		Vec2 heroVec = model.getBody().getWorldVector(new Vec2(-1.0f, 0.0f));
		
		/*
		 * Sets the linearDamping so that the body dosen't continue to go to the
		 * left, right or up
		 */
		model.getBody().setLinearDamping(1.5f);

		if(model.isHurted() && !hurtTimer.isRunning()){
			System.out.println(model.isHurtedFront()? HeroView.Movement.hurt: HeroView.Movement.hurtback);
			view.setAnimation(model.isHurtedFront()? HeroView.Movement.hurt: HeroView.Movement.hurtback, model.getDirection());
			hurtTimer.start();
		}
		
		/*
		 * Tells the view to applay the animation for standing, if the character
		 * isn't jumping or fighting
		 */
		if (!jump && !model.isFalling() && !model.isHurted()&& !fight)
			view.setAnimation(HeroView.Movement.stand, model.getDirection());

		/* This if statements handels the inputs from the keyboard */
		if (Controls.getInstance().check("left")) {
			model.setDirection(Navigation.WEST);
			model.getBody().applyForce(heroVec.mul(-1), model.getBody().getPosition());
			/*
			 * If the character isn't jumping this will start the moving to the
			 * left animation
			 */
			if (!jump && !model.isFalling() && !model.isHurted()&& !fight)
				view.setAnimation(HeroView.Movement.run, model.getDirection());
		}
		if (Controls.getInstance().check("right")) {
			model.setDirection(Navigation.EAST);
			model.getBody().applyForce(heroVec, model.getBody().getPosition());
			/*
			 * If the character isn't jumping this will start the moving to the
			 * right animation
			 */
			if (!jump && !model.isFalling() && !model.isHurted()&& !fight)
				view.setAnimation(HeroView.Movement.run, model.getDirection());
		}
		if (Controls.getInstance().check("jump") && !model.isFalling()) {
			/* This will start the jump animation */
			view.setAnimation(HeroView.Movement.jump, model.getDirection());
			/* This check that we haven't jumped two times */
			if (model.getDoubleJump() < 2)
				jump = true;
			model.incrementJumps();
		}
		if (Controls.getInstance().check("fight")) {
			/*
			 * If you push the jump button it will start the animation and call
			 * the attack method with it's weapon
			 */
			if(model.attack()){
				try {
					Sounds.getInstance().playSound(SoundType.GUN);
				} catch (SlickException e) {}
				fight = true;
				view.setAttackAnimation(model.getDirection());
			}
	
		}
		
		if(fight){
			fightTimer -=1;
			if(fightTimer <= 0){
				fightTimer = 20;
				fight = false;
			}
		}

		/*
		 * This if Statement decrease the jumpcount so that it will reach 0 and
		 * stop applying force in the y axis
		 */
		if (jump) {
			model.getBody().applyForce(
					model.getBody().getWorldVector(new Vec2(.0f, 10.0f)),
					model.getBody().getPosition());
			jumpCount -= 1;
			if (jumpCount <= 0) {
				jump = false;
				jumpCount = 23;
			}
		}
		if(model.getBody().m_linearVelocity.y > 0.1f ){
			view.setAnimation(HeroView.Movement.fall, model.getDirection());
			model.falling();
		}
		firing.update(gc, sbg, delta);
		
		if(model.isDead()){	
			model.destroyBody();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		view.render(gc, sbg, g);
		firing.render(gc, sbg, g);
		g.drawString("\nCoins;" + model.getCoinAmount() +
				"\nGems:" + model.getGemAmount() +
				"\nScore:" + model.getScore() +
				"\nKills:" + model.getKills(), gc.getWidth() -100, 2);
	}

	@Override
	public int getID() {
		return -1;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		model.resetHurted();
		hurtTimer.stop();
	}
}
