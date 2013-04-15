package se.chalmers.grupp18.v01;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState{
	
	public Play(int id){
		
	}
	
	private MapTiles map;
	private int x = 0;
	private int y = 0;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		 map = new MapTiles("test", true);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		 map.renderCollision(x, y, 30, 20);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		 Input input = gc.getInput();
		 if(input.isKeyDown(Input.KEY_RIGHT)){
			 x +=1;
		 }
		
	}

	@Override
	public int getID() {
		return 0;
	}

}
