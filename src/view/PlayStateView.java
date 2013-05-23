package view;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import model.HeroModel;

public class PlayStateView {

	private Image healthbar, statusbar;
	private HeroModel hero;
	private AngelCodeFont acf;
	
	public PlayStateView(HeroModel hero){
		this.hero = hero;
		try{
			healthbar = new Image("res/map/healthbar.png");
			statusbar = new Image("res/map/statusbar.png");
		}catch(SlickException e){
			e.printStackTrace();
		}
		try {
			acf = new AngelCodeFont("res/font/fontwhite.fnt", "res/font/fontwhite_0.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void update(HeroModel hm, GameContainer gc) {

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		float statusy = gc.getHeight()-statusbar.getHeight();
		statusbar.draw(0, statusy);
		healthbar.draw(3, statusy+103, hero.getHp()/(float)hero.getMaxHp()*healthbar.getWidth(), healthbar.getHeight());
		acf.drawString(10, statusy+50, hero.getName());
		acf.drawString(300, statusy+115, "Score: " + hero.getScore());
	}
}
