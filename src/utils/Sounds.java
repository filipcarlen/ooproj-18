package utils;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds{
	
	private static Sounds instance = null;
	
		public static String PATH = "res/Sounds/";
		
		public Sound soundtrack;
		
		private Sounds() throws SlickException {
			soundtrack = new Sound(PATH+"soundtrack.wav");
		}
		
		public static Sounds getInstance() throws SlickException{
			if(instance == null){
				instance = new Sounds();
			}
			return instance;
		}
}
