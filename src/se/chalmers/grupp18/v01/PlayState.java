package se.chalmers.grupp18.v01;

import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState{
	
	World world;
	static CharacterModel hero;
	CharacterController contHero;
	CharacterView pa;
	Body ground, ground1;
	WorldMap wm;
	CollisionDetection cd;
	
	ArrayList<IEntityModel> boddies = new ArrayList <IEntityModel>();
	ArrayList<WorldShapes> terrain = new ArrayList<WorldShapes>();
	ArrayList<Body> terr = new ArrayList<Body>();
	
	public PlayState(int id){
		
	}
	
	public Body createGround(float x, float y){
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(x/ 30f, y/30f);
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(1000, 0);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.0f;
		fd.density = 1f;
		ground = world.createBody(b);
		ground.createFixture(fd);
		
		return ground;
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
		cd = new CollisionDetection(world, boddies, terrain, terr, hero, contHero);
		world.setContactListener(cd);
		//wm = new WorldMap(world, 30f, true, "test");
		// Creating a character
		hero = new CharacterModel(world, "hero", 30f);
		contHero = new CharacterController(hero, "Hero");
		//Create a World
		terr.add(createGround(0 ,600));
		terr.add(createGround(0 ,300));//*/
		// Camera
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		contHero.render(gc, sbg, g);
		g.drawString("Force: " + hero.body.m_force +
				"\nisAwake: " + hero.getBody().isAwake() +
				"\nisActive: " + hero.getBody().isActive() +
				"\nPosition: " + hero.getBody().getPosition(), 100, 100);
		Vec2 groundV = ground.getPosition().mul(30f);
		g.fillRect(groundV.x, groundV.y, 1000, -10);
		g.fillRect(groundV.x, groundV.y, 1000, 10);//*/
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
	
	public static CharacterModel getHero(){
		return hero;
	}


	
}
