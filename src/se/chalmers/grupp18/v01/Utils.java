package se.chalmers.grupp18.v01;

public class Utils {
	
	public static float PIXEL_IN_METERS = 1f/30f;
	public static float METER_IN_PIXELS = 30f;
	
	public static float pixelsToMeters(float pixels) {
		return pixels*PIXEL_IN_METERS;
	}
	
	public static float metersToPixels(float meters) {
		return meters*METER_IN_PIXELS;
	}

}
