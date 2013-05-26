package controller.states;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Controls;
import utils.SoundType;
import utils.Sounds;
import utils.Utils;

public class OptionsState extends BasicGameState {
	
	private int stateID;
	private int previousStateID = -1;
	private final String PATH = "res/options_menu/";
	
	private float arrowSpace = 32, arrowSpeed = 1f, spacing = 20, mouseHandleSpace;
	
	private Image background, options;
	private Vec2 optionsPos;
	
	private Image volume, controls, handle;
	private float handleEndPoint, sliderLength, maxVolume, sliderHeight = 10, arrowRadius = 13;
	
	private Image musicOn, musicOff;
	private Vec2 musicPos, musicVolumePos, musicControlsPos, musicSliderPos, musicHandlePos;
	private boolean insideMusicOn, clickedMusicOn;
	
	private Image soundOn, soundOff;
	private Vec2 soundPos, soundVolumePos, soundControlsPos, soundSliderPos, soundHandlePos;
	private boolean insideSoundOn, clickedSoundOn;
	
	//music left arrow, music right arrow, sound left arrow, sound right arrow
	private List<Boolean> clickedArrow = new ArrayList<Boolean>(4); 
	private List<Vec2> arrowPos = new ArrayList<Vec2>(4);
	//music handle, sound handle
	private List<Boolean> clickedHandle = new ArrayList<Boolean>(2);
	
	private Image fullscreenOn, fullscreenOff;
	private Vec2 fullscreenPos;
	private boolean insideFullscreenOn, clickedFullscreenOn;
	
	private Image walkRight, walkLeft, jump, fight;
	private Vec2 walkRightPos, walkLeftPos, jumpPos, fightPos;
	
	private Image key, keyH, keyM;
	private List<Vec2> keyPos = new ArrayList<Vec2>();
	private List<Boolean> insideKeys = new ArrayList<Boolean>(4), clickedKeys = new ArrayList<Boolean>(4);
	private List<Integer> chosenKeys = new ArrayList<Integer>(4);
	private List<Vec2> chosenKeyPos = new ArrayList<Vec2>(4);
	
	private Image ok, okH;
	private Vec2 okPos;
	private boolean insideOk;
	
	private static OptionsState instance = null;
	
	private OptionsState(int stateID){
		this.stateID = stateID;
	}
	
	public static OptionsState getInstance() {
		if(instance == null){
			instance = new OptionsState(GameApp.OPTIONS_STATE);
		}
		return instance;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		/* IMAGES _______________________________________________ */
		//Initiate all images.
		this.background = new Image("res/background.png");
		this.options = new Image(PATH + "options.png");
		
		this.volume = new Image(PATH + "volume.png");
		this.controls = new Image(PATH + "slider.png");
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
		/* IMAGES END ___________________________________________ */
		
		/* LISTS ________________________________________________ */
		for(int i = 0; i < 4; i++) {
			this.insideKeys.add(false);
			this.clickedKeys.add(false);
			this.clickedArrow.add(false);
			this.arrowPos.add(new Vec2(0,0));
			this.keyPos.add(new Vec2(0,0));
			this.chosenKeyPos.add(new Vec2(0,0));
		}
		
		this.clickedHandle.add(false);
		this.clickedHandle.add(false);
		
		this.chosenKeys.add(0);
		this.chosenKeys.add(0);
		this.chosenKeys.add(0);
		this.chosenKeys.add(0);
		/* LISTS END ____________________________________________ */
		
		this.initPositions(gc);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		this.background.draw(0, 0, gc.getWidth(), gc.getHeight());
		this.options.draw(this.optionsPos.x, this.optionsPos.y);
		
		/* MUSIC _______________________________________________________________ */
		//Draw everything to do with the music option.
		this.renderCheckbox(Sounds.getInstance().isMusicOn(), this.insideMusicOn, this.clickedMusicOn, this.musicOn, this.musicOff, this.musicPos);
		this.renderVolumeControls(this.musicVolumePos, this.musicControlsPos, this.musicHandlePos);
		/* MUSIC END ___________________________________________________________ */
		
		/* SOUND _______________________________________________________________ */
		//Draw everything to do with the sound option.
		this.renderCheckbox(Sounds.getInstance().isSoundOn(), this.insideSoundOn, this.clickedSoundOn, this.soundOn, this.soundOff, this.soundPos);
		this.renderVolumeControls(this.soundVolumePos, this.soundControlsPos, this.soundHandlePos);
		/* SOUND END ___________________________________________________________ */
		
		/* FULLSCREEN __________________________________________________________ */
		//Draw everything to do with the full screen option.
		this.renderCheckbox(gc.isFullscreen(), this.insideFullscreenOn, this.clickedFullscreenOn, this.fullscreenOn, this.fullscreenOff, this.fullscreenPos);
		/* FULLSCREEN END ______________________________________________________ */
		
		/* KEYBINDINGS _________________________________________________________ */
		//Draw everything to do with the key bindings options.
		this.walkRight.draw(this.walkRightPos.x, this.walkRightPos.y);
		this.walkLeft.draw(this.walkLeftPos.x, this.walkLeftPos.y);
		this.jump.draw(this.jumpPos.x, this.jumpPos.y);
		this.fight.draw(this.fightPos.x, this.fightPos.y);
		
		for(int i = 0; i < this.insideKeys.size(); i++){
			if(this.insideKeys.get(i)) {
				if(this.clickedKeys.get(i)) {
					this.keyM.draw(this.keyPos.get(i).x, this.keyPos.get(i).y);
				} else {
					this.keyH.draw(this.keyPos.get(i).x, this.keyPos.get(i).y);
				}
			} else {
				if(this.clickedKeys.get(i)) {
					this.keyM.draw(this.keyPos.get(i).x, this.keyPos.get(i).y);
				} else {
					this.key.draw(this.keyPos.get(i).x, this.keyPos.get(i).y);
				}
			}
		} 
		
		g.setColor(Color.white);
		for(int i = 0; i<this.chosenKeys.size(); i++) {
			g.drawString(Input.getKeyName(this.chosenKeys.get(i)), this.chosenKeyPos.get(i).x, this.chosenKeyPos.get(i).y);
		}
		/* KEYBINDINGS END _____________________________________________________ */
		
		/* OK __________________________________________________________________ */
		if(this.insideOk) {
			this.okH.draw(this.okPos.x, this.okPos.y);
		} else {
			this.ok.draw(this.okPos.x, this.okPos.y);
		} 
		/* OK END ______________________________________________________________ */
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		//Temporarily store the input and the mouse coordinates.
		Input input = gc.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		boolean mousePressed = input.isMousePressed(Input.MOUSE_LEFT_BUTTON);
		boolean mouseDown = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
		
		/* INSIDE BOOLEANS ______________________________________________________________________________ */
		this.insideMusicOn = Utils.isMouseInsideImage(mouseX, mouseY, this.musicPos, this.musicOn);
		boolean insideMusicHandle = Utils.isMouseInsideImage(mouseX, mouseY, this.musicHandlePos, this.handle);
		
		this.insideSoundOn = Utils.isMouseInsideImage(mouseX, mouseY, this.soundPos, this.soundOn);
		boolean insideSoundHandle = Utils.isMouseInsideImage(mouseX, mouseY, this.soundHandlePos, this.handle);
		
		this.insideFullscreenOn = Utils.isMouseInsideImage(mouseX, mouseY, this.fullscreenPos, this.fullscreenOn);
		
		for(int i = 0; i < this.insideKeys.size(); i++){
			this.insideKeys.set(i, Utils.isMouseInsideImage(mouseX, mouseY, this.keyPos.get(i), this.key));
		}
		
		this.insideOk = Utils.isMouseInsideImage(mouseX, mouseY, this.okPos, this.ok);
		/* INSIDE BOOLEANS END __________________________________________________________________________ */
		
		/* CHECKBOX CLICKED _____________________________________________________________________________ */
		//If the mouse is no longer inside a check box option that was recently clicked, set clicked = false;
		if(!this.insideMusicOn && this.clickedMusicOn) {
			this.clickedMusicOn = false;
		} else if(!this.insideSoundOn && this.clickedSoundOn) {
			this.clickedSoundOn = false;
		} else if(!this.insideFullscreenOn && this.clickedFullscreenOn) {
			this.clickedFullscreenOn = false;
		} 
		/* CHECKBOX CLICKED END _________________________________________________________________________ */
		
		/* MUSIC ________________________________________________________________________________________ */
		//The music on option is clicked.
		if(this.insideMusicOn && mousePressed) {
			Sounds.getInstance().setMusicOn(!Sounds.getInstance().isMusicOn());
			if(Sounds.getInstance().isMusicOn()){
				Sounds.getInstance().playMusic(SoundType.MENU_MUSIC);
			} else {
				Sounds.getInstance().stopMusic();
			}
			this.clickedMusicOn = true;
		}
		
		//Calculates the new position for the handle on the music volume slider according to user input and adjusts the music volume accordingly.
		this.moveHandle(insideMusicHandle, mousePressed, mouseX, this.musicHandlePos, mouseDown, this.musicSliderPos, 0);
		
		//If the user clicks the slider the handle will move there.
		this.clickSlider(mouseX, mouseY, this.musicSliderPos, mousePressed, this.musicHandlePos, 0);
		
		//Calculates the new position of the handle if the left arrow is clicked by the user.
		this.clickArrow(mouseX, mouseY, mouseDown, this.musicHandlePos, this.musicSliderPos, 0, 0, -1);
		this.clickArrow(mouseX, mouseY, mouseDown, this.musicHandlePos, this.musicSliderPos, 1, 0, 1);
		/* MUSIC END ____________________________________________________________________________________ */
		
		
		/* SOUND ________________________________________________________________________________________ */
		//The sound on option is clicked.
		if(this.insideSoundOn && mousePressed) {
			Sounds.getInstance().setSoundOn(!Sounds.getInstance().isSoundOn());
			this.clickedSoundOn = true;
		} 
		
		//Calculates the new position for the handle on the sound volume slider according to user input and adjusts the sound volume accordingly.
		this.moveHandle(insideSoundHandle, mousePressed, mouseX, this.soundHandlePos, mouseDown, this.soundSliderPos, 1);
		
		//If the user clicks the slider the handle will move there.
		this.clickSlider(mouseX, mouseY, this.soundSliderPos, mousePressed, this.soundHandlePos, 1);
		
		//Calculates the new position of the handle if the left arrow is clicked by the user.
		this.clickArrow(mouseX, mouseY, mouseDown, this.soundHandlePos, this.soundSliderPos, 2, 1, -1);
		this.clickArrow(mouseX, mouseY, mouseDown, this.soundHandlePos, this.soundSliderPos, 3, 1, 1);
		/* SOUND END ____________________________________________________________________________________ */
		
		/* FULLSCREEN ___________________________________________________________________________________ */
		//The full screen option is clicked.
		if(this.insideFullscreenOn && mousePressed) {
			this.clickedFullscreenOn = true;
			if(gc.isFullscreen()) {
				GameApp.appgc.setDisplayMode((int)GameApp.DEFAULT_WIDTH, (int)GameApp.DEFAULT_HEIGHT, false);
			} else {
				GameApp.appgc.setDisplayMode(gc.getScreenWidth(), gc.getScreenHeight(), true);
			}
			
			this.initPositions(gc);
		} 
		/* FULLSCREEN END _______________________________________________________________________________ */
		
		/* KEYBINDINGS __________________________________________________________________________________ */
		//If any of the key bindings are clicked, mark them.
		for(int i = 0; i < this.insideKeys.size(); i++){
			if(this.insideKeys.get(i) && mousePressed) {
				for(int j = 0; j < this.clickedKeys.size(); j++) {
					this.clickedKeys.set(j, false);
				}
				this.clickedKeys.set(i, true);
				break;
			} 
		}
		if(!this.insideKeys.contains(true) && mousePressed) {
			for(int j = 0; j < this.clickedKeys.size(); j++) {
				this.clickedKeys.set(j, false);
			}
		}
		//If any of the key bindings are marked, check what key is chosen for it.
		int index = this.clickedKeys.indexOf(true);
		if(index != -1) {
			for(int i = 0; i < 220; i++) {
				if(i != Input.KEY_ESCAPE && i != Input.KEY_P && input.isKeyPressed(i)){
					if(!this.chosenKeys.contains(i)) {
						this.chosenKeys.set(index, i);
					}
					this.clickedKeys.set(index, false);
				}
			}
			input.clearKeyPressedRecord();
		}
		/* KEYBINDINGS END ______________________________________________________________________________ */
		
		/* OK ___________________________________________________________________________________________ */
		//The OK button is clicked.
		if(this.insideOk && mousePressed) {
			Controls.getInstance().removeKeySettings();
			Controls.getInstance().bindToKey(Controls.CMD_RIGHT, this.chosenKeys.get(0));
			Controls.getInstance().bindToKey(Controls.CMD_LEFT, this.chosenKeys.get(1));
			Controls.getInstance().bindToKey(Controls.CMD_JUMP, this.chosenKeys.get(2));
			Controls.getInstance().bindToKey(Controls.CMD_FIGHT, this.chosenKeys.get(3));
			input.clearKeyPressedRecord();
			sbg.enterState(this.previousStateID);
		} 
		/* OK END _______________________________________________________________________________________ */
	}
	
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		this.chosenKeys.set(0, Controls.getInstance().getCommand(Controls.CMD_RIGHT));
		this.chosenKeys.set(1, Controls.getInstance().getCommand(Controls.CMD_LEFT));
		this.chosenKeys.set(2, Controls.getInstance().getCommand(Controls.CMD_JUMP));
		this.chosenKeys.set(3, Controls.getInstance().getCommand(Controls.CMD_FIGHT));
		Controls.getInstance().removeKeySettings();
	}
	
	/**
	 * A method that initiates the positions of all images (+ some more) based on what size the game containers window is.
	 * All the positions are based on each other so that they will move accordingly when the window dimensions are changed.
	 * @param gc	the game container
	 */
	private void initPositions(GameContainer gc) {
		float screenWidth = gc.getWidth();
		float screenHeight = gc.getHeight();
		
		//Calculating all positions
		this.optionsPos = new Vec2(screenWidth/2 - this.options.getWidth()/2, this.spacing);
		this.okPos = new Vec2(screenWidth - this.spacing*2 - this.ok.getWidth(), screenHeight-this.ok.getHeight()-this.spacing*2);
		
		/* SPACE _____________________________________________________________________________________________________________________________________ */
		//Calculate a number so that all options are evenly spaced.
		float spaceY = 0;
		if(gc.isFullscreen()) {
			spaceY = (screenHeight - (this.optionsPos.y + this.options.getHeight()) - this.musicOn.getHeight() - this.soundOn.getHeight() - this.fullscreenOn.getHeight() - this.walkRight.getHeight()*2)/10;
		} else {
			spaceY = ((this.okPos.y + this.ok.getHeight()) - (this.optionsPos.y + this.options.getHeight()) - this.musicOn.getHeight() - this.soundOn.getHeight() - this.fullscreenOn.getHeight() - this.walkRight.getHeight()*2)/10;
		}
		/* SPACE END _________________________________________________________________________________________________________________________________ */
		
		/* MUSIC _____________________________________________________________________________________________________________________________________ */
		//Calculates all positions to do with the music options so that they are evenly spaced and centered.
		this.musicPos = new Vec2((screenWidth - this.musicOn.getWidth() - this.volume.getWidth() - this.controls.getWidth() - this.spacing*3)/2, this.optionsPos.y + this.options.getHeight() + spaceY*2);
		this.musicVolumePos = new Vec2(this.musicPos.x + this.musicOn.getWidth() + this.spacing*2, this.musicPos.y);
		this.musicControlsPos = new Vec2(this.musicVolumePos.x + this.volume.getWidth() + this.spacing, this.musicPos.y);
		this.musicSliderPos = new Vec2(this.musicControlsPos.x + this.arrowSpace, this.musicControlsPos.y + this.controls.getHeight()/2 - this.sliderHeight/2);
		this.arrowPos.set(0, new Vec2(this.musicControlsPos.x, this.musicControlsPos.y + this.controls.getHeight()/2 - this.arrowRadius));
		this.arrowPos.set(1, new Vec2(this.musicControlsPos.x + this.controls.getWidth() - this.arrowRadius*2,this.arrowPos.get(0).y));
		/* MUSIC END _________________________________________________________________________________________________________________________________ */
		
		/* UNIVERSAL _________________________________________________________________________________________________________________________________ */
		//Calculate actual start point, end point of the handle and length of the slider, also calculates the length that represents the max volume.
		this.handleEndPoint = this.musicControlsPos.x + this.controls.getWidth() - this.arrowSpace - this.handle.getWidth();
		this.sliderLength = this.handleEndPoint + this.handle.getWidth() - this.musicSliderPos.x;
		this.maxVolume = this.handleEndPoint - this.musicSliderPos.x;
		/* UNIVERSAL END _____________________________________________________________________________________________________________________________ */
		
		/* MUSIC _____________________________________________________________________________________________________________________________________ */
		//Placing the handle on the volume control.
		float musicVolume = Sounds.getInstance().getMusicVolume();
		this.musicHandlePos = new Vec2(this.musicSliderPos.x + musicVolume*this.maxVolume, this.musicControlsPos.y + this.controls.getHeight()/2 - this.handle.getHeight()/2);
		/* MUSIC END _________________________________________________________________________________________________________________________________ */
		
		/* SOUND _____________________________________________________________________________________________________________________________________ */
		//Calculates all positions to do with the sound options so that they are evenly spaced and centered.
		this.soundPos = new Vec2(this.musicPos.x, this.musicPos.y + this.musicOn.getHeight() + spaceY);
		this.soundVolumePos = new Vec2(this.musicPos.x + this.musicOn.getWidth() + this.spacing*2, this.soundPos.y);
		this.soundControlsPos = new Vec2(this.musicVolumePos.x + this.volume.getWidth() + this.spacing, this.soundPos.y);
		this.soundSliderPos = new Vec2(this.musicSliderPos.x, this.soundControlsPos.y + this.controls.getHeight()/2 - this.sliderHeight/2);
		this.arrowPos.set(2, new Vec2(this.soundControlsPos.x, this.soundControlsPos.y + this.controls.getHeight()/2 - this.arrowRadius));
		this.arrowPos.set(3, new Vec2(this.soundControlsPos.x + this.controls.getWidth() - this.arrowRadius*2,this.arrowPos.get(2).y));
		
		//Placing the handle on the volume control.
		float soundVolume = Sounds.getInstance().getSoundVolume();
		this.soundHandlePos = new Vec2(this.soundSliderPos.x + soundVolume*this.maxVolume, this.soundControlsPos.y + this.controls.getHeight()/2 - this.handle.getHeight()/2);
		/* SOUND END _________________________________________________________________________________________________________________________________ */
		
		/* FULLSCREEN ________________________________________________________________________________________________________________________________ */
		//Calculates the position of the full screen option so that it is centered and evenly spaced from the other options.
		this.fullscreenPos = new Vec2(screenWidth/2 - this.fullscreenOn.getWidth()/2, this.soundPos.y + this.soundOn.getHeight() + spaceY*2);
		/* FULLSCREEN END ____________________________________________________________________________________________________________________________ */
		
		/* KEYBINDINGS _______________________________________________________________________________________________________________________________ */
		//Calculates all positions to do with changing the key bindings.
		this.walkRightPos = new Vec2((screenWidth - this.walkRight.getWidth() - this.key.getWidth()*2 - this.jump.getWidth() - this.spacing*4)/2, this.fullscreenPos.y + this.fullscreenOn.getHeight() + spaceY*2);
		this.keyPos.set(0, new Vec2(this.walkRightPos.x + this.walkRight.getWidth() + this.spacing, this.walkRightPos.y));
		this.chosenKeyPos.set(0, new Vec2(this.keyPos.get(0).x + this.spacing, this.keyPos.get(0).y + this.spacing/2 + 5));
		
		this.walkLeftPos = new Vec2((screenWidth - this.walkLeft.getWidth() - this.key.getWidth()*2 - this.fight.getWidth() - this.spacing*4)/2, this.walkRightPos.y + this.walkRight.getHeight() + spaceY);
		this.keyPos.set(1, new Vec2(this.walkLeftPos.x + this.walkLeft.getWidth() + this.spacing, this.walkLeftPos.y));
		this.chosenKeyPos.set(1, new Vec2(this.keyPos.get(1).x + this.spacing, this.keyPos.get(1).y + this.spacing/2 + 5));
		
		this.jumpPos = new Vec2(this.keyPos.get(0).x + this.key.getWidth() + this.spacing*2, this.walkRightPos.y);
		this.keyPos.set(2, new Vec2(this.jumpPos.x + this.jump.getWidth() + this.spacing, this.walkRightPos.y));
		this.chosenKeyPos.set(2, new Vec2(this.keyPos.get(2).x + this.spacing, this.keyPos.get(2).y + this.spacing/2 + 5));
		
		this.fightPos = new Vec2(this.keyPos.get(1).x + this.key.getWidth() + this.spacing*2, this.walkLeftPos.y);
		this.keyPos.set(3, new Vec2(this.fightPos.x + this.fight.getWidth() + this.spacing, this.walkLeftPos.y));
		this.chosenKeyPos.set(3, new Vec2(this.keyPos.get(3).x + this.spacing, this.keyPos.get(3).y + this.spacing/2 + 5));
		/* KEYBINDINGS END ___________________________________________________________________________________________________________________________ */
	}
	
	/**
	 * A method that moves the handle of a volume slider according to user input.
	 * This method only handles if the handle was clicked and dragged.
	 * @param insideHandle	true if the mouse is inside the handle
	 * @param mousePressed	true if the mouse was pressed
	 * @param mouseX		the x-coordinate of the mouse
	 * @param handlePos		the position of the handle
	 * @param mouseDown		true if the mouse is down
	 * @param sliderPos		the position of the slider
	 * @param kind			0 = the music handle, 1 = the sound handle
	 */
	private void moveHandle(boolean insideHandle, boolean mousePressed, float mouseX, Vec2 handlePos, boolean mouseDown, Vec2 sliderPos, int kind) {
		if(insideHandle && mousePressed) {
			this.clickedHandle.set(kind, true);
			this.mouseHandleSpace = mouseX - handlePos.x;
		} else if(this.clickedHandle.get(kind) && mouseDown) {
			if(mouseX - this.mouseHandleSpace <= sliderPos.x) {
				handlePos.x = sliderPos.x;
			} else if(mouseX - this.mouseHandleSpace >= this.handleEndPoint) {
				handlePos.x = this.handleEndPoint;
			} else {
				handlePos.x = mouseX - this.mouseHandleSpace;
			}
		} else if(this.clickedHandle.get(kind)) {
			this.clickedHandle.set(kind, false);
			if(kind == 0) {
				Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.musicSliderPos.x)/this.maxVolume);
			} else {
				Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.soundSliderPos.x)/this.maxVolume);
			}
		}
	}
	
	/**
	 * A method that moves the handle of a volume slider according to user input.
	 * This method only handles if the slider was clicked.
	 * @param mouseX		the x-coordinate of the mouse
	 * @param mouseY		the y-coordinate of the mouse
	 * @param sliderPos		the position of the slider
	 * @param mousePressed	true if the mouse was pressed
	 * @param handlePos		the position of the handle
	 * @param kind			0 = the music slider, 1 = the sound slider
	 */
	private void clickSlider(float mouseX, float mouseY, Vec2 sliderPos, boolean mousePressed, Vec2 handlePos, int kind) {
		if(Utils.isMouseInsideArea(mouseX, mouseY, sliderPos, this.sliderLength, this.sliderHeight) && mousePressed && !this.clickedHandle.get(kind)) {
			if(mouseX - this.handle.getWidth()/2 <= sliderPos.x) {
				handlePos.x = sliderPos.x;
			} else if(mouseX - this.handle.getWidth()/2 >= this.handleEndPoint) {
				handlePos.x = this.handleEndPoint;
			} else {
				handlePos.x = mouseX - this.handle.getWidth()/2;
			}
			if(kind == 0) {
				Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.musicSliderPos.x)/this.maxVolume);
			} else {
				Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.soundSliderPos.x)/this.maxVolume);
			}
		}
	}
	
	/**
	 * A method that moves the handle of a volume slider according to user input.
	 * This method only handles if one of the arrows of the volume control were clicked.
	 * @param mouseX		the x-coordinate of the mouse
	 * @param mouseY		the y-coordinate of the mouse
	 * @param mouseDown		true if the mouse is down
	 * @param handlePos		the position of the handle
	 * @param sliderPos		the position of the slider
	 * @param arrow			0 = left music arrow, 1 = right music arrow, 2 = left sound arrow, 3 = right sound arrow
	 * @param kind			0 = music, 1 = sound
	 * @param navigation	the navigation the handle will move in if this arrow is clicked
	 */
	private void clickArrow(float mouseX, float mouseY, boolean mouseDown, Vec2 handlePos, Vec2 sliderPos, int arrow, int kind, int navigation) {
		if(Utils.isMouseInsideArea(mouseX, mouseY, this.arrowPos.get(arrow), this.arrowRadius*2, this.arrowRadius*2) && mouseDown && !this.clickedHandle.get(kind)){
			this.clickedArrow.set(arrow, true);
			if(handlePos.x - this.arrowSpeed <= sliderPos.x && navigation == -1) {
				handlePos.x = sliderPos.x;
			} else if(handlePos.x + this.arrowSpeed >= this.handleEndPoint && navigation == 1) {
				handlePos.x = this.handleEndPoint;
			} else {
				handlePos.x = handlePos.x + navigation*this.arrowSpeed;
			}
		} else if(this.clickedArrow.get(arrow)) {
			this.clickedArrow.set(arrow, false);
			if(kind == 0) {
				Sounds.getInstance().setVolumeMusic((this.musicHandlePos.x - this.musicSliderPos.x)/this.maxVolume);
			} else {
				Sounds.getInstance().setVolumeSound((this.soundHandlePos.x - this.soundSliderPos.x)/this.maxVolume);
			}
		}
	}
	
	/**
	  * A method that renders a check box according to user input.
	  * @param isOn			true if what the check box controls is active
	  * @param isInside		true if the mouse is inside the check box area
	  * @param isClicked	true if the check box was recently clicked
	  * @param on			the image of the check box checked
	  * @param off			the image of the check box unchecked
	  * @param pos			the position of the check box
	  */
	private void renderCheckbox(boolean isOn, boolean isInside, boolean isClicked, Image on, Image off, Vec2 pos) {
		if(isOn) {
			if(isInside) {
				if(isClicked) {
					on.draw(pos.x, pos.y);
				} else {
					off.draw(pos.x, pos.y);
				}
			} else {
				on.draw(pos.x, pos.y);
			}
		} else {
			if(isInside) {
				if(isClicked) {
					off.draw(pos.x, pos.y);
				} else {
					on.draw(pos.x, pos.y);
				}
			} else {
				off.draw(pos.x, pos.y);
			}
		}
	}
	
	/**
	 * A method that renders a volume controller with the text "Volume" before it.
	 * @param volumePos		the position of the text
	 * @param controlsPos	the position of the controls
	 * @param handlePos		the position of the handle
	 */
	private void renderVolumeControls(Vec2 volumePos, Vec2 controlsPos, Vec2 handlePos) {
		this.volume.draw(volumePos.x, volumePos.y);
		this.controls.draw(controlsPos.x, controlsPos.y);
		this.handle.draw(handlePos.x, handlePos.y);
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	/**
	 * This is so that this OptionsState will know what state to return to.
	 * @param previousStateID
	 */
	public void setPreviousStateID(int previousStateID) {
		this.previousStateID = previousStateID;
	}
}
