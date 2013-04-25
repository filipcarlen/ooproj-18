package se.chalmers.grupp18.v01;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface IEntityController {
	public void update(GameContainer container, StateBasedGame game, int delta);
	public void render(GameContainer container, StateBasedGame game, Graphics g);
}
