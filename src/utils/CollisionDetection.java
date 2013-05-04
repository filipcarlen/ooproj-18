package utils;

import java.util.List;

import map.WorldShapes;
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

	public CollisionDetection(World w, List<IEntityModel> em, List<WorldShapes> ws, PlayState ps){
		this.w = w;
		this.entityModels = em;
		this.terrain = ws;
		this.playState =ps;
		
	}
	
	@Override
	public void beginContact(Contact c) {
		for(WorldShapes ws: terrain){
			if(c.m_fixtureB.m_body.getUserData() == PlayState.getHeroModel()){
				if(c.m_fixtureA.m_body.getUserData() == ws.getBody().getUserData())
					((HeroModel)c.m_fixtureB.m_body.getUserData()).setGroundContact();
			}
			if(c.m_fixtureA.m_body.getUserData() ==  PlayState.getHeroModel()){
				if(c.m_fixtureB.m_body.getUserData() == ws.getBody().getUserData())
					((HeroModel)c.m_fixtureA.m_body.getUserData()).setGroundContact();
			}
		}
		System.out.println("Contact");
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
