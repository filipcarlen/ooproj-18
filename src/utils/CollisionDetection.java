package utils;

import model.BulletModel;
import model.CollectibleModel;
import model.MovingFoeModel;
import model.StaticFoeModel;
import model.SwordModel;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import model.HeroModel;

import states.PlayState;

public class CollisionDetection  implements ContactListener{
	
	HeroModel heromodel;

	public CollisionDetection(){
		
	}
	
	@Override
	public void beginContact(Contact contact) {
		
		Object objectA = contact.getFixtureA().getBody().getUserData();
		Object objectB = contact.getFixtureB().getBody().getUserData();
		
		// Check if objectA of the collision is the hero and check what objectB is too 
		// make the right changes.
		if(objectA == PlayState.getHeroModel()){
			//
			heromodel = (HeroModel)objectA;
			
<<<<<<< HEAD
			if(objectB ==  EntityType.GROUND)
				heromodel.setGroundContact();
=======
			HeroModel heroModel = (HeroModel)objectA;
			
			if(objectB ==  EntityType.GROUND){
				
				heroModel.setGroundContact();
			}
>>>>>>> 0971286cb31bffc80270be0ddae68c3392d8d219
	
			else if(objectB instanceof CollectibleModel){
				System.out.println("Collectible is B");
			}
			
			else if(objectB instanceof StaticFoeModel) {
				heromodel.setGroundContact();
				heromodel.hurt(((StaticFoeModel)objectB).getDamage());
			}
			
			else if(objectB instanceof BulletModel) {
				heromodel.hurt(((BulletModel)objectB).getDamage());
			}
			
			else if(objectB instanceof SwordModel) {
				heromodel.hurt(((SwordModel)objectB).getDamage());
			}
		}
		
		// Check if objectB of the collision is the hero and check what objectA is too 
		// make the right changes.
		if(objectB ==  PlayState.getHeroModel()){
			
			heromodel = (HeroModel)objectB;
			
			if(objectA == EntityType.GROUND)
				heromodel.setGroundContact();
			else if(objectA instanceof CollectibleModel)
				System.out.println("Collectible is A");
			
			else if(objectA instanceof StaticFoeModel) {
				heromodel.setGroundContact();
				heromodel.hurt(((StaticFoeModel)objectA).getDamage());
			}
			
			else if(objectA instanceof BulletModel) {
				heromodel.hurt(((BulletModel)objectA).getDamage());
			}
			
			else if(objectA instanceof SwordModel) {
				heromodel.hurt(((SwordModel)objectA).getDamage());
			}
		}
		
		// Check if objectB of the collision is a moving foe and check what objectA is too 
		// make the right changes.
		if(objectA instanceof MovingFoeModel){
	
			MovingFoeModel foeModel = (MovingFoeModel)objectA;
			
			if(objectB instanceof CollectibleModel){
				
			}
			
			else if(objectB instanceof StaticFoeModel) {
				foeModel.hurt(((StaticFoeModel)objectA).getDamage());
			}
			
			else if(objectB instanceof BulletModel) {
				foeModel.hurt(((BulletModel)objectA).getDamage());
			}
			
			else if(objectB instanceof SwordModel) {
				foeModel.hurt(((SwordModel)objectA).getDamage());
			}
		}
		
		// Check if objectB of the collision is a moving foe and check what objectA is too 
		// make the right changes.
		if(objectB instanceof MovingFoeModel){
			
			MovingFoeModel foeModel = (MovingFoeModel)objectB;
	
			if(objectA instanceof CollectibleModel){
				
			}
			
			else if(objectA instanceof StaticFoeModel) {
				foeModel.hurt(((StaticFoeModel)objectA).getDamage());
			}
			
			else if(objectA instanceof BulletModel) {
				foeModel.hurt(((BulletModel)objectA).getDamage());
			}
			
			else if(objectA instanceof SwordModel) {
				foeModel.hurt(((SwordModel)objectA).getDamage());
			}
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
