package se.chalmers.grupp18.v01;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PlayState extends BasicGameState{
	
	World world;
	CharacterEntity hero;
	CharacterController contHero;
	CharacterAnimation pa;
	Body ground;
	
	public PlayState(int id){
		
	}
	
	public void createGround(){
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(0, 600/50);
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(1000, 0);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.5f;
		fd.density = 1f;
		ground = world.createBody(b);
		ground.createFixture(fd);
	}
	
	public void createGround1(){
		BodyDef b = new BodyDef();
		b.type = BodyType.STATIC;
		b.position.set(0, 300/50);
		
		//Creating the structure
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(1000, 0);
		
		//The Fixture
		FixtureDef fd = new FixtureDef();
		fd.shape = pg;
		fd.friction = 0.5f;
		fd.density = 1f;
		ground = world.createBody(b);
		ground.createFixture(fd);
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// Creating the world
		Vec2 gravity = new Vec2(0.0f, 9.8f);
		world = new World(gravity);
		world.step(1f/60f, 8, 3);
		world.setAllowSleep(true);
		world.setContinuousPhysics(false);
		// Creating a character
		hero = new CharacterEntity(world);
		contHero = new CharacterController(hero, "Hero");
		//Create a World
		createGround();
		createGround1();
		// Camera
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		contHero.render(gc, sbg, g);
		g.drawString("Force: " + hero.body.getWorld().CLEAR_FORCES, 100, 100);
		Vec2 groundV = ground.getPosition().mul(50);
		g.fillRect(groundV.x, groundV.y, 1000, -10);
		g.fillRect(groundV.x, groundV.y, 1000, 10);
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
	
}
