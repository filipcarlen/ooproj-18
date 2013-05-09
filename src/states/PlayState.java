package states;

import java.util.ArrayList;

import map.WorldMap;
import map.WorldShapes;
import model.CoinModel;
import model.GemModel;
import model.HeroModel;
import model.ICollectibleModel;
import model.IEntityModel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.CollisionDetection;
import view.HeroView;
import controller.CollectibleController;
import controller.HeroController;
import controller.IEntityController;

public class PlayState extends BasicGameState{
	
	World world;
	static HeroModel hero;
	static HeroController contHero;
	HeroView pa;
	WorldMap wm;
	CollisionDetection cd;
	
	
	static ArrayList<IEntityModel> bodies = new ArrayList <IEntityModel>();
	static ArrayList<IEntityController> controllers = new ArrayList<IEntityController>();
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
		bodies.add(new CoinModel(world,new Vec2(500,340)));
		controllers.add(new CollectibleController((CoinModel)bodies.get(bodies.size()-1)));
		
		cd = new CollisionDetection();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		wm.render(g);
		try{
			((CollectibleController)controllers.get(0)).render(gc, sbg, g);
		}catch(IndexOutOfBoundsException e){}
		try{
			contHero.render(gc, sbg, g);
		}catch(NullPointerException e){} 
		g.drawString("Force: " + hero.getBody().m_force +
				"\nisAwake: " + hero.getBody().isAwake() +
				"\nisActive: " + hero.getBody().isActive() +
				"\nPosition: " + hero.getBody().getPosition() + 
				"\nBodyCount" + world.getBodyCount(), 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		try{
			contHero.update(gc, sbg, delta);
		}catch(NullPointerException e){} 
	}

	@Override
	public int getID() {
		return 0;
	}
	
	public static HeroModel getHeroModel(){
		return hero;
	}
	
	public static void removeController(){
		if(controllers.size() > 0){
			controllers.remove(0);
		}
	}
	
	public HeroController getHeroController(){
		return contHero;
	}

	public static void removeHeroController(IEntityController controller) {
		if(controller == contHero){
			contHero = null;
		} 
	}
}
