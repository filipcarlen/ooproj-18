package se.chalmers.grupp18.v02;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import it.marteEngine.Camera;
import it.marteEngine.CameraFollowStyle;
import it.marteEngine.World;


public class Level extends World{

	private Player hero;
	
	public Level(int id, GameContainer container) {
		super(id, container);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		
		hero = new Player(200,19800);
		add(hero,World.GAME);
		
		for(int i = 0; i<200000 ; i = i+33){
			Wall wall = new Wall(i,19940);
			if(!(i==99 || i==132)){
				add(wall);
			}
		}
		
		for(int i = 100; i<400 ; i = i+33){
			Wall wall = new Wall(i,450);
			//wall.rotateImage();
			add(wall);
		}
		
		//System.out.println(this.duration);
		
		this.camera = new Camera(1920, 1200, 20000, 20000);
		this.camera.follow(hero, CameraFollowStyle.LOCKON);
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
	}

}
