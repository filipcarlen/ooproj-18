package controller;

import model.HeroModel;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import states.PlayState;
import utils.Camera;
import utils.Controls;
import utils.Navigation;
import view.HeroView;

/**
 * Version: 1.0 A controller for the Hero. This class will interact with the
 * View of the hero and the Model, to make the character move and fight.
 * 
 * @author Project Group 18 (Chalmers, 2013)
 * 
 */

public class HeroController implements IEntityController {
	/* A Boolean to check if the Character is in the air or not */
	private boolean jump;

	/*
	 * This is a count how many times i will apply force in the y axis, this is
	 * use to simulate a real jump
	 */
	private int jumpCount = 20;

	/*
	 * This is a integer that will be set if somebody calls fight, and will
	 * prevent that the standing animation will be active
	 */
	private int fightTimer = 0;

	/* This is the Model */
	private static HeroModel model;

	/* This is the View */
	private HeroView pa;

	public HeroController(HeroModel hm) {
		this(hm, false);
	}

	public HeroController(HeroModel hm, boolean keyRegistrated) {
		model = hm;
		pa = new HeroView(hm.getName(), model.getWeaponType());
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

		/*
		 * Tells the view to applay the animation for standing, if the character
		 * isn't jumping or fighting
		 */
		if (!jump && model.getDoubleJump() < 1 || fightTimer == 0)
			pa.setStandAnimation();

		/* This if statements handels the inputs from the keyboard */
		if (Controls.getInstance().check("left")) {
			model.setDirection(Navigation.WEST);
			model.getBody().applyForce(heroVec.mul(-1),
					model.getBody().getPosition());
			/*
			 * If the character isn't jumping this will start the moving to the
			 * left animation
			 */
			if (!jump && model.getDoubleJump() < 1)
				pa.setLeftAnimation();
		}
		if (Controls.getInstance().check("right")) {
			model.setDirection(Navigation.EAST);
			model.getBody().applyForce(heroVec, model.getBody().getPosition());
			/*
			 * If the character isn't jumping this will start the moving to the
			 * right animation
			 */
			if (!jump && model.getDoubleJump() < 1)
				pa.setRightAnimation();
		}
		if (Controls.getInstance().check("jump")) {
			/* This will start the jump animation */
			pa.setJumpAnimation();
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
			pa.setAttackAnimation();
			model.attack();
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
				jumpCount = 20;
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		/*
		 * Draws the hero if it isn't dead. But if its dead it will remove the
		 * controller and model
		 */
		if (!model.isDead()) {
			Vec2 tmp = Camera.entityRender(model.getPosPixels());
			/* Draws the animation */
			g.drawAnimation(pa.getAnimation(), tmp.x, tmp.y);
			/* Draws the healthbar above the hero */
			g.setColor(Color.white);
			g.drawRect(tmp.x, tmp.y - 15, 101, 11);
			g.setColor(Color.red);
			g.fillRect(tmp.x + 1, tmp.y - 14, model.getHp(), 10);
			g.setColor(Color.white);
			/* Draws the name of the hero above the character and healthbar */
			g.setColor(Color.white);
			g.drawString(model.getName(), tmp.x, tmp.y - 40);
			/* Draws your scores*/
			g.setColor(Color.white);
			g.drawString("\nCoins;" + model.getCoinAmount() +
					"\nGems:" + model.getGemAmount() +
					"\nScore:" + model.getCollectedCoins() +
					"\nKills:" + model.getKills(), gc.getWidth() -100, 2);
		} else {
			model.destroyBody();
			PlayState.removeHero();
		}
	}

	@Override
	public int getID() {
		return -1;
	}
}
