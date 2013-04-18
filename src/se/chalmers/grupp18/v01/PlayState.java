package se.chalmers.grupp18.v01;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState{
	
	World world;
	
	public PlayState(int id){
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Vec2 gravity = new Vec2(0.0f, -10.0f);
		world = new World(gravity);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
	}

	@Override
	public int getID() {
		return 0;
	}
	
}
