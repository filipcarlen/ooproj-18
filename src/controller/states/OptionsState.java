package controller.states;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.SoundType;
import utils.Sounds;

public class OptionsState extends BasicGameState {
	
	private int stateID;
	private final String PATH = "res/OptionsMenu/";
	
	private float arrowSpace = 32, spacing = 20, mouseHandleSpace;
	
	private Image background, options;
	private Vec2 optionsPos;
	
	private Image volume, slider, handle;
	private float sliderStartPoint, handleEndPoint, musicSliderY, soundSliderY, sliderLength, maxVolume, sliderHeight = 10;
	private float arrowLeftX, arrowRightX, musicArrowLeftY, musicArrowRightY, soundArrowLeftY, arrowRadius = 13;
	
	private Image musicOn, musicOff;
	private Vec2 musicPos, musicVolumePos, musicSliderPos, musicHandlePos;
	private boolean insideMusicOn, clickedMusicOn;
	private boolean insideMusicHandle, clickedMusicHandle;
	
	private Image soundOn, soundOff;
	private Vec2 soundPos, soundVolumePos, soundSliderPos, soundHandlePos;
	private boolean insideSoundOn, clickedSoundOn;
	private boolean insideSoundHandle, clickedSoundHandle;
	
	private Image fullscreenOn, fullscreenOff;
	private Vec2 fullscreenPos;
	private boolean insideFullscreenOn, clickedFullscreenOn;
	
	private Image walkRight, walkLeft, jump, fight;
	private Vec2 walkRightPos, walkLeftPos, jumpPos, fightPos;
	
	private Image key, keyH, keyM;
	private Vec2[] keyPos = new Vec2[4];
	private boolean[] insideKeys = new boolean[4], clickedKeys = new boolean[4];
	
	private Image ok, okH;
	private Vec2 okPos;
	private boolean insideOk;
	
	public OptionsState(int stateID){
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		//Initiate all images.
		this.background = new Image("res/Background.png");
		this.options = new Image(PATH + "options.png");
		
		this.volume = new Image(PATH + "volume.png");
		this.slider = new Image(PATH + "slider.png");
		this.handle = new Image(PATH + "handle.png");
		
		this.musicOn = new Image(PATH + "musicOn.png");
		this.musicOff = new Image(PATH + "musicOff.png");
		
		this.soundOn = new Image(PATH + "soundOn.png");
		this.soundOff = new Image(PATH + "soundOff.png");
		
		this.fullscreenOn = new Image(PATH + "fullscreenOn.png");
		this.fullscreenOff = new Image(PATH + "fullscreenOff.png");
		
		this.walkRight = new Image(PATH + "walkRight.png");
		this.walkLeft = new Image(PATH + "walkLeft.png");
		this.jump = new Image(PATH + "jump.png");
		this.fight = new Image(PATH + "fight.png");
		
		this.key = new Image(PATH + "key.png");
		this.keyH = new Image(PATH + "keyH.png");
		this.keyM = new Image(PATH + "keyM.png");
		
		this.ok = new Image(PATH + "ok.png");
		this.okH = new Image(PATH + "okH.png");
		
		this.optionsPos = new Vec2(gc.getWidth()/2 - this.options.getWidth()/2, this.spacing);
		this.okPos = new Vec2(gc.getWidth() - this.spacing*2 - this.ok.getWidth(), gc.getHeight()-this.ok.getHeight()-this.spacing*2);
		
		//Calculates all positions to do with the music options so that they are evenly spaced and centered.
		this.musicPos = new Vec2((gc.getWidth() - this.musicOn.getWidth() - this.volume.getWidth() - this.slider.getWidth() - this.spacing*3)/2, this.optionsPos.y + this.options.getHeight() + this.spacing);
		this.musicVolumePos = new Vec2(this.musicPos.x + this.musicOn.getWidth() + this.spacing*2, this.musicPos.y);
		this.musicSliderPos = new Vec2(this.musicVolumePos.x + this.volume.getWidth() + this.spacing, this.musicPos.y);
		this.musicSliderY = this.musicSliderPos.y + this.slider.getHeight()/2 - this.sliderHeight/2;
		
		//Calculate actual start point, end point of the handle and length of the slider, also calculates the length that represents the max volume.
		this.sliderStartPoint = this.musicSliderPos.x + this.arrowSpace;
		this.handleEndPoint = this.musicSliderPos.x + this.slider.getWidth() - this.arrowSpace - this.handle.getWidth();
		this.sliderLength = (this.handleEndPoint + this.handle.getWidth()) - this.sliderStartPoint;
		this.maxVolume = this.handleEndPoint - this.sliderStartPoint;
		
		float musicVolume = Sounds.getInstance().getMusicVolume();
		this.musicHandlePos = new Vec2(this.sliderStartPoint + musicVolume*this.maxVolume, this.musicSliderPos.y + this.slider.getHeight()/2 - this.handle.getHeight()/2);
		
		//Calculate a number so that all options are evenly spaced.
		float spaceY = (this.okPos.y - this.musicPos.y - this.musicOn.getHeight() - this.soundOn.getHeight() - this.fullscreenOn.getHeight() - this.walkRight.getHeight()*2)/6;
		
		//Calculates all positions to do with the sound options so that they are evenly spaced and centered.
		this.soundPos = new Vec2(this.musicPos.x, this.musicPos.y + this.musicOn.getHeight() + spaceY);
		this.soundVolumePos = new Vec2(this.musicPos.x + this.musicOn.getWidth() + this.spacing*2, this.soundPos.y);
		this.soundSliderPos = new Vec2(this.musicVolumePos.x + this.volume.getWidth() + this.spacing, this.soundPos.y);
		this.soundSliderY = this.soundSliderPos.y + this.slider.getHeight()/2 - this.sliderHeight/2;
		
		float soundVolume = Sounds.getInstance().getSoundVolume();
		this.soundHandlePos = new Vec2(this.sliderStartPoint + soundVolume*this.maxVolume, this.soundSliderPos.y + this.slider.getHeight()/2 - this.handle.getHeight()/2);
		
		//Calculates the position of the full screen option so that it is centered and evenly spaced from the other options.
		this.fullscreenPos = new Vec2(gc.getWidth()/2 - this.fullscreenOn.getWidth()/2, this.soundPos.y + this.soundOn.getHeight() + spaceY*2);
		
		//Calculates all positions to do with changing the key bindings.
		this.walkRightPos = new Vec2((gc.getWidth() - this.walkRight.getWidth() - this.key.getWidth()*2 - this.jump.getWidth() - this.spacing*4)/2, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.keyPos[0] = new Vec2(this.walkRightPos.x + this.walkRight.getWidth() + this.spacing, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.jumpPos = new Vec2(this.keyPos[0].x + this.key.getWidth() + this.spacing*2, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.keyPos[1] = new Vec2(this.jumpPos.x + this.jump.getWidth() + this.spacing, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.walkLeftPos = new Vec2((gc.getWidth() - this.walkLeft.getWidth() - this.key.getWidth()*2 - this.fight.getWidth() - this.spacing*4)/2, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
		this.keyPos[2] = new Vec2(this.walkLeftPos.x + this.walkLeft.getWidth() + this.spacing, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
		this.fightPos = new Vec2(this.keyPos[2].x + this.key.getWidth() + this.spacing*2, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
		this.keyPos[3] = new Vec2(this.fightPos.x + this.fight.getWidth() + this.spacing, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		this.background.draw(0,0);
		this.options.draw(this.optionsPos.x, this.optionsPos.y);
		
		//Draw everything to do with the music option.
		if(Sounds.getInstance().isMusicOn()) {
			if(this.insideMusicOn) {
				if(this.clickedMusicOn) {
					this.musicOn.draw(this.musicPos.x, this.musicPos.y);
				} else {
					this.musicOff.draw(this.musicPos.x, this.musicPos.y);
				}
			} else {
				this.musicOn.draw(this.musicPos.x, this.musicPos.y);
			}
		} else {
			if(this.insideMusicOn) {
				if(this.clickedMusicOn) {
					this.musicOff.draw(this.musicPos.x, this.musicPos.y);
				} else {
					this.musicOn.draw(this.musicPos.x, this.musicPos.y);
				}
			} else {
				this.musicOff.draw(this.musicPos.x, this.musicPos.y);
			}
		}
		
		this.volume.draw(this.musicVolumePos.x, this.musicVolumePos.y);
		this.slider.draw(this.musicSliderPos.x, this.musicSliderPos.y);
		this.handle.draw(this.musicHandlePos.x, this.musicHandlePos.y);
		
		//Draw everything to do with the sound option.
		if(Sounds.getInstance().isSoundOn()) {
			if(this.insideSoundOn) {
				if(this.clickedSoundOn) {
					this.soundOn.draw(this.soundPos.x, this.soundPos.y);
				} else {
					this.soundOff.draw(this.soundPos.x, this.soundPos.y);
				}
			} else {
				this.soundOn.draw(this.soundPos.x, this.soundPos.y);
			}
		} else {
			if(this.insideSoundOn) {
				if(this.clickedSoundOn) {
					this.soundOff.draw(this.soundPos.x, this.soundPos.y);
				} else {
					this.soundOn.draw(this.soundPos.x, this.soundPos.y);
				}
			} else {
				this.soundOff.draw(this.soundPos.x, this.soundPos.y);
			}
		}
		
		this.volume.draw(this.soundVolumePos.x, this.soundVolumePos.y);
		this.slider.draw(this.soundSliderPos.x, this.soundSliderPos.y);
		this.handle.draw(this.soundHandlePos.x, this.soundHandlePos.y);
		
		//Draw everything to do with the full screen option.
		if(gc.isFullscreen()) {
			if(this.insideFullscreenOn) {
				if(this.clickedFullscreenOn) {
					this.fullscreenOn.draw(this.fullscreenPos.x, this.fullscreenPos.y);
				} else {
					this.fullscreenOff.draw(this.fullscreenPos.x, this.fullscreenPos.y);
				}
			} else {
				this.fullscreenOn.draw(this.fullscreenPos.x, this.fullscreenPos.y);
			}
		} else {
			if(this.insideFullscreenOn) {
				if(this.clickedFullscreenOn) {
					this.fullscreenOff.draw(this.fullscreenPos.x, this.fullscreenPos.y);
				} else {
					this.fullscreenOn.draw(this.fullscreenPos.x, this.fullscreenPos.y);
				}
			} else {
				this.fullscreenOff.draw(this.fullscreenPos.x, this.fullscreenPos.y);
			}
		}
		
		//Draw everything to do with the key bindings options.
		this.walkRight.draw(this.walkRightPos.x, this.walkRightPos.y);
		this.walkLeft.draw(this.walkLeftPos.x, this.walkLeftPos.y);
		this.jump.draw(this.jumpPos.x, this.jumpPos.y);
		this.fight.draw(this.fightPos.x, this.fightPos.y);
		
		for(int i = 0; i < this.insideKeys.length; i++){
			if(this.insideKeys[i]) {
				if(this.clickedKeys[i]) {
					this.keyM.draw(this.keyPos[i].x, this.keyPos[i].y);
				} else {
					this.keyH.draw(this.keyPos[i].x, this.keyPos[i].y);
				}
			} else {
				if(this.clickedKeys[i]) {
					this.keyM.draw(this.keyPos[i].x, this.keyPos[i].y);
				} else {
					this.keyH.draw(this.keyPos[i].x, this.keyPos[i].y);
				}
			}
		}
		
		//Draw OK.
		if(this.insideOk) {
			this.okH.draw(this.okPos.x, this.okPos.y);
		} else {
			this.ok.draw(this.okPos.x, this.okPos.y);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		//Temporarily store the input and the mouse coordinates.
		Input input = gc.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		
		this.insideMusicOn = this.mouseInsideImage(mouseX, mouseY, this.musicPos.x, this.musicPos.y, this.musicOn);
		this.insideMusicHandle = this.mouseInsideImage(mouseX, mouseY, this.musicHandlePos.x, this.musicHandlePos.y, this.handle);
		
		this.insideSoundOn = this.mouseInsideImage(mouseX, mouseY, this.soundPos.x, this.soundPos.y, this.soundOn);
		this.insideSoundHandle = this.mouseInsideImage(mouseX, mouseY, this.soundHandlePos.x, this.soundHandlePos.y, this.handle);
		
		this.insideFullscreenOn = this.mouseInsideImage(mouseX, mouseY, this.fullscreenPos.x, this.fullscreenPos.y, this.fullscreenOn);
		
		for(int i = 0; i < this.insideKeys.length; i++){
			this.insideKeys[i] = this.mouseInsideImage(mouseX, mouseY, this.keyPos[i].x, this.keyPos[i].y, this.key);
		}
		
		this.insideOk = this.mouseInsideImage(mouseX, mouseY, this.okPos.x, this.okPos.y, this.ok);
		
		//If the mouse is no longer inside a check box option that was recently clicked, set clicked = false;
		if(!this.insideMusicOn && this.clickedMusicOn) {
			this.clickedMusicOn = false;
		} else if(!this.insideSoundOn && this.clickedSoundOn) {
			this.clickedSoundOn = false;
		} else if(!this.insideFullscreenOn && this.clickedFullscreenOn) {
			this.clickedFullscreenOn = false;
		}
		
		//The music on option i clicked.
		if(this.insideMusicOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Sounds.getInstance().setMusicOn(!Sounds.getInstance().isMusicOn());
			if(Sounds.getInstance().isMusicOn()){
				Sounds.getInstance().stopMusic();
				Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
			} else {
				Sounds.getInstance().stopMusic();
			}
			this.clickedMusicOn = true;
		} //The sound on option is clicked.
		else if(this.insideSoundOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Sounds.getInstance().setSoundOn(!Sounds.getInstance().isSoundOn());
			this.clickedSoundOn = true;
		} //The full screen option is clicked.
		else if(this.insideFullscreenOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.clickedFullscreenOn = true;
		} //The OK button is clicked.
		else if(this.insideOk && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			sbg.enterState(GameApp.MAINMENUSTATE);
		}
		
		for(int i = 0; i < this.insideKeys.length; i++){
			if(this.insideKeys[i] && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				this.clickedKeys[i] = true;
			}
		}
		
		//Calculates the new position for the handle on the music volume slider according to user input and adjusts the music volume accordingly.
		if(this.insideMusicHandle && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.clickedMusicHandle = true;
			this.mouseHandleSpace = mouseX - this.musicHandlePos.x;
		} else if(this.clickedMusicHandle && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX - this.mouseHandleSpace <= this.sliderStartPoint) {
				this.musicHandlePos.x = this.sliderStartPoint;
			} else if(mouseX - this.mouseHandleSpace >= this.handleEndPoint) {
				this.musicHandlePos.x = this.handleEndPoint;
			} else {
				this.musicHandlePos.x = mouseX - this.mouseHandleSpace;
			}
		} else if(this.clickedMusicHandle) {
			this.clickedMusicHandle = false;
			Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.sliderStartPoint)/this.maxVolume);
		} 
		if(this.mouseInsideArea(mouseX, mouseY, this.sliderStartPoint, this.musicSliderY, this.sliderLength, this.sliderHeight) && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX - this.handle.getWidth()/2 <= this.sliderStartPoint) {
				this.musicHandlePos.x = this.sliderStartPoint;
			} else if(mouseX - this.handle.getWidth()/2 >= this.handleEndPoint) {
				this.musicHandlePos.x = this.handleEndPoint;
			} else {
				this.musicHandlePos.x = mouseX - this.handle.getWidth()/2;
			}
			Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.sliderStartPoint)/this.maxVolume);
		}
	
		//Calculates the new position for the handle on the sound volume slider according to user input and adjusts the sound volume accordingly.
		if(this.insideSoundHandle && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.clickedSoundHandle = true;
			this.mouseHandleSpace = mouseX - this.soundHandlePos.x;
		} else if(this.clickedSoundHandle && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX - this.mouseHandleSpace <= this.sliderStartPoint) {
				this.soundHandlePos.x = this.sliderStartPoint;
			} else if(mouseX - this.mouseHandleSpace >= this.handleEndPoint) {
				this.soundHandlePos.x = this.handleEndPoint;
			} else {
				this.soundHandlePos.x = mouseX - this.mouseHandleSpace;
			}
		} else if(this.clickedSoundHandle) {
			this.clickedSoundHandle = false;
			Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.sliderStartPoint)/this.maxVolume);
		} 
		if(this.mouseInsideArea(mouseX, mouseY, this.sliderStartPoint, this.soundSliderY, this.sliderLength, this.sliderHeight) && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX - this.handle.getWidth()/2 <= this.sliderStartPoint) {
				this.soundHandlePos.x = this.sliderStartPoint;
			} else if(mouseX - this.handle.getWidth()/2 >= this.handleEndPoint) {
				this.soundHandlePos.x = this.handleEndPoint;
			} else {
				this.soundHandlePos.x = mouseX - this.handle.getWidth()/2;
			}
			Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.sliderStartPoint)/this.maxVolume);
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public boolean mouseInsideImage(float mouseX, float mouseY, float imageX, float imageY, Image image){
		if((mouseX >= imageX && mouseX <= imageX + image.getWidth()) &&
	            (mouseY >= imageY && mouseY <= imageY + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}
	
	public boolean mouseInsideArea(float mouseX, float mouseY, float imageX, float imageY, float imageWidth, float imageHeight){
		if((mouseX >= imageX && mouseX <= imageX + imageWidth) &&
	            (mouseY >= imageY && mouseY <= imageY + imageHeight)){
					return true;
		}
		else{
			return false;
		}
	}
}
