package se.chalmers.grupp18.v01;

import java.util.List;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

public class CollisionDetection  implements ContactListener{
	
	List<IEntityModel> entityModels;
	List<WorldShapes> terrain;
	List<Body> terr;
	CharacterModel hero;
	CharacterController contHero;
	World w;

	public CollisionDetection(World w, List<IEntityModel> em, List<WorldShapes> ws, List<Body> b, CharacterModel hero, CharacterController contHero){
		this.w = w;
		this.entityModels = em;
		this.terrain = ws;
		this.hero = hero;
		this.contHero = contHero;
		this.terr = b;
		
	}
	
	@Override
	public void beginContact(Contact c) {
		for(Body b: terr){
			if((c.m_fixtureB.m_body == hero.body && c.m_fixtureA.m_body == b)||
					(c.m_fixtureA.m_body == hero.body && c.m_fixtureB.m_body == b)){
				System.out.println("bajs");
				contHero.setGroundContact();
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
