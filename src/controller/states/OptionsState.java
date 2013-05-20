package controller.states;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import utils.Sounds;

public class OptionsState extends BasicGameState {
	
	private int stateID;
	private final String PATH = "res/OptionsMenu/";
	
	private float arrowSpace = 32;
	private float spacing = 20;
	
	private float startPoint;
	private float endPoint;
	private float maxVolume;
	
	private Image background;
	
	private Image options;
	
	private Image musicOn;
	private Image musicOff;
	private Image soundsOn;
	private Image soundsOff;
	private Image volume;
	private Image slider;
	private Image handle;
	
	private Image fullscreenOn;
	private Image fullscreenOff;
	
	private Image walkRight;
	private Image walkLeft;
	private Image jump;
	private Image fight;
	
	private Image key;
	private Image keyH;
	private Image keyM;
	
	private Image ok;
	private Image okH;
	
	private Vec2 optionsPos;
	
	private Vec2 musicPos;
	private Vec2 soundsPos;
	private Vec2 musicVolumePos;
	private Vec2 soundsVolumePos;
	private Vec2 musicSliderPos;
	private Vec2 soundsSliderPos;
	private Vec2 musicHandlePos;
	private Vec2 soundsHandlePos;
	
	private Vec2 fullscreenPos;
	
	private Vec2 walkRightPos;
	private Vec2 walkLeftPos;
	private Vec2 jumpPos;
	private Vec2 fightPos;
	
	private Vec2[] keyPos = new Vec2[4];
	
	private Vec2 okPos;
	
	private boolean insideOk;
	
	private boolean insideMusicOn;
	private boolean clickedMusicOn;
	
	private boolean insideSoundsOn;
	private boolean clickedSoundsOn;
	
	private boolean insideFullscreenOn;
	private boolean clickedFullscreenOn;
	
	private boolean[] insideKeys = new boolean[4];
	
	private boolean[] clickedKeys = new boolean[4];

	public OptionsState(int stateID){
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		this.background = new Image("res/Background.png");
		
		this.options = new Image(PATH + "options.png");
		
		this.musicOn = new Image(PATH + "musicOn.png");
		this.musicOff = new Image(PATH + "musicOff.png");
		
		this.soundsOn = new Image(PATH + "soundsOn.png");
		this.soundsOff = new Image(PATH + "soundsOff.png");
		
		this.volume = new Image(PATH + "volume.png");
		this.slider = new Image(PATH + "slider.png");
		this.handle = new Image(PATH + "handle.png");
		
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
		
		this.musicPos = new Vec2((gc.getWidth() - this.musicOn.getWidth() - this.volume.getWidth() - this.slider.getWidth() - this.spacing*3)/2, this.optionsPos.y + this.options.getHeight() + this.spacing);
		
		float spaceY = (this.okPos.y - this.musicPos.y - this.musicOn.getHeight() - this.soundsOn.getHeight() - this.fullscreenOn.getHeight() - this.walkRight.getHeight()*2)/6;
		
		this.musicVolumePos = new Vec2(this.musicPos.x + this.musicOn.getWidth() + this.spacing*2, this.musicPos.y);
		this.musicSliderPos = new Vec2(this.musicVolumePos.x + this.volume.getWidth() + this.spacing, this.musicPos.y);
		
		this.startPoint = this.musicSliderPos.x + this.arrowSpace;
		this.endPoint = this.musicSliderPos.x + this.slider.getWidth() - this.arrowSpace - this.handle.getWidth();
		this.maxVolume = this.endPoint - this.startPoint;
		
		float musicVolume = Sounds.getInstance().getMusicVolume();
		
		this.musicHandlePos = new Vec2(this.startPoint + musicVolume*this.maxVolume, this.musicSliderPos.y + this.slider.getHeight()/2 - this.handle.getHeight()/2);
		
		this.soundsPos = new Vec2(this.musicPos.x, this.musicPos.y + this.musicOn.getHeight() + spaceY);
		this.soundsVolumePos = new Vec2(this.musicPos.x + this.musicOn.getWidth() + this.spacing*2, this.soundsPos.y);
		this.soundsSliderPos = new Vec2(this.musicVolumePos.x + this.volume.getWidth() + this.spacing, this.soundsPos.y);
		
		float soundVolume = Sounds.getInstance().getSoundVolume();
		
		this.soundsHandlePos = new Vec2(this.startPoint + soundVolume*this.maxVolume, this.soundsSliderPos.y + this.slider.getHeight()/2 - this.handle.getHeight()/2);
		
		this.fullscreenPos = new Vec2(gc.getWidth()/2 - this.fullscreenOn.getWidth()/2, this.soundsPos.y + this.soundsOn.getHeight() + spaceY*2);
		
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
		
		if(Sounds.getInstance().isSoundOn()) {
			if(this.insideSoundsOn) {
				if(this.clickedSoundsOn) {
					this.soundsOn.draw(this.soundsPos.x, this.soundsPos.y);
				} else {
					this.soundsOff.draw(this.soundsPos.x, this.soundsPos.y);
				}
			} else {
				this.soundsOn.draw(this.soundsPos.x, this.soundsPos.y);
			}
		} else {
			if(this.insideSoundsOn) {
				if(this.clickedSoundsOn) {
					this.soundsOff.draw(this.soundsPos.x, this.soundsPos.y);
				} else {
					this.soundsOn.draw(this.soundsPos.x, this.soundsPos.y);
				}
			} else {
				this.soundsOff.draw(this.soundsPos.x, this.soundsPos.y);
			}
		}
		
		this.volume.draw(this.soundsVolumePos.x, this.soundsVolumePos.y);
		this.slider.draw(this.soundsSliderPos.x, this.soundsSliderPos.y);
		this.handle.draw(this.soundsHandlePos.x, this.soundsHandlePos.y);
		
		if(Sounds.getInstance().isSoundOn()) {
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
		
		this.walkRight.draw(this.walkRightPos.x, this.walkRightPos.y);
		
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
		
		this.walkLeft.draw(this.walkLeftPos.x, this.walkLeftPos.y);
		
		this.jump.draw(this.jumpPos.x, this.jumpPos.y);
	
		this.fight.draw(this.fightPos.x, this.fightPos.y);
		
		if(this.insideOk) {
			this.okH.draw(this.okPos.x, this.okPos.y);
		} else {
			this.ok.draw(this.okPos.x, this.okPos.y);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput();
		float mouseX = input.getMouseX();
		float mouseY = input.getMouseY();
		
		this.insideOk = this.checkMouse(mouseX, mouseY, this.okPos.x, this.okPos.y, this.ok);
		this.insideMusicOn = this.checkMouse(mouseX, mouseY, this.musicPos.x, this.musicPos.y, this.musicOn);
		this.insideSoundsOn = this.checkMouse(mouseX, mouseY, this.soundsPos.x, this.soundsPos.y, this.soundsOn);
		this.insideFullscreenOn = this.checkMouse(mouseX, mouseY, this.fullscreenPos.x, this.fullscreenPos.y, this.fullscreenOn);
		
		this.insideKeys[0] = this.checkMouse(mouseX, mouseY, this.keyPos[0].x, this.keyPos[0].y, this.key);
		this.insideKeys[1] = this.checkMouse(mouseX, mouseY, this.keyPos[1].x, this.keyPos[1].y, this.key);
		this.insideKeys[2] = this.checkMouse(mouseX, mouseY, this.keyPos[2].x, this.keyPos[2].y, this.key);
		this.insideKeys[3] = this.checkMouse(mouseX, mouseY, this.keyPos[3].x, this.keyPos[3].y, this.key);
		
		if(!this.insideMusicOn && this.clickedMusicOn) {
			this.clickedMusicOn = false;
		}
		if(!this.insideSoundsOn && this.clickedSoundsOn) {
			this.clickedSoundsOn = false;
		}
		if(!this.insideFullscreenOn && this.clickedFullscreenOn) {
			this.clickedFullscreenOn = false;
		}
		
		if(this.insideOk && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			sbg.enterState(GameApp.MAINMENUSTATE);
		} else if(this.insideMusicOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Sounds.getInstance().setMusicOn(!Sounds.getInstance().isMusicOn());
			this.clickedMusicOn = true;
		} else if(this.insideSoundsOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			Sounds.getInstance().setSoundOn(!Sounds.getInstance().isSoundOn());
			this.clickedSoundsOn = true;
		} else if(this.insideFullscreenOn && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			this.clickedFullscreenOn = true;
		}
		
		for(int i = 0; i < this.insideKeys.length; i++){
			if(this.insideKeys[i] && input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				this.clickedKeys[i] = true;
			}
		}
	}

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public boolean checkMouse(float mouseX, float mouseY, float menuX, float menuY, Image image){
		if((mouseX >= menuX && mouseX <= menuX + image.getWidth()) &&
	            (mouseY >= menuY && mouseY <= menuY + image.getHeight())){
					return true;
		}
		else{
			return false;
		}
	}

}
