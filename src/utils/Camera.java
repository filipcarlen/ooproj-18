package utils;

import java.awt.Rectangle;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.geom.Vector2f;

public class Camera {
	int width,height;
	float x, y;
	static Vector2f positionCamera;
	int distFromWall = 100;
	int distFromGToF = 100;
	Vec2 posOfHero;
	/**
	 * Creats a Camera that will follow the character
	 * @param displaywidth - the width of the screen
	 * @param displayheight - the height of the screen
	 * @param posFocusPixel - the thing you want to focus on
	 */
	public Camera(int displaywidth, int displayheight,Vec2 posFocusPixel){
		this.width = displaywidth;
		this.height = displayheight;
		this.posOfHero = posFocusPixel;
		positionCamera = new Vector2f(0,0);
		x= 0;
		y = 0;
	}
	
	
	public Camera(int displaywidth, int displayheight, int distanceToWall, Vec2 posFocusPixel){
		this(displaywidth, displayheight, posFocusPixel);
	}
	
	public Camera(int displaywidth, int displayheight, Rectangle moveableArea, Vec2 posFocusPixel){
		this(displaywidth, displayheight, posFocusPixel);
		distFromWall = (int) (displaywidth-moveableArea.getWidth())/2;
		distFromGToF = (int) (displayheight - moveableArea.getHeight())/2;
	}
	
	/**
	 * Method that is used to place entitys in the world and not just on the screenwindow
	 * (Pixels)
	 * @param v - the coordinate that is located at the screen in the beginning
	 * @return - the coordinate in the world
	 */
	public static Vec2 entityRender(Vec2 v){
		return new Vec2(v.x -positionCamera.getX(), v.y - positionCamera.getY());
	}
	
	public void updateCamera(Vec2 posFocusPixel){
		posOfHero = posFocusPixel;
		if(posOfHero.x > positionCamera.x+width-distFromWall){
			x = positionCamera.x +(posOfHero.x-(positionCamera.x+width-distFromWall));
		}
		if(posOfHero.y > positionCamera.y+height-distFromGToF){
			y = positionCamera.y +(posOfHero.y-(positionCamera.y+height-distFromGToF));
		}
		if(posOfHero.x < positionCamera.x+distFromWall && posOfHero.x > distFromWall){
			x = positionCamera.x -(positionCamera.x +distFromWall - posOfHero.x);
		}
		if(posOfHero.y < positionCamera.y+distFromGToF && posOfHero.y > distFromGToF){
			y = positionCamera.y -(positionCamera.y +distFromGToF - posOfHero.y);
		}
		positionCamera.set(x, y);
	}
	public Vector2f getCameraPosition(){
		return positionCamera;
	}

}
