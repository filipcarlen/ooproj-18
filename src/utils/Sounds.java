package utils;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds{
	
private static Sounds instance = null;
	
	public static String PATH = "res/Sounds/";
		
	private float volumeMusic = 1.0f;
	private boolean volumeMusicOn = true;
	private float volumeSound = 1.0f;
	private boolean volumeSoundOn = true;
	
	private float mute = 0.0f;
		
	private Sound soundtrack;
	private Sound gunfire;
	private Sound enemyHurt;
	private Sound enemyDie;
	private Sound musicInGame;
	private Sound hurt;
	private Sound collectCoin;
	private Sound collectGem;
	private Sound die;
		
	private Sounds() throws SlickException {
		soundtrack = new Sound(PATH+"soundtrack.wav");
		gunfire = new Sound(PATH+"GE_KF7_Soviet.wav");
		enemyDie = new Sound(PATH+"enemyDie.wav");
		musicInGame = new Sound(PATH+"Musicingame.wav");
		hurt = new Sound(PATH+"hurt.wav");
		collectCoin = new Sound(PATH+"collect_coin.wav");
		//collectGem = new Sound(PATH+"collect_gem.wav");
		die = new Sound(PATH+"die.wav");
		
	}
		
	public static Sounds getInstance() throws SlickException{
		if(instance == null){
			instance = new Sounds();
		}
		return instance;
	}
	
	public void playMusic(SoundType music){
		float volumeMusic;
		
		if(volumeMusicOn)
			volumeMusic = this.volumeMusic;
		else
			volumeMusic = this.mute;
		switch(music){
		
		case MENU_MUSIC:
			soundtrack.loop(1, volumeMusic);
			break;
			
		case GAME_MUSIC:
			musicInGame.loop(1, volumeMusic);
			break;
			
		default:
			break;	
		}
	}
	
	public void playSound(SoundType sound){
		float volumeSound;
		
		if(volumeSoundOn)
			volumeSound = this.volumeSound;
		else
			volumeSound = this.mute;
		
		switch(sound){
		
		case GUN:
			gunfire.play(1, volumeSound);
			break;
			
		case DIE:
			die.play(1,volumeSound);
			break;
			
		case HURT:
			hurt.play(1, volumeSound);
			break;
		
		case ENEMY_DIE:
			enemyDie.play(1, volumeSound);
			break;
		
		case ENEMY_HURT:
			enemyHurt.play(1, volumeSound);
			break;
			
		case COLLECT_COIN:
			collectCoin.play(1,volumeSound);
			break;
			
		case COLLECT_GEM:
			collectGem.play(1,volumeSound);
			break;
			
		default:
			break;
			
		}	
	}
	
	public void setVolumeMusic(float volumeMusic){
		this.volumeMusic = volumeMusic;
		updateMusic();
	}
	
	public void setVolumeSound(float volumeSound){
		this.volumeSound = volumeSound;
	}
	
	public boolean isMusicOn(){
		return this.volumeMusicOn;
	}
	
	public boolean isSoundOn(){
		return this.volumeSoundOn;
	}
	
	public void setMusicOn(boolean on){
		this.volumeMusicOn = on;
		if(this.volumeMusicOn){
			updateMusic();
		}	
	}
	
	public void setSoundOn(boolean on){
		this.volumeSoundOn = on;
	}
	
	public void updateMusic(){
		if(soundtrack.playing()){
			soundtrack.stop();
			playMusic(SoundType.MENU_MUSIC);
		}
		if(musicInGame.playing()){
			musicInGame.stop();
			playMusic(SoundType.GAME_MUSIC);
		}
	}
	
	public void stopMusic(){
		soundtrack.stop();
		musicInGame.stop();
	}
}
