package utils;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Class holding and handling all sounds used in the game
 * @author Filip Carlén
 */
public class Sounds{
	
	private static Sounds instance = null;
	
	private static final String PATH = "res/sounds/";
		
	private float volumeMusic = 1.0f;
	private boolean volumeMusicOn = true;
	private float volumeSound = 1.0f;
	private boolean volumeSoundOn = true;
	
	private float mute = 0.0f;
	private float pitch = 1f;
		
	private Sound musicInMenus, musicInGame, musicYouLose, musicYouWin, gunShot, foeHurt, foeDie,
				heroHurt, collectCoin, collectGem, heroDie,
				swordSwing, collectChocolateBar, collectEnergyDrink;
		
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
			collectChocolateBar = new Sound(PATH+"chocolate_bar.wav");
			collectEnergyDrink = new Sound(PATH+"energy_drink.wav");
		
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
			if(!musicInMenus.playing()){
				stopMusic();
				musicInMenus.loop(pitch, volumeMusic);
			}
			break;
			
		case GAME_MUSIC:
			if(!musicInGame.playing()){
				stopMusic();
				musicInGame.loop(pitch, volumeMusic);
			}
			break;
			
		case YOU_WIN_MUSIC:
			if(!musicYouWin.playing()){
				stopMusic();
				musicYouWin.loop(pitch, volumeMusic);
			}
			break;
			
		case YOU_LOSE_MUSIC:
			if(!musicYouLose.playing()){
				stopMusic();
				musicYouLose.loop(pitch, volumeMusic);
			}
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
			collectEnergyDrink.play(pitch,volumeSound);
			break;
			
		case CHOCOLATE_BAR:
			collectChocolateBar.play(pitch,volumeSound);
			break;
			
		default:
			break;
			
		}	
	}
	
	public void stopMusic(){
		musicInMenus.stop();
		musicInGame.stop();
		musicYouLose.stop();
		musicYouWin.stop();
	}
	
	public void setVolumeMusic(float volumeMusic){
		this.volumeMusic = volumeMusic;
		updateMusic();
	}
	
	public void setVolumeSound(float volumeSound){
		this.volumeSound = volumeSound;
	}
	
	/**
	 * Check if current music is playing
	 * @return boolean volumeMusicOn
	 */
	public boolean isMusicOn(){
		return this.volumeMusicOn;
	}
	
	/**
	 * Check if current sound is playing
	 * @return boolean volumeSoundOn;
	 */
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
	
	/**
	 * Update music with new sound level.
	 */
	public void updateMusic(){
		if(musicInMenus.playing()){
			musicInMenus.stop();
			playMusic(SoundType.MENU_MUSIC);
		}
		if(musicInGame.playing()){
			musicInGame.stop();
			playMusic(SoundType.GAME_MUSIC);
		}
		if(musicYouWin.playing()){
			musicYouWin.stop();
			playMusic(SoundType.YOU_WIN_MUSIC);
		}
		if(musicYouLose.playing()){
			musicYouLose.stop();
			playMusic(SoundType.YOU_LOSE_MUSIC);
		}
	}
	
	
	
	public float getSoundVolume(){
		return this.volumeSound;
	}
	
	public float getMusicVolume(){
		return this.volumeMusic;		
	}
}
