package controller.states;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import map.WorldMap;
import map.WorldShapeModel;
import model.AbstractCollectibleModel;
import model.AbstractWeaponModel;
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
import utils.WeaponType;
import view.HeroView;
import view.StaticFoeView;
import controller.CollectibleController;
import controller.CollisionDetection;
import controller.GunController;
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
	GunController gunCont, gunContE;
	
	
	ArrayList<IEntityModel> bodies = new ArrayList <IEntityModel>();
	ArrayList<IEntityController> controllers = new ArrayList<IEntityController>();
	ArrayList<WorldShapeModel> terrain = new ArrayList <WorldShapeModel>();
	
	public PlayState(int id){
		stateID = id;
	}
	
	public void loadWorld(String levelName){
		wm = new WorldMap(world, true, "test1");
		wm.setBounds();
	}
	
	public void loadCharacters(ArrayList<IEntityModel> bodies)throws SlickException{
		this.bodies = bodies;
		for(IEntityModel b: bodies){
			if(b instanceof MovingFoeModel){
				controllers.add(new MovingFoeController((MovingFoeModel)b, this));
			}else if(b instanceof StaticFoeModel){
				controllers.add(new StaticFoeController((StaticFoeModel)b));
			}else if(b instanceof AbstractCollectibleModel){
				controllers.add(new CollectibleController((AbstractCollectibleModel)b, this));
			}else{
				throw new SlickException("Couldn't load the entity " + b);
			}
		}
	}
	
	public void loadHero(String heroName, Vec2 pos, AbstractWeaponModel awm){
		hero = new HeroModel(world ,heroName, pos, awm);
		contHero = new HeroController(hero, this);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Creating the world
		Vec2 gravity = new Vec2(0.0f, 9.8f);
		world = new World(gravity);
		world.setAllowSleep(true);
		world.setContinuousPhysics(true);
		cd = new CollisionDetection();
		world.setContactListener(cd);
		loadWorld("test1");
		
		loadCharacters(wm.getListOfBodies());
		
		GunModel gm = new GunModel(world, 500, 10, 10, 56);
		
		loadHero("BluePants", wm.getHeroPosition(), gm);
		// Camera
		camera = new Camera(gc.getWidth(), gc.getHeight(), 
				wm.getWorldWidth(), wm.getWorldHeight(), 
				new Rectangle(300,200), hero.getPosPixels());
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		wm.render(g, (int)camera.getCameraPosition().x, (int)camera.getCameraPosition().y, gc.getWidth(), gc.getHeight());

		try{
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).render(gc, sbg, g);
			}
			contHero.render(gc, sbg, g);
		}catch(NullPointerException e){
			sbg.enterState(GameApp.GAMEOVERSTATE);
		}
		try{
			g.drawString(" BodyCount" + world.getBodyCount() +"\n HeroModelBody " + hero.getBody() + 
				"\n Force:" + hero.getBody().m_linearVelocity.y, 100, 100);
		} catch(NullPointerException e){			
			sbg.enterState(GameApp.GAMEOVERSTATE);
		}
				
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		try{
			camera.updateCamera(hero.getFrontPosPixels());
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).update(gc, sbg, delta);
			}
			contHero.update(gc, sbg, delta);
		}catch(NullPointerException e){}
		if(hero.isDead()){
			this.pauseUpdate();
			sbg.enterState(GameApp.GAMEOVERSTATE);
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
