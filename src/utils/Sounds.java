package utils;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds{
	
private static Sounds instance = null;
	
	private static final String PATH = "res/sounds/";
		
	private float volumeMusic = 1.0f;
	private boolean volumeMusicOn = true;
	private float volumeSound = 1.0f;
	private boolean volumeSoundOn = true;
	
	private float mute = 0.0f;
	private float pitch = 1f;
		
	private Sound musicInMenus;
	private Sound musicInGame;
	private Sound musicYouLose;
	private Sound musicYouWin;
	private Sound gunShot;
	private Sound foeHurt;
	private Sound foeDie;
	private Sound heroHurt;
	private Sound collectCoin;
	private Sound collectGem;
	private Sound heroDie;
	private Sound swordSwing;
	private Sound chocolateBar;
	private Sound energyDrink;
		
	private Sounds() {
		try{
			musicInMenus = new Sound(PATH+"music_in_menus.wav");
			musicInGame = new Sound(PATH+"music_in_game.wav");
			musicYouLose = new Sound(PATH+"music_you_lose.wav");
			musicYouWin = new Sound(PATH+"music_you_win.wav");
			gunShot = new Sound(PATH+"gun_shot.wav");
			foeDie = new Sound(PATH+"foe_die.wav");
			heroHurt = new Sound(PATH+"hero_hurt.wav");
			collectCoin = new Sound(PATH+"collect_coin.wav");
			collectGem = new Sound(PATH+"collect_gem.wav");
			heroDie = new Sound(PATH+"hero_die.wav");
			foeHurt = new Sound(PATH+"foe_hurt.wav");
			swordSwing = new Sound(PATH+"sword_swing.wav");
			chocolateBar = new Sound(PATH+"chocolate_bar.wav");
			energyDrink = new Sound(PATH+"energy_drink.wav");
		
		} catch(SlickException e){
			System.out.println("Couldn't load sound files in Sounds class.");
		}
		
	}
		
	public static Sounds getInstance(){
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
			musicInMenus.loop(pitch, volumeMusic);
			break;
			
		case GAME_MUSIC:
			musicInGame.loop(pitch, volumeMusic);
			break;
			
		case YOU_WIN_MUSIC:
			musicYouLose.loop(pitch, volumeMusic);
			break;
			
		case YOU_LOSE_MUSIC:
			musicYouWin.loop(pitch, volumeMusic);
			
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
		
		case GUN_SHOT:
			gunShot.play(pitch, volumeSound);
			break;
			
		case HERO_DIE:
			heroDie.play(pitch,volumeSound);
			break;
			
		case HERO_HURT:
			heroHurt.play(pitch, volumeSound);
			break;
		
		case FOE_DIE:
			foeDie.play(pitch, volumeSound);
			break;
		
		case FOE_HURT:
			foeHurt.play(pitch, volumeSound);
			break;
			
		case COLLECT_COIN:
			collectCoin.play(pitch,volumeSound);
			break;
			
		case COLLECT_GEM:
			collectGem.play(pitch,volumeSound);
			break;
			
		case SWORD_SWING:
			swordSwing.play(pitch,volumeSound);
			break;
			
		case ENERGY_DRINK:
			energyDrink.play(pitch,volumeSound);
			break;
			
		case CHOCOLATE_BAR:
			chocolateBar.play(pitch,volumeSound);
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
		if(musicInMenus.playing()){
			musicInMenus.stop();
			playMusic(SoundType.MENU_MUSIC);
		}
		if(musicInGame.playing()){
			musicInGame.stop();
			playMusic(SoundType.GAME_MUSIC);
		}
	}
	
	public void stopMusic(){
		musicInMenus.stop();
		musicInGame.stop();
	}
	
	public float getSoundVolume(){
		return this.volumeSound;
	}
	
	public float getMusicVolume(){
		return this.volumeMusic;		

	}
}
