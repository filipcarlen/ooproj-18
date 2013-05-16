package utils;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds{
	
	private static Sounds instance = null;
	
		public static String PATH = "res/Sounds/";
		
		public Sound soundtrack;
		public Sound gunfire;
		public Sound enemyHurt;
		public Sound enemyDie;
		public Sound musicIngame;
		
		private Sounds() throws SlickException {
			soundtrack = new Sound(PATH+"soundtrack.wav");
			gunfire = new Sound(PATH+"GE_KF7_Soviet.wav");
			enemyHurt = new Sound(PATH+"enemyhurt.wav");
			enemyDie = new Sound(PATH+"enemyDie.wav");
			musicIngame = new Sound(PATH+"Musicingame.wav");
		}
		
		public static Sounds getInstance() throws SlickException{
			if(instance == null){
				instance = new Sounds();
			}
			return instance;
		}
}
