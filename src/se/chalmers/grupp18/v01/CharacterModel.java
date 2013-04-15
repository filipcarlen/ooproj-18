package se.chalmers.grupp18.v01;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class CharacterModel extends BasicGame{
	
	private Image background;
	private Image character;
	
	private float xpos,ypos;
	private int hp;
	private Weapon weapon;
	
	public CharacterModel(){
		super("Test");
		this.hp = 100;
		this.weapon = new Sword(20);
	}
	
	public CharacterModel(int hp) throws SlickException{
		this();
		
		if(hp > 0 && hp <= 100){
			this.hp = hp;
		} else {
			throw new SlickException("Character can't be initiated dead or too healthy.");
		}
	}
	
	public CharacterModel(int hp, Weapon weapon) throws SlickException{
		this(hp);
		this.weapon = weapon;
	}
	
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public void fight(){
		this.weapon.fight();
	}
	
	public void setXpos(float xpos){
		this.xpos = xpos;
	}
	
	public void setYpos(float ypos){
		this.ypos = ypos;
	}
	
	public float getXpos(){
		return this.xpos;
	}
	
	public float getYpos(){
		return this.ypos;
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		background.draw(0, 0);
		character.draw(this.xpos, this.ypos, 0.1f);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		this.xpos = 0;
		this.ypos = 1000;
		background = new Image("res/testbackground.png");
		character = new Image("res/testcharacter.png");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
 
        if(input.isKeyDown(Input.KEY_LEFT))
        {
            float hip = 0.4f * delta;
 
            this.xpos-= hip;
        }
        
        if(input.isKeyDown(Input.KEY_RIGHT))
        {
        	float hip = 0.4f * delta;
 
            this.xpos+= hip;
        }
	}
	
	public static void main(String[] args) throws SlickException{
		AppGameContainer app =
			new AppGameContainer( new CharacterModel() );
 
         app.setDisplayMode(1920, 1200, false);
         app.start();
	}
	
}
