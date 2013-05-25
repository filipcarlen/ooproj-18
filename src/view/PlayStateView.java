package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import model.Hero;

public class PlayStateView implements ActionListener {

	private Image healthbar, statusbar;
	private Hero hero;
	private AngelCodeFont acfw,acfb;
	private Timer timer = new Timer (1500, this);
	private final String not_in_goal = "You need 180 score to finish this Level. \nYou got ";
	
	public PlayStateView(Hero hero){
		this.hero = hero;
		try{
			healthbar = new Image("res/map/healthbar.png");
			statusbar = new Image("res/map/statusbar.png");
		}catch(SlickException e){
			e.printStackTrace();
		}
		try {
			acfw = new AngelCodeFont("res/font/fontwhite.fnt", "res/font/fontwhite_0.png");
			acfb = new AngelCodeFont("res/font/font.fnt", "res/font/font_0.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public void update(Hero hm, GameContainer gc) {

	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)throws SlickException {
		statusbar.draw(0, 0);
		healthbar.draw(3, 70, hero.getHp()/(float)hero.getMaxHp()*healthbar.getWidth(), healthbar.getHeight());
		acfw.drawString(10, 16, hero.getPlayerName());
		acfw.drawString(300, 10, "Score: " + hero.getScore());
		if(timer.isRunning()){
			acfb.drawString((gc.getWidth()/2)-(acfb.getWidth(not_in_goal)/2), gc.getHeight()/2 -100, not_in_goal + hero.getScore());
		}
	}
	
	public void hasReachedEnd(){
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.stop();
	}
		
}
