package controller.states;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import map.WorldMap;
import model.AbstractCollectibleModel;
import model.AbstractWeaponModel;
import model.CoinModel;
import model.GemModel;
import model.GunModel;
import model.HeroModel;
import model.IEntityModel;
import model.MovingFoeModel;
import model.StaticFoeModel;
import model.SwordModel;
import model.WorldShapeModel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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
import controller.SwordController;

public class PlayState extends BasicGameState implements IPlayStateController, ActionListener{
	
	World world;
	HeroModel hero;
	HeroController contHero;
	HeroView pa;
	WorldMap wm;
	CollisionDetection cd;
	int nbr= 0;
	int stateID;
	Camera camera;
	GunController heroGunController;
	SwordController heroSwordController;
	Timer endGameDelay = new Timer(2000, this);
	boolean endGame = false;
	
	
	ArrayList<IEntityModel> bodies = new ArrayList <IEntityModel>();
	ArrayList<IEntityController> controllers = new ArrayList<IEntityController>();
	ArrayList<WorldShapeModel> terrain = new ArrayList <WorldShapeModel>();
	
	ArrayList<GunModel> gunThatsActive= new ArrayList<GunModel>();
	
	public PlayState(int id){
		stateID = id;
	}
	
	public void loadWorld(String levelName){
		wm = new WorldMap(world, true, "test1");
		wm.setBounds();
	}
	
	public void loadEntity(ArrayList<IEntityModel> bodies)throws SlickException{
		this.bodies = bodies;
		for(IEntityModel b: bodies){
			if(b instanceof MovingFoeModel){
				if(((MovingFoeModel)b).getWeapon().getWeaponType() == WeaponType.gun)
					controllers.add(new GunController((GunModel) ((MovingFoeModel)b).getWeapon()));
				else
					controllers.add(new SwordController((SwordModel) ((MovingFoeModel)b).getWeapon()));
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
	
	public void loadHero(String heroName, Vec2 pos, AbstractWeaponModel awm) throws SlickException{
		hero = new HeroModel(world ,heroName, pos, awm);
		contHero = new HeroController(hero, this);
		if(hero.canLoadBody())
			hero.createNewHero(wm.getHeroPosition(), awm);
		else
			throw new SlickException("Unable to load Hero");
	}
	
	public void reTry(){
		//removeAllEntity();
		wm.destroyWorld();
		wm.loadMapFromTMX(wm.getMapName());
		hero.resurrection(wm.getHeroPosition());
		camera.updateCamera(hero.getFrontPosPixels());
		endGame = false;
	}
	
	public void newGame(String level, String name, AbstractWeaponModel herosWeapon){
		try {
			loadWorld(level);
			loadEntity(wm.getListOfBodies());
			loadHero(name, wm.getHeroPosition(), herosWeapon);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Creating the world
		Vec2 gravity = new Vec2(0.0f, 9.8f);
		world = new World(gravity);
		world.setAllowSleep(true);
		world.setContinuousPhysics(true);
		cd = new CollisionDetection();
		world.setContactListener(cd);
		
		GunModel gm = new GunModel(world, 500, 10, 10, 56);

		newGame("test1", "BluePants", gm);
		// Camera
		camera = new Camera(gc.getWidth(), gc.getHeight(), 
				wm.getWorldWidth(), wm.getWorldHeight(), 
				new Rectangle(300,200), hero.getPosPixels());
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(new Image("res/Map/Background-game.png"), 0, 0);
		wm.render(g, (int)camera.getCameraPosition().x, (int)camera.getCameraPosition().y, gc.getWidth(), gc.getHeight());

		try{
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).render(gc, sbg, g);
			}
			contHero.render(gc, sbg, g);
		}catch(NullPointerException e){
		}
		try{
			g.drawString(" BodyCount" + world.getBodyCount() +"\n HeroModelBody " + hero.getBody() + 
				"\n Force:" + hero.getBody().m_linearVelocity.y, 100, 100);
		} catch(NullPointerException e){
		}
				
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		if(hero.isDead()){
			this.pauseUpdate();
			endGameDelay.start();
		}
		if(endGame){
			sbg.enterState(GameApp.GAMEOVERSTATE);
		}
		try{
			camera.updateCamera(hero.getFrontPosPixels());
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).update(gc, sbg, delta);
			}
			contHero.update(gc, sbg, delta);
		}catch(NullPointerException e){}
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
	
	public void removeBullet(){
		for(GunModel gm: gunThatsActive){
			gm.isDone();
		}
	}
	
	public void removeAllEntity(){
		for(IEntityController iec: controllers){
			controllers.remove(iec);
		}
		for(IEntityModel iem: bodies){
			world.destroyBody(iem.getBody());
			bodies.remove(iem);
		}
		for(GunModel gm: gunThatsActive){
			bodies.remove(gm);
		}
	}
	
	public void removeEntity(int id){
		int j = 0;
		for(int i = 0; i < controllers.size(); i++){
			if(((IEntityController)controllers.get(i)).getID() == id){
				++j;
				if(controllers.get(i) instanceof GunController){
					--j;
				}else
					controllers.remove(i);
				if(j > 0){
					j = 0;
					break;
				}
			}
		}
		for(int i = 0; i < bodies.size(); i++){
			if(((IEntityModel)bodies.get(i)).getID() == id){
				if((bodies.get(i) instanceof MovingFoeModel) && ((MovingFoeModel)bodies.get(i)).getWeapon().getWeaponType() == WeaponType.gun){
					gunThatsActive.add((GunModel)((MovingFoeModel)bodies.get(i)).getWeapon());
					break;
				}else{
					bodies.remove(i);
					break;
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		endGame = true;
		this.unpauseUpdate();
		endGameDelay.stop();
		
	}
}
