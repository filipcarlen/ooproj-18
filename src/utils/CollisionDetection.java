package utils;

import model.BulletModel;
import model.HeroModel;
import model.ICollectibleModel;
import model.MovingFoeModel;
import model.StaticFoeModel;
import model.SwordModel;
import model.*;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

import states.PlayState;

public class CollisionDetection  implements ContactListener{

	MovingFoeModel foeModel;
	HeroModel heroModel;
	
	public CollisionDetection(){
		
	}
	
	@Override
	public void beginContact(Contact contact) {
		Object objectA = contact.getFixtureA().getBody().getUserData();
		Object objectB = contact.getFixtureB().getBody().getUserData();
		
		// Check if objectA of the collision is the hero and check what objectB is too 
		// make the right changes.
		if(objectA == PlayState.getHeroModel()){
			
			heroModel = (HeroModel)objectA;
			
			if(objectB ==  EntityType.GROUND){
				
				heroModel.setGroundContact();
			}
	
			else if(objectB instanceof ICollectibleModel){
				((AbstractCollectibleModel)objectB).killBody();
				PlayState.removeController();
			}
			
			else if(objectB instanceof StaticFoeModel) {
				heroModel.hurt(((StaticFoeModel)objectB).getDamage());
			}
			
			else if(objectB instanceof BulletModel) {
				heroModel.hurt(((BulletModel)objectB).getDamage());
			}
			
			else if(objectB instanceof SwordModel) {
				heroModel.hurt(((SwordModel)objectB).getDamage());
			}
		}
		
		// Check if objectB of the collision is the hero and check what objectA is too 
		// make the right changes.
		if(objectB ==  PlayState.getHeroModel()){
			
			heroModel = (HeroModel)objectB;
			
			if(objectA == EntityType.GROUND){
				heroModel.setGroundContact();
			}
	
			else if(objectA instanceof ICollectibleModel){
				((AbstractCollectibleModel)objectA).killBody();				
			}
			
			else if(objectA instanceof StaticFoeModel) {
				heroModel.hurt(((StaticFoeModel)objectA).getDamage());
			}
			
			else if(objectA instanceof BulletModel) {
				heroModel.hurt(((BulletModel)objectA).getDamage());
			}
			
			else if(objectA instanceof SwordModel) {
				heroModel.hurt(((SwordModel)objectA).getDamage());
			}
		}
		
		// Check if objectB of the collision is a moving foe and check what objectA is too 
		// make the right changes.
		if(objectA instanceof MovingFoeModel){
	
			foeModel = (MovingFoeModel)objectA;
			
			if(objectB instanceof ICollectibleModel){
				
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
			
			foeModel = (MovingFoeModel)objectB;
	
			if(objectA instanceof ICollectibleModel){
				
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
