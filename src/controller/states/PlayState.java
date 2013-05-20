package controller.states;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import map.WorldMap;
import model.AbstractCollectibleModel;
import model.AbstractWeaponModel;
import model.GunModel;
import model.HeroModel;
import model.IEntityModel;
import model.MovingFoeModel;
import model.StaticFoeModel;
import model.SwordModel;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Camera;
import utils.Controls;
import utils.SoundType;
import utils.Sounds;
import utils.WeaponType;
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
	
	private World world;
	private HeroModel heromodel;
	private HeroController herocontroller;
	private WorldMap worldmap;
	private CollisionDetection collisiondetection;
	private int stateID;
	private Camera camera;
	private Timer endGameDelay = new Timer(2000, this);
	private boolean endGame = false;
	
	/* This list contains all the bodies in the world*/
	ArrayList<IEntityModel> bodies = new ArrayList <IEntityModel>();
	/* This list contains all the controllers for the bodies in the list above*/
	ArrayList<IEntityController> controllers = new ArrayList<IEntityController>();
	/* This list contains all the guns without any Entity
	 * (the entity has died before the bullet has transport the distance)*/
	ArrayList<GunModel> gunThatsActive= new ArrayList<GunModel>();
	
	public PlayState(int id){
		stateID = id;
	}
	/**
	 * This method load everything in world.
	 * @param level
	 * @param name
	 * @param herosWeapon
	 */
	public void newGame(String level, String name, AbstractWeaponModel herosWeapon){
		try {
			loadWorld(level);
			loadEntity(worldmap.getListOfBodies());
			loadHero(name, worldmap.getHeroPosition(), herosWeapon);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void loadWorld(String levelName){
		worldmap = new WorldMap(world, true, "test1");
		worldmap.setBounds();
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
		heromodel = new HeroModel(world ,heroName, pos, awm);
		herocontroller = new HeroController(heromodel);
		if(heromodel.canLoadBody())
			heromodel.createNewHero(worldmap.getHeroPosition(), awm);
		else
			throw new SlickException("Unable to load Hero");
	}
	
	/**
	 * You should call this method when you want to restart a Level 
	 */
	public void reTry(){
		endGame = false;
		removeAllEntity();
		worldmap.destroyWorld();
		worldmap.loadMapFromTMX(worldmap.getMapName());
		worldmap.setBounds();
		heromodel.resurrection(worldmap.getHeroPosition());
		try {
			this.loadEntity(worldmap.getListOfBodies());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		camera.resetCamera();
		camera.updateCamera(heromodel.getFrontPosPixels());
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Sounds.getInstance().playSound(SoundType.GAME_MUSIC);
		if(endGame)
			reTry();
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Creating the world
		Vec2 gravity = new Vec2(0.0f, 9.8f);
		world = new World(gravity);
		world.setAllowSleep(true);
		world.setContinuousPhysics(true);
		collisiondetection = new CollisionDetection();
		world.setContactListener(collisiondetection);
		
		GunModel gm = new GunModel(world, 500, 10, 10, 56);

		newGame("test1", "BluePants", gm);
		// Camera
		camera = new Camera(gc.getWidth(), gc.getHeight(), 
				worldmap.getWorldWidth(), worldmap.getWorldHeight(), 
				new Rectangle(300,200), heromodel.getPosPixels());
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(new Image("res/Map/Background-game.png"), 0, 0);
		worldmap.render(g, (int)camera.getCameraPosition().x, (int)camera.getCameraPosition().y, gc.getWidth(), gc.getHeight());

		try{
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).render(gc, sbg, g);
			}
			herocontroller.render(gc, sbg, g);
		}catch(NullPointerException e){}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		if(heromodel.isDead() && !endGame){
			this.pauseUpdate();
			endGameDelay.start();
		}
		if(endGame){
			sbg.enterState(GameApp.GAMEOVERSTATE);
		}
		try{
			camera.updateCamera(heromodel.getFrontPosPixels());
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).update(gc, sbg, delta);
			}
			herocontroller.update(gc, sbg, delta);
		}catch(NullPointerException e){}
		if(Controls.getInstance().check("pause")){
			this.pauseUpdate();
		}
	}

	public int getID() {
		return stateID;
	}
	
	public HeroModel getHeroModel(){
		return heromodel;
	}
	
	public void removeBullet(){
		for(GunModel gm: gunThatsActive){
			if(gm.isDone()){
				removeEntity(gm.getID());
				gunThatsActive.remove(gm);
			}
		}
	}
	
	public void removeAllEntity(){
		for(int i = controllers.size()-1; i >= 0; i--){
			controllers.remove(0);
		}
		for(int i = bodies.size()-1; i >= 0; i--){
			world.destroyBody(bodies.get(i).getBody());
			bodies.remove(i);
		}
		for(int i = gunThatsActive.size()-1; i >= 0; i--){
			gunThatsActive.remove(i);
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		endGame = true;
		this.unpauseUpdate();
		endGameDelay.stop();
	}
	@Override
	public HeroController getHeroController() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void removeHero() {
		// TODO Auto-generated method stub
		
	}
}
