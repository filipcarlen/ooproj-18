package states;

import java.awt.Rectangle;
import java.util.ArrayList;

import map.WorldMap;
import map.WorldShapes;
import model.BulletModel;
import model.CoinModel;
import model.GemModel;
import model.GunModel;
import model.HeroModel;
import model.IEntityModel;
import model.MovingFoeModel;
import model.StaticFoeModel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;
import utils.CollisionDetection;
import view.HeroView;
import view.StaticFoeView;
import controller.CollectibleController;
import controller.FiringController;
import controller.HeroController;
import controller.IEntityController;
import controller.MovingFoeController;
import controller.StaticFoeController;

public class PlayState extends BasicGameState{
	
	World world;
	static HeroModel hero;
	static HeroController contHero;
	HeroView pa;
	WorldMap wm;
	CollisionDetection cd;
	int nbr= 0;
	int stateID;
	Camera camera;
	FiringController firingCont;
	
	
	static ArrayList<IEntityModel> bodies = new ArrayList <IEntityModel>();
	static ArrayList<IEntityController> controllers = new ArrayList<IEntityController>();
	ArrayList<WorldShapes> terrain = new ArrayList <WorldShapes>();
	
	public PlayState(int id){
		stateID = id;
	}
	
	public void loadCharacters(){
		
	}
	
	public void loadWorld(){
		
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Creating the world
		Vec2 gravity = new Vec2(0.0f, 9.8f);
		world = new World(gravity);
		world.setAllowSleep(true);
		world.setContinuousPhysics(true);
		cd = new CollisionDetection();
		world.setContactListener(cd);
		wm = new WorldMap(world, true, "test");
		//Weapon Create
		GunModel gm = new GunModel(world, 500);
		firingCont = new FiringController(gm);
		// Creating a character
		hero = new HeroModel(world, "hero", gm);
		contHero = new HeroController(hero);
		// Camera
		camera = new Camera(gc.getWidth(), gc.getHeight(), new Rectangle(300,200), hero.getPosPixels());
		
		bodies.add(new MovingFoeModel(world, new Vec2(480,240), 100, new GunModel(world, 2000, 20, 100), 3));
		controllers.add(new MovingFoeController((MovingFoeModel)bodies.get(bodies.size()-1)));
		bodies.add(new StaticFoeModel(world, new Vec2(530, 330), 20, 4));
		controllers.add(new StaticFoeController((StaticFoeModel)bodies.get(bodies.size()-1), StaticFoeView.StaticFoeType.FIRE));
		
		bodies.add(new GemModel(world, new Vec2(500,240), 1));
		controllers.add(new CollectibleController((GemModel)bodies.get(bodies.size()-1)));
		bodies.add(new CoinModel(world,new Vec2(400, 340), 2));
		controllers.add(new CollectibleController((CoinModel)bodies.get(bodies.size()-1)));
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		wm.render(g, (int)camera.getCameraPosition().x, (int)camera.getCameraPosition().y, gc.getWidth(), gc.getHeight());
		firingCont.render(gc, sbg, g);

		try{
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).render(gc, sbg, g);
			}
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

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		camera.updateCamera(hero.getFrontPosPixels());
		//firingCont.update(gc, sbg, delta);
		try{
			contHero.update(gc, sbg, delta);
		}catch(NullPointerException e){} 

		for(int i = 0; i < controllers.size(); i++){
			controllers.get(i).update(gc, sbg, delta);
		}
	}

	public int getID() {
		return stateID;
	}
	
	public static HeroModel getHeroModel(){
		return hero;
	}
	
	public static void removeEntity(int id){
		int j = 0;
		for(int i = 0; i < controllers.size(); i++){
			if(((IEntityController)controllers.get(i)).getID() == id){
				j ++;
				controllers.remove(i);
				if(j > 1){
					j = 0;
					return;
				}
			}
			if(((IEntityModel)bodies.get(i)).getID() == id){
				j ++;
				bodies.remove(i);
				if(j > 1){
					j =0;
					return;
				}
			}
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
