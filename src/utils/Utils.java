package utils;

import org.jbox2d.common.Vec2;

public class Utils {
	
	public static float PIXEL_IN_METERS = 1f/30f;
	public static float METER_IN_PIXELS = 30f;
	
	/**
	 * @param pixels	the position in pixels
	 * @return			the given position in meters
	 */
	public static Vec2 pixelsToMeters(Vec2 pixels) {
		return pixels.mul(PIXEL_IN_METERS);
	}
	
	/**
	 * @param meters	the position in meters
	 * @return			the given position in pixels
	 */
	public static Vec2 metersToPixels(Vec2 meters) {
		return meters.mul(METER_IN_PIXELS);
	}
	
	/**
	 * @param pixels	the number of pixels
	 * @return			the given number of pixels in meters
	 */
	public static float pixelsToMeters(float pixels) {
		return pixels*PIXEL_IN_METERS;
	}
	
	/**
	 * @param meters	the number of meters
	 * @return			the given number of meters in pixels
	 */
	public static float metersToPixels(float meters) {
		return meters*METER_IN_PIXELS;
	}
}
