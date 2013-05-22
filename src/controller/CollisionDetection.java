package controller;

import model.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import utils.EntityType;

public class CollisionDetection  implements ContactListener{

	private MovingFoeModel foeModel;
	private HeroModel heroModel;
	
	public CollisionDetection(){
		
	}
	
	@Override
	public void beginContact(Contact contact) {
		Object objectA = contact.getFixtureA().getBody().getUserData();
		Object objectB = contact.getFixtureB().getBody().getUserData();
		
		// Check if objectA of the collision is the hero and check what objectB is too 
		// make the right changes.
		if(objectA instanceof HeroModel){
			
			heroModel = (HeroModel)objectA;
			//This if statements checks what you're colliding with
			if(objectB ==  EntityType.GROUND){
				heroModel.setGroundContact();
			}else if(objectB instanceof MovingFoeModel){// This if Statement will call setGroundcontact when you jump on a enemies head
				if(heroModel.getPosMeters().y < ((MovingFoeModel)objectB).getPosMeters().y)
					heroModel.setGroundContact();
			}else if(objectB instanceof ICollectibleModel && objectB instanceof AbstractPointsModel){
				((AbstractPointsModel)objectB).killBody();
				heroModel.incrementScore(((AbstractPointsModel)objectB).getValue());
				if(objectB instanceof GemModel){
					heroModel.incrementGem();
				}else{
					heroModel.incrementCoin();
				}
				
			}else if(objectB instanceof ICollectibleModel && objectB instanceof AbstractPowerUpModel){
				((AbstractPowerUpModel)objectB).killBody();
				heroModel.setHp((int)((AbstractPowerUpModel)objectB).gethpBoost()*heroModel.getMaxHp());
				if(objectB instanceof ChocolateBarModel){
					heroModel.incrementGem();
				}else{
					heroModel.incrementCoin();
				}
				
			}else if(objectB instanceof StaticFoeModel) {
				heroModel.setHurted(null,((StaticFoeModel)objectB).getDamage());
			}else if(objectB instanceof BulletModel) {
				heroModel.setHurted(((BulletModel)objectB).getNavigation(),((BulletModel)objectB).getDamage());
				((BulletModel)objectB).destroyEntity();
			}else if(objectB instanceof SwordModel) {
				heroModel.setHurted(((SwordModel)objectB).getNavigation() ,((SwordModel)objectB).getDamage());
				((SwordModel)objectB).destroyEntity();
			}
		}
		
		// Check if objectB of the collision is the hero and check what objectA is too 
		// make the right changes.
		else if(objectB instanceof HeroModel){
			
			heroModel = (HeroModel)objectB;
			//This if statements checks what you're colliding with
			if(objectA == EntityType.GROUND){
				heroModel.setGroundContact();
			}else if(objectA instanceof MovingFoeModel){// This if Statement will call setGroundcontact when you jump on a enemies head
				if(heroModel.getPosMeters().y < ((MovingFoeModel)objectA).getPosMeters().y){
					heroModel.setGroundContact();
				}
			}else if(objectA instanceof ICollectibleModel && objectA instanceof AbstractPointsModel){
				((AbstractPointsModel)objectA).killBody();
				heroModel.incrementScore(((AbstractPointsModel)objectA).getValue());
				if(objectA instanceof GemModel){
					heroModel.incrementGem();
				}else{
					heroModel.incrementCoin();
				}
				
			}else if(objectA instanceof ICollectibleModel && objectA instanceof AbstractPowerUpModel){
				((AbstractPowerUpModel)objectA).killBody();
				heroModel.setHp((int)((AbstractPowerUpModel)objectA).gethpBoost()*heroModel.getMaxHp());
				if(objectB instanceof ChocolateBarModel){
					heroModel.incrementGem();
				}else{
					heroModel.incrementCoin();
				}
			}else if(objectA instanceof StaticFoeModel) {
				heroModel.setHurted(null,((StaticFoeModel)objectA).getDamage());
			}else if(objectA instanceof BulletModel) {
				heroModel.setHurted( ((BulletModel)objectA).getNavigation() ,((BulletModel)objectA).getDamage());
				((BulletModel)objectA).destroyEntity();
			}else if(objectA instanceof SwordModel) {
				heroModel.setHurted(((SwordModel)objectA).getNavigation(), ((SwordModel)objectA).getDamage());
				((SwordModel)objectA).destroyEntity();
			}
		}
		
		// Check if objectA of the collision is a moving foe and check what objectB is too 
		// make the right changes.
		else if(objectA instanceof MovingFoeModel){
	
			foeModel = (MovingFoeModel)objectA;
			
			if(objectB instanceof BulletModel) {
				foeModel.hurt(((BulletModel)objectB).getDamage());
				((BulletModel)objectB).destroyEntity();
			}
			
			else if(objectB instanceof SwordModel) {
				foeModel.hurt(((SwordModel)objectB).getDamage());
				((SwordModel)objectB).destroyEntity();
			}
		}
		
		// Check if objectB of the collision is a moving foe and check what objectA is too 
		// make the right changes.
		else if(objectB instanceof MovingFoeModel){
			
			foeModel = (MovingFoeModel)objectB;
			
			if(objectA instanceof BulletModel) {
				foeModel.hurt(((BulletModel)objectA).getDamage());
				((BulletModel)objectA).destroyEntity();
			}
			
			else if(objectA instanceof SwordModel) {
				foeModel.hurt(((SwordModel)objectA).getDamage());
				((SwordModel)objectA).destroyEntity();
			}
		}
		
		// Check if objectA of the collision is a sword and remove it if it hits anything
		else if(objectA instanceof BulletModel){
			
			((BulletModel)objectA).destroyEntity();

		}
				
		// Check if objectB of the collision is a bullet and remove it if it hits anything
		else if(objectB instanceof BulletModel){
					
			((BulletModel)objectB).destroyEntity();

		}
		
		// Check if objectA of the collision is a sword and remove it if it hits anything
		else if(objectA instanceof SwordModel){
			
			((SwordModel)objectA).destroyEntity();

		}
				
		// Check if objectB of the collision is a sword and remove it if it hits anything
		else if(objectB instanceof SwordModel){
					
			((SwordModel)objectB).destroyEntity();

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
