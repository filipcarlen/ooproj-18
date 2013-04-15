package se.chalmers.grupp18.v01;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MapTiles extends TiledMap{
	
	private int background, collision, object;
	
	private static final String path = "res/Map/";
	
	
	public MapTiles(String ref, boolean loadTileSets) throws SlickException{
		super(path + ref + ".tmx", loadTileSets);
		background = this.getLayerIndex("background");
		collision = this.getLayerIndex("collision");
		object = this.getLayerIndex("object");
	}
	
	public void renderBackground(int sx, int sy, int width, int height){
		this.render(0, 0, sx, sy, width, height, background, true);
	}
	
	public void renderCollision(int sx, int sy, int width, int height){
		this.render(0, 0, sx, sy, width, height, collision, true);
	}
	
	public void renderObject(int sx, int sy, int width, int height){
		this.render(0, 0, sx, sy, width, height, object, true);
	}

}