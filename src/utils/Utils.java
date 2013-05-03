package utils;

import org.jbox2d.common.Vec2;

public class Utils {
	
	public static float PIXEL_IN_METERS = 1f/30f;
	public static float METER_IN_PIXELS = 30f;
	
	public static Vec2 pixelsToMeters(Vec2 pixels) {
		return pixels.mul(PIXEL_IN_METERS);
	}
	
	public static Vec2 metersToPixels(Vec2 meters) {
		return meters.mul(METER_IN_PIXELS);
	}

}
