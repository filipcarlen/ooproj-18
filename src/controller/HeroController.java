package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Gun;
import model.Hero;
import model.Sword;

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

	/* This is the Model */
	private Hero model;

	/* This is the View */
	private HeroView view;
	
	/* This is the controller that controls the weapon*/
	private IEntityController controller;
	
	/* This timer controls how long time the hurt animations should continue*/
	private Timer hurtTimer = new Timer(220, this);

	public HeroController(Hero hm) {
		model = hm;
		view = new HeroView(hm);
		if(!model.hasLoadedDimension()){
			/* Sets the dimension of the hero after the slimmest animation standing*/
			model.setDimension(view.getWidth(), view.getHeight());
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
		/* Boolean to be able you check if the body is moving(only right and left)*/
		boolean move = false;
		
		/* Plays sound if the model has getting hurt or has died*/
		Sounds sound = Sounds.getInstance();
		switch(model.getCurrentHealthAction()){
		case HURT:
			sound.playSound(SoundType.HERO_HURT);
			break;
		case DEAD:
			sound.playSound(SoundType.HERO_DIE);
			break;
		case NONE:
			//Then the hero is as usual, got the same hp as before
			break;
		}
		
		/* Get the vector that is used to apply force in the x axis to the character */
		Vec2 heroVec = model.getBody().getWorldVector(new Vec2(-1.2f, 0.0f));
		
		/*
		 * Sets the linearDamping so that the body dosen't continue to go to the
		 * left, right or up
		 */
		model.getBody().setLinearDamping(1.5f);
	
		/* Starts the hurt Animation*/
		if(model.isHurted() && !hurtTimer.isRunning()){
			view.setAnimation(model.isHurtedFront()? HeroView.Movement.HURT: HeroView.Movement.HURTBACK, model.getDirection());
			hurtTimer.start();				// The timer is used to run the hurtAnimaion for a short period of time
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
				view.setAnimation(HeroView.Movement.RUN, model.getDirection());
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
				view.setAnimation(HeroView.Movement.RUN, model.getDirection());
		}
		if (Controls.getInstance().check("jump")) {
			move = true;
			/* This will start the jump animation */
			view.setAnimation(HeroView.Movement.JUMP, model.getDirection());
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
			view.setAnimation(HeroView.Movement.STAND, model.getDirection());
			model.getBody().setLinearDamping(5f);
		}
		
		if (Controls.getInstance().check("fight")) {
			/*
			 * If you push the jump button it will start the animation and call
			 * the attack method with it's weapon
			 */
			if(model.attack()){
				if(model.getWeaponType() == WeaponType.GUN)
					Sounds.getInstance().playSound(SoundType.GUN_SHOT);
				else if(model.getWeaponType() == WeaponType.SWORD)
					Sounds.getInstance().playSound(SoundType.SWORD_SWING);
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
		if(model.isFalling()){
			view.setAnimation(HeroView.Movement.FALL, model.getDirection());
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
	}

	@Override
	public int getID() {
		return -1;
	}
	
	public void updateWeaponAnimation(){
		view.loadWeaponAnimation(model.getName(), model.getWeaponType());
		fightTimer= new Timer(view.getAttackAnimation().getFrameCount()* 
				view.getAttackAnimation().getDuration(0),this);
		if(model.getWeaponType() == WeaponType.GUN)
			controller = new GunController((Gun) model.getWeapon());
		else if(model.getWeaponType() == WeaponType.SWORD){
			controller = new SwordController((Sword) model.getWeapon());
		}
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
