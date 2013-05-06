package utils;

import java.util.List;

import map.WorldShapes;
import model.CollectibleModel;
import model.IEntityModel;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import controller.HeroController;
import model.HeroModel;

import states.PlayState;

public class CollisionDetection  implements ContactListener{
	
	List<IEntityModel> entityModels;
	List<WorldShapes> terrain;
	PlayState playState;
	World w;
	CollectibleModel cm;

	public CollisionDetection(World w, List<IEntityModel> em, List<WorldShapes> ws, PlayState ps, CollectibleModel cm){
		this.w = w;
		this.entityModels = em;
		this.terrain = ws;
		this.playState =ps;
		this.cm = cm;
		
	}
	
	@Override
	public void beginContact(Contact c) {
		if(c.m_fixtureB.m_body.getUserData() == PlayState.getHeroModel()){
			if(c.m_fixtureA.m_body.getUserData() == EntityType.GROUND)
				((HeroModel)c.m_fixtureB.m_body.getUserData()).setGroundContact();
			
			else if(c.m_fixtureA.m_body.getUserData() == cm){
				cm.killBody();
			}
		}
		if(c.m_fixtureA.m_body.getUserData() ==  PlayState.getHeroModel()){
			if(c.m_fixtureB.m_body.getUserData() ==  EntityType.GROUND)
				((HeroModel)c.m_fixtureA.m_body.getUserData()).setGroundContact();
			
			else if(c.m_fixtureB.m_body.getUserData() == cm){
				cm.killBody();
			}
		}
		System.out.println(c.m_fixtureA.m_body.getUserData() + "\n" + c.m_fixtureB.m_body.getUserData());
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
