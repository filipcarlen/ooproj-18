package se.chalmers.grupp18.v01;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public interface IEntityController {
	public void init();
	public void update(GameContainer container, StateBasedGame game, int delta);
	public void render(GameContainer container, StateBasedGame game, Graphics g);
	public Vec2 getPosition();
	public void setPosition(Vec2 pos);
}
