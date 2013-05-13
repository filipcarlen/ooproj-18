package utils;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.geom.Vector2f;

public class Camera {
	int width,height;
	float x, y;
	static Vector2f positionCamera;
	Vec2 posOfHero;
	/**
	 * Creats a Camera that will follow the character
	 * @param displaywidth - the width of the screen
	 * @param displayheight - the height of the screen
	 * @param posFocusPixel - the thing you want to focus on
	 */
	public Camera(int displaywidth, int displayheight, Vec2 posFocusPixel){
		this.width = displaywidth;
		this.height = displayheight;
		this.posOfHero = posFocusPixel;
		positionCamera = new Vector2f(0,0);
		x= 0;
		y = 0;
		updateCamera(posOfHero);
	}
	
	public static Vec2 heroRender(Vec2 v){
		return new Vec2(v.x -positionCamera.getX(), v.y - positionCamera.getY());
	}
	
	public void updateCamera(Vec2 posFocusPixel){
		posOfHero = posFocusPixel;
		if(posOfHero.x > positionCamera.x+width-100){
			x = positionCamera.x +(posOfHero.x-(positionCamera.x+width-100));
		}
		if(posOfHero.y > positionCamera.y+height-100){
			y = positionCamera.y +(posOfHero.y-(positionCamera.y+height-100));
		}
		if(posOfHero.x < positionCamera.x+100 && posOfHero.x > 100){
			x = positionCamera.x -(positionCamera.x +100 - posOfHero.x);
		}
		if(posOfHero.y < positionCamera.y+100 && posOfHero.y > 100){
			y = positionCamera.y -(positionCamera.y +100 - posOfHero.y);
		}
		positionCamera.set(x, y);
	}
	public Vector2f getCameraPosition(){
		return positionCamera;
	}

}
