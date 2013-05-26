package utils;

import java.awt.Rectangle;

import org.jbox2d.common.Vec2;

public class Camera {
	private int displaywidth,displayheight,worldwidth,worldheight;
	private static Vec2 positionCamera = new Vec2(0,0);
	private int distFromWall = 60;
	private int distFromGToF = 60;
	private Vec2 posOfHero;
	/**
	 * Creats a Camera that will follow the character
	 * @param displaywidth - the width of the screen
	 * @param displayheight - the height of the screen
	 * @param posFocusPixel - the thing you want to focus on
	 */
	public Camera(int displaywidth, int displayheight, int worldwidth, int worldheight,Vec2 posFocusPixel){
		this.displaywidth = displaywidth;
		this.displayheight = displayheight;
		this.worldwidth = worldwidth;
		this.worldheight = worldheight;
		this.posOfHero = posFocusPixel;
	}
	
	public Camera(int displaywidth, int displayheight, int worldwidth, int worldheight, Rectangle moveableArea, Vec2 posFocusPixel){
		this(displaywidth, displayheight, worldwidth, worldheight, posFocusPixel);
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
		return new Vec2(v.x -positionCamera.x, v.y - positionCamera.y);
	}
	
	public void updateCamera(Vec2 posFocusPixel){
		posOfHero = posFocusPixel;
		if(posOfHero.x > positionCamera.x+displaywidth-distFromWall){
			if(distFromWall < (worldwidth-posOfHero.x))
				positionCamera.x = positionCamera.x +(posOfHero.x-(positionCamera.x+displaywidth-distFromWall));
			else
				positionCamera.x = worldwidth-1 -displaywidth;
		}
		else if(posOfHero.x < positionCamera.x+distFromWall){
			if( posOfHero.x > distFromWall)
				positionCamera.x = positionCamera.x -(positionCamera.x +distFromWall - posOfHero.x);
			else
				positionCamera.x = 0;
		}
		if(posOfHero.y > positionCamera.y+displayheight-distFromGToF){
			if(distFromGToF <(worldheight- posOfHero.y))
				positionCamera.y = positionCamera.y +(posOfHero.y-(positionCamera.y+displayheight-distFromGToF));
			else
				positionCamera.y = worldheight-1-displayheight;
		}else if(posOfHero.y < positionCamera.y+distFromGToF){
			if(posOfHero.y > distFromGToF)
				positionCamera.y = positionCamera.y -(positionCamera.y +distFromGToF - posOfHero.y);
			else
				positionCamera.y = 0;
		}
	}
	
	public void resetCamera(){
		positionCamera.set(0,0);
	}
	
	public Vec2 getCameraPosition(){
		return positionCamera;
	}

}
