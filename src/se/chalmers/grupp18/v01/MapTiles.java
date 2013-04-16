package se.chalmers.grupp18.v01;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MapTiles extends TiledMap{
	
	private int background, collision, object;
	
	private static final String path = "res/Map/";
	
	/**
	 * Creat a matrix over the tiles and screen
	 * @param ref - The Reference to which map we want to load
	 * @param loadTileSets - a boolean that decides if we want to load our tilesets
	 * @throws SlickException
	 */
	public MapTiles(String ref, boolean loadTileSets) throws SlickException{
		super(path + ref + ".tmx", loadTileSets);
		background = this.getLayerIndex("background");
		collision = this.getLayerIndex("collision");
		object = this.getLayerIndex("object");
	}
	
	/**
	 * Only render the layer Background
	 * @param sx
	 * @param sy
	 * @param width
	 * @param height
	 */
	public void renderBackground(int sx, int sy, int width, int height){
		this.render(0, 0, sx, sy, width, height, background, true);
	}
	
	/**
	 * Only render the layer Collision
	 * @param sx
	 * @param sy
	 * @param width
	 * @param height
	 */
	public void renderCollision(int sx, int sy, int width, int height){
		this.render(0, 0, sx, sy, width, height, collision, true);
	}
	
	/**
	 * Only render the layer Object
	 * @param sx
	 * @param sy
	 * @param width
	 * @param height
	 */
	public void renderObject(int sx, int sy, int width, int height){
		this.render(0, 0, sx, sy, width, height, object, true);
	}
	
	/**
	 * checks if the position is in a tile that is in the layer collision
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isColliding(float x, float y){
		if(this.getTileId((int)x, (int)y, collision) == 3){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @return the x coordinate that is located east of the tile
	 */
	public float eastTile(float x){
		return (x % this.getTileWidth());
	}
	
	/**
	 * 
	 * @param x
	 * @return the x coordinate that is located west of the tile
	 */
	public float westTile(float x){
		return -(x % this.getTileWidth());
	}
	
	/**
	 * 
	 * @param y
	 * @return the y Coordinate that is located on top of the tile
	 */
	public float topOfTile(float y){
		return -(y % this.getTileHeight());
	}

}