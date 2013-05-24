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
	private final String PATH = "res/options_menu/";
	
	private float arrowSpace = 32, spacing = 20, mouseHandleSpace;
	private float arrowSpeed = 1f;
	
	private Image background, options;
	private Vec2 optionsPos;
	
	private Image volume, slider, handle;
	private float handleEndPoint, sliderLength, maxVolume, sliderHeight = 10, arrowRadius = 13;
	
	private Image musicOn, musicOff;
	private Vec2 musicPos, musicVolumePos, musicControlsPos, musicSliderPos,musicHandlePos, musicArrowLeftPos, musicArrowRightPos;
	private boolean insideMusicOn, clickedMusicOn, clickedMusicHandle, clickedMusicArrowLeft, clickedMusicArrowRight;
	
	private Image soundOn, soundOff;
	private Vec2 soundPos, soundVolumePos, soundControlsPos, soundSliderPos, soundHandlePos, soundArrowLeftPos, soundArrowRightPos;
	private boolean insideSoundOn, clickedSoundOn, clickedSoundHandle, clickedSoundArrowLeft, clickedSoundArrowRight;
	
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
		
		//IMAGES _______________________________________________
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
		//IMAGES END ___________________________________________
		
		this.initPositions(gc);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		this.background.draw(0,0);
		this.options.draw(this.optionsPos.x, this.optionsPos.y);
		
		//MUSIC _______________________________________________________________
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
		this.slider.draw(this.musicControlsPos.x, this.musicControlsPos.y);
		this.handle.draw(this.musicHandlePos.x, this.musicHandlePos.y);
		//MUSIC END ___________________________________________________________
		
		//SOUND _______________________________________________________________
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
		this.slider.draw(this.soundControlsPos.x, this.soundControlsPos.y);
		this.handle.draw(this.soundHandlePos.x, this.soundHandlePos.y);
		//SOUND END ___________________________________________________________
		
		//FULLSCREEN __________________________________________________________
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
		} //FULLSCREEN END ____________________________________________________
		
		//KEYBINDINGS _________________________________________________________
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
		} //KEYBINDINGS END ___________________________________________________
		
		//OK __________________________________________________________________
		if(this.insideOk) {
			this.okH.draw(this.okPos.x, this.okPos.y);
		} else {
			this.ok.draw(this.okPos.x, this.okPos.y);
		} //OK END ____________________________________________________________
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		//Temporarily store the input and the mouse coordinates.
		Input input = gc.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		
		//INSIDE BOOLEANS ______________________________________________________________________________
		this.insideMusicOn = this.mouseInsideImage(mouseX, mouseY, this.musicPos, this.musicOn);
		boolean insideMusicHandle = this.mouseInsideImage(mouseX, mouseY, this.musicHandlePos, this.handle);
		
		this.insideSoundOn = this.mouseInsideImage(mouseX, mouseY, this.soundPos, this.soundOn);
		boolean insideSoundHandle = this.mouseInsideImage(mouseX, mouseY, this.soundHandlePos, this.handle);
		
		this.insideFullscreenOn = this.mouseInsideImage(mouseX, mouseY, this.fullscreenPos, this.fullscreenOn);
		
		for(int i = 0; i < this.insideKeys.length; i++){
			this.insideKeys[i] = this.mouseInsideImage(mouseX, mouseY, this.keyPos[i], this.key);
		}
		
		this.insideOk = this.mouseInsideImage(mouseX, mouseY, this.okPos, this.ok);
		//INSIDE BOOLEANS END __________________________________________________________________________
		
		//CHECKBOX CLICKED _____________________________________________________________________________
		//If the mouse is no longer inside a check box option that was recently clicked, set clicked = false;
		if(!this.insideMusicOn && this.clickedMusicOn) {
			this.clickedMusicOn = false;
		} else if(!this.insideSoundOn && this.clickedSoundOn) {
			this.clickedSoundOn = false;
		} else if(!this.insideFullscreenOn && this.clickedFullscreenOn) {
			this.clickedFullscreenOn = false;
		} //CHECKBOX CLICKED END _______________________________________________________________________
		
		//MUSIC ________________________________________________________________________________________
		//The music on option is clicked.
		if(this.insideMusicOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Sounds.getInstance().setMusicOn(!Sounds.getInstance().isMusicOn());
			if(Sounds.getInstance().isMusicOn()){
				Sounds.getInstance().stopMusic();
				Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
			} else {
				Sounds.getInstance().stopMusic();
			}
			this.clickedMusicOn = true;
		} //-----------------------------------------------
		//Calculates the new position for the handle on the music volume slider according to user input and adjusts the music volume accordingly.
		if(insideMusicHandle && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.clickedMusicHandle = true;
			this.mouseHandleSpace = mouseX - this.musicHandlePos.x;
		} else if(this.clickedMusicHandle && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX - this.mouseHandleSpace <= this.musicSliderPos.x) {
				this.musicHandlePos.x = this.musicSliderPos.x;
			} else if(mouseX - this.mouseHandleSpace >= this.handleEndPoint) {
				this.musicHandlePos.x = this.handleEndPoint;
			} else {
				this.musicHandlePos.x = mouseX - this.mouseHandleSpace;
			}
		} else if(this.clickedMusicHandle) {
			this.clickedMusicHandle = false;
			Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.musicSliderPos.x)/this.maxVolume);
		} //-----------------------------------------------
		//If the user clicks the slider the handle will move there.
		if(this.mouseInsideArea(mouseX, mouseY, this.musicSliderPos, this.sliderLength, this.sliderHeight) && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX - this.handle.getWidth()/2 <= this.musicSliderPos.x) {
				this.musicHandlePos.x = this.musicSliderPos.x;
			} else if(mouseX - this.handle.getWidth()/2 >= this.handleEndPoint) {
				this.musicHandlePos.x = this.handleEndPoint;
			} else {
				this.musicHandlePos.x = mouseX - this.handle.getWidth()/2;
			}
			Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.musicSliderPos.x)/this.maxVolume);
		} //-----------------------------------------------
		//Calculates the new position of the handle if the left arrow is clicked by the user.
		if(this.mouseInsideArea(mouseX, mouseY, this.musicArrowLeftPos, this.arrowRadius*2, this.arrowRadius*2) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			this.clickedMusicArrowLeft = true;
			if(this.musicHandlePos.x - this.arrowSpeed <= this.musicSliderPos.x) {
				this.musicHandlePos.x = this.musicSliderPos.x;
			} else {
				this.musicHandlePos.x -= this.arrowSpeed;
			}
		} else if(this.clickedMusicArrowLeft) {
			this.clickedMusicArrowLeft = false;
			Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.musicSliderPos.x)/this.maxVolume);
		} //-----------------------------------------------
		//Calculates the new position of the handle if the left arrow is clicked by the user.
		if(this.mouseInsideArea(mouseX, mouseY, this.musicArrowRightPos, this.arrowRadius*2, this.arrowRadius*2) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			this.clickedMusicArrowRight = true;
			if(this.musicHandlePos.x + this.arrowSpeed >= this.handleEndPoint) {
				this.musicHandlePos.x = this.handleEndPoint;
			} else {
				this.musicHandlePos.x += this.arrowSpeed;
			}
		} else if(this.clickedMusicArrowRight) {
			this.clickedMusicArrowRight = false;
			Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.musicSliderPos.x)/this.maxVolume);
		} //MUSIC END __________________________________________________________________________________
		
		//SOUND ________________________________________________________________________________________
		//The sound on option is clicked.
		else if(this.insideSoundOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Sounds.getInstance().setSoundOn(!Sounds.getInstance().isSoundOn());
			this.clickedSoundOn = true;
		} //-----------------------------------------------
		//Calculates the new position for the handle on the sound volume slider according to user input and adjusts the sound volume accordingly.
		if(insideSoundHandle && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.clickedSoundHandle = true;
			this.mouseHandleSpace = mouseX - this.soundHandlePos.x;
		} else if(this.clickedSoundHandle && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX - this.mouseHandleSpace <= this.soundSliderPos.x) {
				this.soundHandlePos.x = this.soundSliderPos.x;
			} else if(mouseX - this.mouseHandleSpace >= this.handleEndPoint) {
				this.soundHandlePos.x = this.handleEndPoint;
			} else {
				this.soundHandlePos.x = mouseX - this.mouseHandleSpace;
			}
		} else if(this.clickedSoundHandle) {
			this.clickedSoundHandle = false;
			Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.soundSliderPos.x)/this.maxVolume);
		} //-----------------------------------------------
		//If the user clicks the slider the handle will move there.
		if(this.mouseInsideArea(mouseX, mouseY, this.soundSliderPos, this.sliderLength, this.sliderHeight) && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(mouseX - this.handle.getWidth()/2 <= this.soundSliderPos.x) {
				this.soundHandlePos.x = this.soundSliderPos.x;
			} else if(mouseX - this.handle.getWidth()/2 >= this.handleEndPoint) {
				this.soundHandlePos.x = this.handleEndPoint;
			} else {
				this.soundHandlePos.x = mouseX - this.handle.getWidth()/2;
			}
			Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.soundSliderPos.x)/this.maxVolume);
		} //-----------------------------------------------
		//Calculates the new position of the handle if the left arrow is clicked by the user.
		if(this.mouseInsideArea(mouseX, mouseY, this.soundArrowLeftPos, this.arrowRadius*2, this.arrowRadius*2) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			this.clickedSoundArrowLeft = true;
			if(this.soundHandlePos.x - this.arrowSpeed <= this.soundSliderPos.x) {
				this.soundHandlePos.x = this.soundSliderPos.x;
			} else {
				this.soundHandlePos.x -= this.arrowSpeed;
			}
		} else if(this.clickedSoundArrowLeft) {
			this.clickedSoundArrowLeft = false;
			Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.soundSliderPos.x)/this.maxVolume);
		} //-----------------------------------------------
		//Calculates the new position of the handle if the left arrow is clicked by the user.
		if(this.mouseInsideArea(mouseX, mouseY, this.soundArrowRightPos, this.arrowRadius*2, this.arrowRadius*2) && input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			this.clickedSoundArrowRight = true;
			if(this.soundHandlePos.x + this.arrowSpeed >= this.handleEndPoint) {
				this.soundHandlePos.x = this.handleEndPoint;
			} else {
				this.soundHandlePos.x += this.arrowSpeed;
			}
		} else if(this.clickedSoundArrowRight) {
			this.clickedSoundArrowRight = false;
			Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.soundSliderPos.x)/this.maxVolume);
		} //SOUND END __________________________________________________________________________________
		
		//FULLSCREEN ___________________________________________________________________________________
		//The full screen option is clicked.
		if(this.insideFullscreenOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.clickedFullscreenOn = true;
			if(gc.isFullscreen()) {
				GameApp.appgc.setDisplayMode((int)GameApp.DEFAULT_WIDTH, (int)GameApp.DEFAULT_HEIGHT, false);
			} else {
				GameApp.appgc.setDisplayMode(gc.getScreenWidth(), gc.getScreenHeight(), true);
			}
			
			this.initPositions(gc);
		} //FULLSCREEN END _____________________________________________________________________________
		
		//OK ___________________________________________________________________________________________
		//The OK button is clicked.
		if(this.insideOk && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			sbg.enterState(GameApp.MAINMENUSTATE);
		} //OK END _____________________________________________________________________________________
		
		//KEYBINDINGS __________________________________________________________________________________
		for(int i = 0; i < this.insideKeys.length; i++){
			if(this.insideKeys[i] && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				this.clickedKeys[i] = true;
			}
		} //KEYBINDINGS END ____________________________________________________________________________
	}
	
	public void initPositions(GameContainer gc) {
		float screenWidth = gc.getWidth();
		float screenHeight = gc.getHeight();
		
		//Calculating all positions
		this.optionsPos = new Vec2(screenWidth/2 - this.options.getWidth()/2, this.spacing);
		this.okPos = new Vec2(screenWidth - this.spacing*2 - this.ok.getWidth(), screenHeight-this.ok.getHeight()-this.spacing*2);
		
		//MUSIC __________________________________________________________MUSIC_________________________________________________________________MUSIC
		//Calculates all positions to do with the music options so that they are evenly spaced and centered.
		this.musicPos = new Vec2((screenWidth - this.musicOn.getWidth() - this.volume.getWidth() - this.slider.getWidth() - this.spacing*3)/2, this.optionsPos.y + this.options.getHeight() + this.spacing);
		this.musicVolumePos = new Vec2(this.musicPos.x + this.musicOn.getWidth() + this.spacing*2, this.musicPos.y);
		this.musicControlsPos = new Vec2(this.musicVolumePos.x + this.volume.getWidth() + this.spacing, this.musicPos.y);
		this.musicSliderPos = new Vec2(this.musicControlsPos.x + this.arrowSpace, this.musicControlsPos.y + this.slider.getHeight()/2 - this.sliderHeight/2);
		this.musicArrowLeftPos = new Vec2(this.musicControlsPos.x, this.musicControlsPos.y + this.slider.getHeight()/2 - this.arrowRadius);
		this.musicArrowRightPos = new Vec2(this.musicControlsPos.x + this.slider.getWidth() - this.arrowRadius*2,this.musicArrowLeftPos.y);
		//MUSIC END _________________________________________________________________________________________________________________________________
		
		//UNIVERSAL _________________________________________________________________________________________________________________________________
		//Calculate actual start point, end point of the handle and length of the slider, also calculates the length that represents the max volume.
		this.handleEndPoint = this.musicControlsPos.x + this.slider.getWidth() - this.arrowSpace - this.handle.getWidth();
		this.sliderLength = this.handleEndPoint + this.handle.getWidth() - this.musicSliderPos.x;
		this.maxVolume = this.handleEndPoint - this.musicSliderPos.x;
		//UNIVERSAL END _____________________________________________________________________________________________________________________________
		
		//MUSIC _____________________________________________________________________________________________________________________________________
		//Placing the handle on the volume control.
		float musicVolume = Sounds.getInstance().getMusicVolume();
		this.musicHandlePos = new Vec2(this.musicSliderPos.x + musicVolume*this.maxVolume, this.musicControlsPos.y + this.slider.getHeight()/2 - this.handle.getHeight()/2);
		//MUSIC END _________________________________________________________________________________________________________________________________
		
		//SPACE _____________________________________________________________________________________________________________________________________
		//Calculate a number so that all options are evenly spaced.
		float spaceY = (this.okPos.y - this.musicPos.y - this.musicOn.getHeight() - this.soundOn.getHeight() - this.fullscreenOn.getHeight() - this.walkRight.getHeight()*2)/6;
		//SPACE END _________________________________________________________________________________________________________________________________
		
		//SOUND _____________________________________________________________________________________________________________________________________
		//Calculates all positions to do with the sound options so that they are evenly spaced and centered.
		this.soundPos = new Vec2(this.musicPos.x, this.musicPos.y + this.musicOn.getHeight() + spaceY);
		this.soundVolumePos = new Vec2(this.musicPos.x + this.musicOn.getWidth() + this.spacing*2, this.soundPos.y);
		this.soundControlsPos = new Vec2(this.musicVolumePos.x + this.volume.getWidth() + this.spacing, this.soundPos.y);
		this.soundSliderPos = new Vec2(this.musicSliderPos.x, this.soundControlsPos.y + this.slider.getHeight()/2 - this.sliderHeight/2);
		this.soundArrowLeftPos = new Vec2(this.soundControlsPos.x, this.soundControlsPos.y + this.slider.getHeight()/2 - this.arrowRadius);
		this.soundArrowRightPos = new Vec2(this.soundControlsPos.x + this.slider.getWidth() - this.arrowRadius*2,this.soundArrowLeftPos.y);
		
		//Placing the handle on the volume control.
		float soundVolume = Sounds.getInstance().getSoundVolume();
		this.soundHandlePos = new Vec2(this.soundSliderPos.x + soundVolume*this.maxVolume, this.soundControlsPos.y + this.slider.getHeight()/2 - this.handle.getHeight()/2);
		//SOUND END _________________________________________________________________________________________________________________________________
		
		//FULLSCREEN ________________________________________________________________________________________________________________________________
		//Calculates the position of the full screen option so that it is centered and evenly spaced from the other options.
		this.fullscreenPos = new Vec2(screenWidth/2 - this.fullscreenOn.getWidth()/2, this.soundPos.y + this.soundOn.getHeight() + spaceY*2);
		//FULLSCREEN END ____________________________________________________________________________________________________________________________
		
		//KEYBINDINGS _______________________________________________________________________________________________________________________________
		//Calculates all positions to do with changing the key bindings.
		this.walkRightPos = new Vec2((screenWidth - this.walkRight.getWidth() - this.key.getWidth()*2 - this.jump.getWidth() - this.spacing*4)/2, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.keyPos[0] = new Vec2(this.walkRightPos.x + this.walkRight.getWidth() + this.spacing, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.jumpPos = new Vec2(this.keyPos[0].x + this.key.getWidth() + this.spacing*2, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.keyPos[1] = new Vec2(this.jumpPos.x + this.jump.getWidth() + this.spacing, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.walkLeftPos = new Vec2((screenWidth - this.walkLeft.getWidth() - this.key.getWidth()*2 - this.fight.getWidth() - this.spacing*4)/2, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
		this.keyPos[2] = new Vec2(this.walkLeftPos.x + this.walkLeft.getWidth() + this.spacing, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
		this.fightPos = new Vec2(this.keyPos[2].x + this.key.getWidth() + this.spacing*2, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
		this.keyPos[3] = new Vec2(this.fightPos.x + this.fight.getWidth() + this.spacing, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
		//KEYBINDINGS END ___________________________________________________________________________________________________________________________
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public boolean mouseInsideImage(float mouseX, float mouseY, Vec2 imagePos, Image image){
		if((mouseX >= imagePos.x && mouseX <= imagePos.x + image.getWidth()) &&
	            (mouseY >= imagePos.y && mouseY <= imagePos.y + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}
	
	public boolean mouseInsideArea(float mouseX, float mouseY, Vec2 imagePos, float imageWidth, float imageHeight){
		if((mouseX >= imagePos.x && mouseX <= imagePos.x + imageWidth) &&
	            (mouseY >= imagePos.y && mouseY <= imagePos.y + imageHeight)){
					return true;
		}
		else{
			return false;
		}
	}
}
