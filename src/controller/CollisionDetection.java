package controller;

import model.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import utils.EntityType;

public class CollisionDetection  implements ContactListener{

	MovingFoeModel foeModel;
	HeroModel heroModel;
	private BulletModel bulletModel;
	
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
			
			if(objectB ==  EntityType.GROUND){
				
				heroModel.setGroundContact();
			}
	
			else if(objectB instanceof ICollectibleModel){
				((AbstractCollectibleModel)objectB).killBody();
				heroModel.incrementScore(((AbstractCollectibleModel)objectB).getValue());
				if(objectB instanceof GemModel){
					heroModel.incrementGem();
				}else{
					heroModel.incrementCoin();
				}
			}
			
			else if(objectB instanceof StaticFoeModel) {
				heroModel.hurt(((StaticFoeModel)objectB).getDamage());
			}
			
			else if(objectB instanceof BulletModel) {
				heroModel.hurt(((BulletModel)objectB).getDamage());
				((BulletModel)objectB).setAlive(false);
			}
			
			else if(objectB instanceof SwordModel) {
				heroModel.hurt(((SwordModel)objectB).getDamage());
			}
		}
		
		// Check if objectB of the collision is the hero and check what objectA is too 
		// make the right changes.
		else if(objectB instanceof HeroModel){
			
			heroModel = (HeroModel)objectB;
			
			if(objectA == EntityType.GROUND){
				heroModel.setGroundContact();
			}
	
			else if(objectA instanceof ICollectibleModel){
				((AbstractCollectibleModel)objectA).killBody();
				heroModel.incrementScore(((AbstractCollectibleModel)objectA).getValue());
				if(objectA instanceof GemModel){
					heroModel.incrementGem();
				}else{
					heroModel.incrementCoin();
				}
			}
			
			else if(objectA instanceof StaticFoeModel) {
				heroModel.hurt(((StaticFoeModel)objectA).getDamage());
			}
			
			else if(objectA instanceof BulletModel) {
				heroModel.hurt(((BulletModel)objectA).getDamage());
				((BulletModel)objectA).setAlive(false);
			}
			
			else if(objectA instanceof SwordModel) {
				heroModel.hurt(((SwordModel)objectA).getDamage());
			}
		}
		
		// Check if objectA of the collision is a moving foe and check what objectB is too 
		// make the right changes.
		else if(objectA instanceof MovingFoeModel){
	
			foeModel = (MovingFoeModel)objectA;
			
			if(objectB instanceof BulletModel) {
				foeModel.hurt(((BulletModel)objectB).getDamage());
				((BulletModel)objectB).setAlive(false);
			}
			
			else if(objectB instanceof SwordModel) {
				foeModel.hurt(((SwordModel)objectB).getDamage());
			}
		}
		
		// Check if objectB of the collision is a moving foe and check what objectA is too 
		// make the right changes.
		else if(objectB instanceof MovingFoeModel){
			
			foeModel = (MovingFoeModel)objectB;
			
			if(objectA instanceof BulletModel) {
				foeModel.hurt(((BulletModel)objectA).getDamage());
				((BulletModel)objectA).setAlive(false);
			}
			
			else if(objectA instanceof SwordModel) {
				foeModel.hurt(((SwordModel)objectA).getDamage());
			}
		}
		
		// Check if objectA of the collision is a bullet and check what objectB is too 
		// make the right changes.
		else if(objectA instanceof BulletModel){
			bulletModel = (BulletModel)objectA;
			
			if(objectB instanceof BulletModel) {
				((BulletModel)objectB).setAlive(false);
			}
			
			bulletModel.setAlive(false);

		}
				
		// Check if objectB of the collision is a bullet and check what objectA is too 
		// make the right changes.
		else if(objectB instanceof BulletModel){
					
			bulletModel = (BulletModel)objectB;
			bulletModel.setAlive(false);

		}
		
		System.out.println(objectA + "\n" + objectB);
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
