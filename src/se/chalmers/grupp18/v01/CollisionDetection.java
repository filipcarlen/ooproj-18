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
	PlayState playState;
	World w;

	public CollisionDetection(World w, List<IEntityModel> em, List<WorldShapes> ws, List<Body> b, PlayState ps){
		this.w = w;
		this.entityModels = em;
		this.terrain = ws;
		this.terr = b;
		this.playState =ps;
		
	}
	
	@Override
	public void beginContact(Contact c) {
		for(Body ws: terr){
			if((c.m_fixtureB.m_body.getUserData() == PlayState.getHero().getBody().getUserData() && c.m_fixtureA.m_body.getUserData() == ws.getUserData())||
					(c.m_fixtureA.m_body.getUserData() == PlayState.getHero().getBody().getUserData() && c.m_fixtureB.m_body.getUserData() == ws.getUserData())){
				System.out.println("ja");
				playState.getHeroController().setGroundContact();
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
