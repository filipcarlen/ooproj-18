package controller;

import model.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.Contact;

import utils.EntityType;
import utils.SoundType;
import utils.Sounds;

public class CollisionDetection implements ContactListener{

	private Sounds sound = Sounds.getInstance();
	
	public CollisionDetection(){}
	
	@Override
	public void beginContact(Contact contact) {
		Object objectA = contact.getFixtureA().getBody().getUserData();
		Object objectB = contact.getFixtureB().getBody().getUserData();
		
		if(objectA instanceof Hero) {
			this.heroCollision((Hero)objectA, objectB);
		} 
		else if(objectB instanceof Hero) {
			this.heroCollision((Hero)objectB, objectA);
		} 
		else if(objectA instanceof MovingFoe) {
			this.foeCollision((MovingFoe)objectA, objectB);
		} 
		else if(objectB instanceof MovingFoe) {
			this.foeCollision((MovingFoe)objectB, objectA);
		} 
		else if(objectA instanceof Bullet) {
			((Bullet)objectA).destroyEntity();
		} 
		else if(objectB instanceof Bullet) {
			((Bullet)objectB).destroyEntity();
		} 
		else if(objectA instanceof Sword) {
			((Sword)objectA).destroyEntity();
		} 
		else if(objectB instanceof Sword) {
			((Sword)objectB).destroyEntity();
		}
	}
	
	/**
	 * This method handles what happens when the hero collides with something.
	 * @param hero	the hero
	 * @param other	the object the hero collided with
	 */
	private void heroCollision(Hero hero, Object other) {
		if(other ==  EntityType.GROUND) {
			hero.setGroundContact();
		} else if(other instanceof MovingFoe) {
			//If the hero jumps on top of the foe, the foe loses life and the hero is reflected.
			MovingFoe foe = ((MovingFoe)other);
			if(hero.getPosMeters().y + hero.getHeight()/2 - 0.025f < foe.getPosMeters().y - foe.getHeight()/2 + 0.025f) {
				foe.hurt(new Integer(20));
				this.playFoeSound(foe);
				hero.getBody().applyLinearImpulse(new Vec2(0, -4), hero.getBody().getPosition());
			}
		} else if(other instanceof ICollectible) {
			((AbstractCollectible)other).killBody();
			if(other instanceof AbstractPoints) {
				hero.incrementScore(((AbstractPoints)other).getValue());
				if(other instanceof Gem) {
					hero.incrementGem();
					this.sound.playSound(SoundType.COLLECT_GEM);
				} else {
					hero.incrementCoin();
					this.sound.playSound(SoundType.COLLECT_COIN);
				}
			} else {
				if(other instanceof ChocolateBar) {
					hero.heal((int)(Math.round(((AbstractPowerUp)other).gethpBoost() * hero.getMaxHp())));
					sound.playSound(SoundType.CHOCOLATE_BAR);
				} else {
					hero.heal((int)(Math.round(((AbstractPowerUp)other).gethpBoost() * hero.getMaxHp())));
					sound.playSound(SoundType.ENERGY_DRINK);
				}
			}
		} else if(other instanceof AbstractStaticFoe) {
			hero.setHurted(null,((AbstractStaticFoe)other).getDamage());
		} else if(other instanceof Bullet) {
			Bullet bullet = (Bullet)other;
			hero.setHurted(bullet.getNavigation(), bullet.getDamage());
			bullet.destroyEntity();
		} else if(other instanceof Sword) {
			Sword sword = (Sword)other;
			hero.setHurted(sword.getNavigation(), sword.getDamage());
			sword.destroyEntity();
		}
	}
	
	/**
	 * This method handles what happens if a foe collides with either a bullet or sword. 
	 * Collision with the hero is handled by heroCollision().
	 * @param foe	the foe
	 * @param other	the object the foe collided with
	 */
	private void foeCollision(MovingFoe foe, Object other) {
		if(other instanceof Bullet) {
			Bullet bullet = (Bullet)other;
			foe.hurt(bullet.getDamage());
			this.playFoeSound(foe);
			bullet.destroyEntity();
		} else if(other instanceof Sword) {
			Sword sword = (Sword)other;
			foe.hurt(sword.getDamage());
			this.playFoeSound(foe);
			sword.destroyEntity();
		}
	}
		
	/**
	 * This method plays a die or hurt sound of a foe depending on if the foe is dead or still alive.
	 * @param foe	the foe that was hurt
	 */
	public void playFoeSound(MovingFoe foe) {
		if(foe.isAlive()) {
			this.sound.playSound(SoundType.FOE_HURT);
		} else {
			this.sound.playSound(SoundType.FOE_DIE);
		}
	}

	@Override
	public void endContact(Contact c) {}

	@Override
	public void postSolve(Contact c, ContactImpulse ci) {}

	@Override
	public void preSolve(Contact c, Manifold m) {}
}
