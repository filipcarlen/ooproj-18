package states;

import java.util.ArrayList;

import map.WorldMap;
import map.WorldShapes;
import model.HeroModel;
import model.IEntityModel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.CollisionDetection;
import view.HeroView;

import controller.HeroController;

public class PlayState extends BasicGameState{
	
	World world;
	static HeroModel hero;
	HeroController contHero;
	HeroView pa;
	WorldMap wm;
	CollisionDetection cd;
	
	ArrayList<IEntityModel> boddies = new ArrayList <IEntityModel>();
	ArrayList<WorldShapes> terrain = new ArrayList <WorldShapes>();
	
	public PlayState(int id){
		
	}
	
	public void loadCharacters(){
		
	}
	
	public void loadWorld(){
		
	}

	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Creating the world
		Vec2 gravity = new Vec2(0.0f, 9.8f);
		world = new World(gravity);
		world.step(1f/60f, 8, 3);
		world.setAllowSleep(true);
		world.setContinuousPhysics(true);
		
		world.setContactListener(cd);
		wm = new WorldMap(world, true, "test");
		// Creating a character
		hero = new HeroModel(world, "hero");
		contHero = new HeroController(hero);
		// Camera
		cd = new CollisionDetection(world, boddies, wm.getListOfShapes(), this);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		contHero.render(gc, sbg, g);
		wm.render(g);
		g.drawString("Force: " + hero.getBody().m_force +
				"\nisAwake: " + hero.getBody().isAwake() +
				"\nisActive: " + hero.getBody().isActive() +
				"\nPosition: " + hero.getBody().getPosition(), 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		contHero.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		return 0;
	}
	
	public static HeroModel getHero(){
		return hero;
	}
	
	public HeroController getHeroController(){
		return contHero;
	}
}
