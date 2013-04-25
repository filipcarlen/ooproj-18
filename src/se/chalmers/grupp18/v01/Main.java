package se.chalmers.grupp18.v01;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
	
	public static void main(String []args){
		AppGameContainer appgc;
		try{
			appgc = new AppGameContainer(new GameApp("The Game"));
			appgc.setDisplayMode(900, 600, false);
			appgc.setTargetFrameRate(100);
			appgc.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
}
