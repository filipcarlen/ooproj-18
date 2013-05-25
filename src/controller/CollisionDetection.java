package controller;

import model.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import utils.EntityType;
import utils.SoundType;
import utils.Sounds;

public class CollisionDetection  implements ContactListener{

	private MovingFoeModel foeModel;
	private Hero heroModel;
	private Sounds sound = Sounds.getInstance();
	
	public CollisionDetection(){
		
	}
	
	@Override
	public void beginContact(Contact contact) {
		Object objectA = contact.getFixtureA().getBody().getUserData();
		Object objectB = contact.getFixtureB().getBody().getUserData();
		
		// Check if objectA of the collision is the hero and check what objectB is too 
		// make the right changes.
		if(objectA instanceof Hero){
			
			heroModel = (Hero)objectA;
			//This if statements checks what you're colliding with
			if(objectB ==  EntityType.GROUND){
				heroModel.setGroundContact();
			}else if(objectB instanceof MovingFoeModel){// This if Statement will call setGroundcontact when you jump on a enemies head
				if(heroModel.getPosMeters().y < ((MovingFoeModel)objectB).getPosMeters().y){
					heroModel.setGroundContact();
				}
			}else if(objectB instanceof ICollectible){
				((AbstractCollectible)objectB).killBody();
				if(objectB instanceof AbstractPoints){
					heroModel.incrementScore(((AbstractPoints)objectB).getValue());
					if(objectB instanceof Gem){
						heroModel.incrementGem();
						sound.playSound(SoundType.COLLECT_GEM);
					}else if(objectB instanceof Coin){
						heroModel.incrementCoin();
						sound.playSound(SoundType.COLLECT_COIN);
					}
				}
				if(objectB instanceof AbstractPowerUp)
					if(objectB instanceof ChocolateBar){
						heroModel.heal((int)(Math.round(((AbstractPowerUp)objectB).gethpBoost() * heroModel.getMaxHp())));
						sound.playSound(SoundType.CHOCOLATE_BAR);
					}else if(objectB instanceof EnergyDrink){
						heroModel.heal((int)(Math.round(((AbstractPowerUp)objectB).gethpBoost() * heroModel.getMaxHp())));
						sound.playSound(SoundType.ENERGY_DRINK);
				}
			}else if(objectB instanceof StaticFoeModel) {
				heroModel.setHurted(null,((StaticFoeModel)objectB).getDamage());
			}else if(objectB instanceof Bullet) {
				heroModel.setHurted(((Bullet)objectB).getNavigation(),((Bullet)objectB).getDamage());
				((Bullet)objectB).destroyEntity();
			}else if(objectB instanceof Sword) {
				heroModel.setHurted(((Sword)objectB).getNavigation() ,((Sword)objectB).getDamage());
				((Sword)objectB).destroyEntity();
			}
		}
		
		// Check if objectB of the collision is the hero and check what objectA is too 
		// make the right changes.
		else if(objectB instanceof Hero){
			
			heroModel = (Hero)objectB;
			//This if statements checks what you're colliding with
			if(objectA == EntityType.GROUND){
				heroModel.setGroundContact();
			}else if(objectA instanceof MovingFoeModel){// This if Statement will call setGroundcontact when you jump on a enemies head
				if(heroModel.getPosMeters().y < ((MovingFoeModel)objectA).getPosMeters().y){
					heroModel.setGroundContact();
				}
			}else if(objectA instanceof ICollectible){
				((AbstractCollectible)objectA).killBody();
				if(objectA instanceof AbstractPoints){
					heroModel.incrementScore(((AbstractPoints)objectA).getValue());
					if(objectA instanceof Gem){
						heroModel.incrementGem();
						sound.playSound(SoundType.COLLECT_GEM);
					}else if(objectA instanceof Coin){
						heroModel.incrementCoin();
						sound.playSound(SoundType.COLLECT_COIN);
					}
				}
				if(objectA instanceof AbstractPowerUp)
					if(objectA instanceof ChocolateBar){
						heroModel.heal((int)(Math.round(((AbstractPowerUp)objectA).gethpBoost() * heroModel.getMaxHp())));
						sound.playSound(SoundType.CHOCOLATE_BAR);
					}else if(objectA instanceof EnergyDrink){
						heroModel.heal((int)(Math.round(((AbstractPowerUp)objectA).gethpBoost() * heroModel.getMaxHp())));
						sound.playSound(SoundType.ENERGY_DRINK);
				}
			}else if(objectA instanceof StaticFoeModel) {
				heroModel.setHurted(null,((StaticFoeModel)objectA).getDamage());
			}else if(objectA instanceof Bullet) {
				heroModel.setHurted( ((Bullet)objectA).getNavigation() ,((Bullet)objectA).getDamage());
				((Bullet)objectA).destroyEntity();
			}else if(objectA instanceof Sword) {
				heroModel.setHurted(((Sword)objectA).getNavigation(), ((Sword)objectA).getDamage());
				((Sword)objectA).destroyEntity();
			}
		}
		
		// Check if objectA of the collision is a moving foe and check what objectB is too 
		// make the right changes.
		else if(objectA instanceof MovingFoeModel){
	
			foeModel = (MovingFoeModel)objectA;
			
			if(objectB instanceof Bullet) {
				foeModel.hurt(((Bullet)objectB).getDamage());
				((Bullet)objectB).destroyEntity();
			}
			
			else if(objectB instanceof Sword) {
				foeModel.hurt(((Sword)objectB).getDamage());
				((Sword)objectB).destroyEntity();
			}
		}
		
		// Check if objectB of the collision is a moving foe and check what objectA is too 
		// make the right changes.
		else if(objectB instanceof MovingFoeModel){
			
			foeModel = (MovingFoeModel)objectB;
			
			if(objectA instanceof Bullet) {
				foeModel.hurt(((Bullet)objectA).getDamage());
				((Bullet)objectA).destroyEntity();
			}
			
			else if(objectA instanceof Sword) {
				foeModel.hurt(((Sword)objectA).getDamage());
				((Sword)objectA).destroyEntity();
			}
		}
		
		// Check if objectA of the collision is a sword and remove it if it hits anything
		else if(objectA instanceof Bullet){
			
			((Bullet)objectA).destroyEntity();

		}
				
		// Check if objectB of the collision is a bullet and remove it if it hits anything
		else if(objectB instanceof Bullet){
					
			((Bullet)objectB).destroyEntity();

		}
		
		// Check if objectA of the collision is a sword and remove it if it hits anything
		else if(objectA instanceof Sword){
			
			((Sword)objectA).destroyEntity();

		}
				
		// Check if objectB of the collision is a sword and remove it if it hits anything
		else if(objectB instanceof Sword){
					
			((Sword)objectB).destroyEntity();

		}
	}

	@Override
	public void endContact(Contact c) {

	}

	@Override
	public void postSolve(Contact c, ContactImpulse ci) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact c, Manifold m) {
		// TODO Auto-generated method stub
		
	}
}
