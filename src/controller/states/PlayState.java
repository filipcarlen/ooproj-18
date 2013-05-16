package controller.states;

import java.awt.Rectangle;
import java.util.ArrayList;

import map.WorldMap;
import map.WorldShapes;
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
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;
import utils.Controls;
import view.HeroView;
import view.StaticFoeView;
import controller.CollectibleController;
import controller.CollisionDetection;
import controller.FiringController;
import controller.HeroController;
import controller.IEntityController;
import controller.IPlayStateController;
import controller.MovingFoeController;
import controller.StaticFoeController;

public class PlayState extends BasicGameState implements IPlayStateController{
	
	World world;
	HeroModel hero;
	HeroController contHero;
	HeroView pa;
	WorldMap wm;
	CollisionDetection cd;
	int nbr= 0;
	int stateID;
	Camera camera;
	FiringController firingCont, firingContE;
	
	
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
		wm = new WorldMap(world, true, "test1");
		//Weapon Create
		GunModel gm = new GunModel(world, 200);
		firingCont = new FiringController(gm, -1);
		GunModel gmE = new GunModel(world, 2000, 20, 15);
		firingContE = new FiringController(gmE, 3);
		// Creating a character
		hero = new HeroModel(world ,"hero", new Vector2f(0,2800), 50,50, gm);
		contHero = new HeroController(hero, this);
		// Camera
		camera = new Camera(gc.getWidth(), gc.getHeight(), wm.getWorldWidth(), wm.getWorldHeight(), new Rectangle(300,200), hero.getPosPixels());
		 
		bodies.add(new MovingFoeModel(world, new Vec2(480,2900), 100, gmE, 3));
		controllers.add(new MovingFoeController((MovingFoeModel)bodies.get(bodies.size()-1)));
		bodies.add(new StaticFoeModel(world, new Vec2(530, 2912), 20, 4));
		controllers.add(new StaticFoeController((StaticFoeModel)bodies.get(bodies.size()-1), StaticFoeView.StaticFoeType.FIRE));
		
		bodies.add(new GemModel(world, new Vec2(470,2720), 1));
		controllers.add(new CollectibleController((GemModel)bodies.get(bodies.size()-1), this));
		bodies.add(new CoinModel(world,new Vec2(400, 2850), 2));
		controllers.add(new CollectibleController((CoinModel)bodies.get(bodies.size()-1), this));
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		wm.render(g, (int)camera.getCameraPosition().x, (int)camera.getCameraPosition().y, gc.getWidth(), gc.getHeight());
		firingCont.render(gc, sbg, g);
		firingContE.render(gc, sbg, g);
		try{
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).render(gc, sbg, g);
			}
		}catch(NullPointerException e){}
		try{
			contHero.render(gc, sbg, g);
		}catch(NullPointerException e){} 
		g.drawString(" BodyCount" + world.getBodyCount(), 100, 100);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		firingCont.update(gc, sbg, delta);
		firingContE.update(gc, sbg, delta);
		try{
			camera.updateCamera(hero.getFrontPosPixels());
			contHero.update(gc, sbg, delta);
		}catch(NullPointerException e){} 

		for(int i = 0; i < controllers.size(); i++){
			controllers.get(i).update(gc, sbg, delta);
		}
		if(Controls.getInstance().check("pause")){
			this.pauseUpdate();
		}
	}

	public int getID() {
		return stateID;
	}
	
	public HeroModel getHeroModel(){
		return hero;
	}
	
	public void removeEntity(int id){
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

	public void removeHero() {
		contHero = null;
	}
}
