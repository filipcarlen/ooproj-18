package controller.states;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import map.WorldMap;
import model.*;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.*;
import view.PlayStateView;
import controller.*;

public class PlayState extends BasicGameState implements IPlayStateController, ActionListener{
	
	private static PlayState instance;
	private int stateID;
	
	private World world;
	private WorldMap worldMap;
	private Camera camera;
	private PlayStateView playstateview;
	
	private Hero hero;
	private HeroController heroController;
	
	/* This two variables is use to end the game with a delay*/
	private Timer endGameDelay = new Timer(2000, this);
	private boolean endGame = false;
	
	private Image background;
	
	/* This list contains all the bodies in the world*/
	private ArrayList<IEntity> bodies = new ArrayList <IEntity>();
	/* This list contains all the controllers for the bodies in the list above*/
	private ArrayList<IEntityController> controllers = new ArrayList<IEntityController>();
	
	/* This list contains all the guns without any Entity
	 * (the entity has died before the bullet has transport the distance)*/
	private ArrayList<Gun> gunThatsActive= new ArrayList<Gun>();
	
	private PlayState(int id){
		stateID = id;
	}
	
	public static PlayState getInstance(){
		if(instance == null)
			instance = new PlayState(GameApp.PLAYS_STATE);
		return instance;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Creating the world and the CollisionDetection
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
		// Loading the world and creating the bodies, enemies and hero
		newGame("level1", "bluepants");
		playstateview = new PlayStateView(hero);
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException{
		Sounds.getInstance().stopMusic();
		Sounds.getInstance().playMusic(SoundType.GAME_MUSIC);
		hero.continueUseHero(hero.getPosMeters());
		if(endGame){
			reTry();
		}
		camera = new Camera(gc.getWidth(), gc.getHeight(), 
				worldMap.getWorldWidth(), worldMap.getWorldHeight(), 
				new Rectangle((int)((gc.getWidth()*0.3)/2), (int)((gc.getHeight()*0.3)/2)), hero.getPosPixels());
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		background.draw(0, 0, gc.getWidth(), gc.getHeight());
		worldMap.render(g, (int)camera.getCameraPosition().x, (int)camera.getCameraPosition().y, gc.getWidth(), gc.getHeight());
		try{
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).render(gc, sbg, g);
			}
			heroController.render(gc, sbg, g);
		}catch(NullPointerException e){}
		playstateview.render(gc, sbg, g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		world.step(1f/60f, 8, 3);
		if(hero.isDead() && !endGame){
			this.pauseUpdate();
			endGameDelay.start();
		}
		if(endGame){
			sbg.enterState(GameApp.GAME_OVER_STATE);
		}
		try{
			camera.updateCamera(hero.getFrontPosPixels());
			for(int i = 0; i < controllers.size(); i++){
				controllers.get(i).update(gc, sbg, delta);
			}
			heroController.update(gc, sbg, delta);
		}catch(NullPointerException e){}
		if(Controls.getInstance().check("pause")){
			sbg.enterState(GameApp.PAUSE_STATE);
		}
		
		// this if-statement checks if the hero is inside the Goallocation,
		// and if you got the right score to finish the game
		if(worldMap.isInGoalArea(hero.getPosMeters())){
			if(hero.getScore() > 180)
				sbg.enterState(GameApp.GAME_OVER_STATE);			//Finish the game
			else
				playstateview.hasReachedEnd();					// Calls the View to print that you doon't have enough score
		}
	}

	/**
	 * This method load everything in world.
	 * @param level
	 * @param name
	 * @param herosWeapon
	 */
	public void newGame(String level, String heroName){
		try {
			loadWorld(level);
			loadEntity(worldMap.getListOfBodies());
			loadHero(heroName, worldMap.getHeroPosition());
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

	/**
	 * Loads the map from a TiledMap and setBounds so you can't go outside the map
	 * @param levelName
	 */
	public void loadWorld(String levelName){
		worldMap = new WorldMap(world, true, levelName);
		worldMap.setBounds();
	}
	
	/**
	 * This method gives every entity a Controller and loads the bodies
	 * @param bodies
	 * @throws SlickException
	 */
	public void loadEntity(ArrayList<IEntity> bodies)throws SlickException{
		this.bodies = bodies;
		for(IEntity b: bodies){
			if(b instanceof MovingFoe){
				if(((MovingFoe)b).getWeapon().getWeaponType() == WeaponType.GUN)
					controllers.add(new GunController((Gun) ((MovingFoe)b).getWeapon()));
				else
					controllers.add(new SwordController((Sword) ((MovingFoe)b).getWeapon()));
				controllers.add(new MovingFoeController((MovingFoe)b, this));
			}else if(b instanceof AbstractStaticFoe){
				controllers.add(new StaticFoeController((AbstractStaticFoe)b));
			}else if(b instanceof ICollectible){
				controllers.add(new CollectibleController((ICollectible) b, this));
			}else{
				throw new SlickException("Couldn't load the entity " + b.toString());
			}
		}
	}
	
	public void loadHero(String heroName, Vec2 pos) throws SlickException{
		hero = new Hero(world ,heroName, pos);
		heroController = new HeroController(hero);
	}
	
	public Hero getHeroModel(){
		return hero;
	}
	
	/**
	 * This gives the Hero a Weapon and sets the Animation of the weapon
	 * @param wt
	 */
	public void setWeaponInUse(WeaponType wt){
		AbstractWeapon heroWeapon;
		if(wt == WeaponType.GUN){
			heroWeapon =new Gun(world, 500, 20, 15, 1337);
		}else if(wt ==WeaponType.SWORD){
			heroWeapon = new Sword(world, 200, 40, 1337);
		}else{
			return;
		}
		hero.setWeapon(heroWeapon);
		heroController.updateWeaponAnimation();
	}

	/**
	 * This method is to Remove all the GunModels that has a owner that has died, 
	 * but the bullet is still active. 
	 * When the bullet has traveled the distance the gunmodel will be removed
	 */
	public void removeGun(){
		for(Gun gm: gunThatsActive){
			if(gm.isDone()){
				removeEntity(gm.getID());
				gunThatsActive.remove(gm);
			}
		}
	}
	
	/**
	 * This Method removes every enemy and gunsmodels that is active in the world.
	 * this is used when you want to create a new track and new to remove the old map/ entities.
	 */
	public void removeAllEntities(){
		controllers.clear();
		gunThatsActive.clear();
		for(int i = bodies.size()-1; i >= 0; i--){
			world.destroyBody(bodies.get(i).getBody());
			bodies.remove(i);
		}
	}
	
	/**
	 * This Method removes a specified Entity and it's controller and the Weapon if it's not in use
	 */
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
			if(((IEntity)bodies.get(i)).getID() == id){
				if((bodies.get(i) instanceof MovingFoe) && ((MovingFoe)bodies.get(i)).getWeapon().getWeaponType() == WeaponType.GUN){
					gunThatsActive.add((Gun)((MovingFoe)bodies.get(i)).getWeapon());
					break;
				}else{
					bodies.remove(i);
					break;
				}
			}
		}
	}
	
	public int getID() {
		return stateID;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		endGame = true;
		this.unpauseUpdate();
		endGameDelay.stop();
	}
}
