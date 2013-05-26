package utils;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Image;

public class Utils {
	
	public static float PIXEL_IN_METERS = 1f/30f;
	public static float METER_IN_PIXELS = 30f;
	
	/**
	 * Converts a vector in pixels to a vector in meters.
	 * @param pixels	the position in pixels
	 * @return			the given position in meters
	 */
	public static Vec2 pixelsToMeters(Vec2 pixels) {
		return pixels.mul(PIXEL_IN_METERS);
	}
	
	/**
	 * Converts a vector in meters to a vector in pixels.
	 * @param meters	the position in meters
	 * @return			the given position in pixels
	 */
	public static Vec2 metersToPixels(Vec2 meters) {
		return meters.mul(METER_IN_PIXELS);
	}
	
	/**
	 * Converts a number in pixels to a number in pixels.
	 * @param pixels	the number of pixels
	 * @return			the given number of pixels in meters
	 */
	public static float pixelsToMeters(float pixels) {
		return pixels*PIXEL_IN_METERS;
	}
	
	/**
	 * Converts a number in meters to a number in pixels.
	 * @param meters	the number of meters
	 * @return			the given number of meters in pixels
	 */
	public static float metersToPixels(float meters) {
		return meters*METER_IN_PIXELS;
	}
	
	/**
	 * Checks if the mouse is inside an image.
	 * @param mouseX	the x-coordinate of the mouse
	 * @param mouseY	the y-coordinate of the mouse
	 * @param imagePos	the position of the image
	 * @param image		the image
	 * @return
	 */
	public static boolean isMouseInsideImage(float mouseX, float mouseY, Vec2 imagePos, Image image, float resizepic){
		if((mouseX >= imagePos.x && mouseX <= imagePos.x + image.getWidth()*resizepic) &&
	            (mouseY >= imagePos.y && mouseY <= imagePos.y + image.getHeight()*resizepic)){
					return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Checks if the mouse is inside an area.
	 * @param mouseX	the x-coordinate of the mouse
	 * @param mouseY	the y-coordinate of the mouse
	 * @param pos		the start position of the area
	 * @param width		the width of the area
	 * @param height	the height of the area
	 * @return
	 */
	public static boolean isMouseInsideArea(float mouseX, float mouseY, Vec2 pos, float width, float height){
		if((mouseX >= pos.x && mouseX <= pos.x + width) &&
	            (mouseY >= pos.y && mouseY <= pos.y + height)){
					return true;
		}
		else{
			return false;
		}
	}
}
