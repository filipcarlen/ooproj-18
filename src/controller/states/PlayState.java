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
import model.ICollectibleModel;
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
import view.PlayStateView;
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
	private HeroModel hero;
	private HeroController heroController;
	private WorldMap worldMap;
	private PlayStateView playstateview;
	private int stateID;
	private Camera camera;
	private Timer endGameDelay = new Timer(2000, this);
	private boolean endGame = false;
	private static PlayState instance;
	private Image background;
	
	/* This list contains all the bodies in the world*/
	private ArrayList<IEntityModel> bodies = new ArrayList <IEntityModel>();
	/* This list contains all the controllers for the bodies in the list above*/
	private ArrayList<IEntityController> controllers = new ArrayList<IEntityController>();
	/* This list contains all the guns without any Entity
	 * (the entity has died before the bullet has transport the distance)*/
	private ArrayList<GunModel> gunThatsActive= new ArrayList<GunModel>();
	
	private PlayState(int id){
		stateID = id;
	}
	
	public static PlayState getInstance(){
		if(instance == null)
			instance = new PlayState(GameApp.PLAYSTATE);
		return instance;
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Sounds.getInstance().playSound(SoundType.GAME_MUSIC);
		camera = new Camera(gc.getWidth(), gc.getHeight(), 
				worldMap.getWorldWidth(), worldMap.getWorldHeight(), 
				new Rectangle((int)((gc.getWidth()*0.3)/2), (int)((gc.getHeight()*0.3)/2)), hero.getPosPixels());
		if(endGame){
			reTry();
		}
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Creating the world
		Vec2 gravity = new Vec2(0.0f, 9.8f);
		world = new World(gravity);
		world.setAllowSleep(true);
		world.setContinuousPhysics(true);
		CollisionDetection collisionDetection = new CollisionDetection();
		world.setContactListener(collisionDetection);
		try {
			background = new Image("res/map/background-game.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		GunModel gm = new GunModel(world, 500, 10, 10, 56);
	
		SwordModel sm = new SwordModel(world, 300, 40, 1337);
		
		
		newGame("level1", "BluePants", gm);
		// Camera
		playstateview = new PlayStateView(hero);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		worldMap.render(g, (int)camera.getCameraPosition().x, (int)camera.getCameraPosition().y, gc.getWidth(), gc.getHeight());
		playstateview.render(gc, sbg, g);
		try{
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).render(gc, sbg, g);
			}
			heroController.render(gc, sbg, g);
		}catch(NullPointerException e){}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		if(hero.isDead() && !endGame){
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
			heroController.update(gc, sbg, delta);
		}catch(NullPointerException e){}
		if(Controls.getInstance().check("pause")){
			//sbg.enterState(GameApp.PAUSESTATE);
		}
		
		if(worldMap.isInGoalArea(hero.getPosMeters())){
			if(hero.getScore() > 180)
				sbg.enterState(GameApp.GAMEOVERSTATE);
			else
				playstateview.hasReachedEnd();
		}
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
			loadEntity(worldMap.getListOfBodies());
			loadHero(name, worldMap.getHeroPosition(), herosWeapon);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * You should call this method when you want to restart a Level 
	 */
	public void reTry(){
		endGame = false;
		removeAllEntities();
		worldMap.destroyWorld();
		worldMap.loadMapFromTMX(worldMap.getMapName());
		worldMap.setBounds();
		hero.resurrection(worldMap.getHeroPosition());
		try {
			this.loadEntity(worldMap.getListOfBodies());
		} catch (SlickException e) {
			e.printStackTrace();
		}
		camera.resetCamera();
	}

	public void loadWorld(String levelName){
		worldMap = new WorldMap(world, true, levelName);
		worldMap.setBounds();
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
			}else if(b instanceof ICollectibleModel){
				controllers.add(new CollectibleController((ICollectibleModel) b, this));
			}else{
				throw new SlickException("Couldn't load the entity " + b);
			}
		}
	}
	
	public void loadHero(String heroName, Vec2 pos, AbstractWeaponModel awm) throws SlickException{
		hero = new HeroModel(world ,heroName, pos, awm);
		heroController = new HeroController(hero);
		if(hero.canLoadBody())
			hero.createNewHero(worldMap.getHeroPosition(), awm);
		else
			throw new SlickException("Unable to load Hero");
	}
	
	public int getID() {
		return stateID;
	}
	
	public HeroModel getHeroModel(){
		return hero;
	}
	
	public void removeGun(){
		for(GunModel gm: gunThatsActive){
			if(gm.isDone()){
				removeEntity(gm.getID());
				gunThatsActive.remove(gm);
			}
		}
	}
	
	public void removeAllEntities(){
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
}
