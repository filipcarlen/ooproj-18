package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.*;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import utils.Utils;

/**
 * A class to read a Map from a tmx file, and then create the bodies for everything in the world,
 * and store all the pictures.
 * 
 * Program to make tmx files is founded on http://www.mapeditor.org/
 * 
 * @author Project Group 18 (Chalmers, 2013)
 *
 */
public class WorldMap{
	
	private World w;
	
	private TiledMap tiledmap;
	
	//Contains all the shaps that is used for collisions and so on.....
	private List<WorldShape> listOfWorldShapes = new ArrayList<WorldShape>();
	
	//HashMap that contains the path and id to pictures 
	private HashMap<Integer, Image> pictureName = new HashMap<Integer, Image>();
	
	private String levelName;
	
	private Rectangle goallocationrect;
	
	//Background
	private int [][] background;
	private Vec2 positionHero;
	
	private List<IEntity> bodies = new ArrayList <IEntity>();

	
	public WorldMap(World world){
		this(world,  false, null);
	}

	public WorldMap(World world, boolean loadMap, String levelName){
		this.levelName = levelName;
		this.w = world;
		if(loadMap){
			loadMapFromTMX(levelName);
		}		
	}
	
	//Loads a file from from the resourses folder with a levelName
	public void loadMapFromTMX(String levelName){
		try {
			tiledmap = new TiledMap("res/map/" + levelName + ".tmx");
			background = new int[tiledmap.getWidth()][tiledmap.getHeight()];
			int collision = tiledmap.getLayerIndex("collision");
			int numberOfTiles = 0; 							//number to keep track of how many tile that have been a added in arow
			int idtile;
			int id = 1;
			List<Vec2> goallocation = new ArrayList<Vec2>();
			Vec2 pos;
			//Adds all tiles to the list represented by a world shape
			for(int j = 0; j < tiledmap.getHeight(); j++){
				for(int i = 0; i <tiledmap.getWidth(); i ++){
					background[i][j] = tiledmap.getTileId(i, j, collision);
					pos = new Vec2(i*tiledmap.getTileWidth(), j*tiledmap.getTileHeight());
					idtile = tiledmap.getTileId(i, j, collision);
					/* If the tile is a world entity that you want to collide with 
					 * this will create a body along the x axis(until if finds a new 
					 * tile that isn't a tile you able you collide with), here it uses the number of tiles
					 * and each time the next tile is a tile that your able you collide with 
					 * it will increment by one*/
					if(isWorldTile(idtile)){
						while(isWorldTile(idtile) && i < tiledmap.getWidth()-1){
							if(!pictureName.containsKey(tiledmap.getTileId(i, j, collision)))
								pictureName.put(idtile, tiledmap.getTileImage(i, j, collision));
							++numberOfTiles;
							++i;
							idtile = tiledmap.getTileId(i, j, collision);
							pos = new Vec2(i*tiledmap.getTileWidth(), j*tiledmap.getTileHeight());
							background[i][j] = tiledmap.getTileId(i, j, collision);
						}
						addWorldShape(i - numberOfTiles, j, tiledmap.getTileWidth(), tiledmap.getTileHeight(), numberOfTiles);
						numberOfTiles = 0;
					}
					if(idtile < 61 && !isWorldTile(idtile)){
						if(!pictureName.containsKey(tiledmap.getTileId(i, j, collision)))
							pictureName.put(idtile, tiledmap.getTileImage(i, j, collision));
						
					}else if(idtile <91 && idtile > 80){
						if(!pictureName.containsKey(tiledmap.getTileId(i, j, collision))){
							pictureName.put(idtile, tiledmap.getTileImage(i, j, collision));
						}
						goallocation.add(new Vec2(i*tiledmap.getTileWidth(), j*tiledmap.getTileHeight()));
					}else if(idtile == 91){
						positionHero = new Vec2(i*tiledmap.getTileWidth()+(tiledmap.getTileWidth()/2), j*tiledmap.getTileHeight()+ (tiledmap.getTileHeight()/2));
					}else if(idtile == 92){
						bodies.add(new MovingFoe(w, pos, 50, new Sword(w, 1000 ,20, id), 5, id));
					}else if(idtile == 93){
						bodies.add(new MovingFoe(w, pos, 50, new Gun(w, 1000, 5, 10, id), 5, id));
					}else if(idtile == 94){
						bodies.add(new StaticFoeFire(w, pos, 15, id));
					}else if(idtile == 95){
						bodies.add(new ChocolateBar(w, pos, id));
					}else if(idtile == 96){
						bodies.add(new EnergyDrink(w, pos, id));
					}else if(idtile == 97){
						bodies.add(new StaticFoePlant(w, pos, 20, id));
					}else if(idtile == 99){
						bodies.add(new Coin(w, pos, id));
					}else if(idtile == 100){
						bodies.add(new Gem(w, pos, id));
					}
					++id;
				}
			}
			goallocationrect = new Rectangle(goallocation.get(0).x, 
					goallocation.get(0).y,
					goallocation.get(1).x-goallocation.get(0).x+tiledmap.getTileWidth(), 
					goallocation.get(goallocation.size()-1).y- goallocation.get(0).y+tiledmap.getTileHeight());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method creats worldshapes
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param width
	 * @param height
	 * @param numberOfTiles - this is a variable to know how many tiles that has been the same row and that is added into one worldshape
	 */
	private void addWorldShape(float xCoordinate, float yCoordinate, int width, int height, int numberOfTiles){
		listOfWorldShapes.add(new WorldShape(w, xCoordinate, yCoordinate, width, height, numberOfTiles));
		
	}
	
	public void render(Graphics g){
		for(int i = 0; i < background.length; i ++){
			for(int j = 0; j < background[i].length; j++){
				if(background[i][j] > 0)
					g.drawImage(pictureName.get(background[i][j]), (i * tiledmap.getTileWidth()), j* tiledmap.getTileHeight());
			}
		}
	}
	
	/**
	 * This method is used to only load and the create bodys that is wall ground and roof ,
	 * this checks is the id is the id on the terrain in the world
	 * (This is useful to separate the world objects from the picture)
	 * @param id
	 * @return
	 */
	private boolean isWorldTile(int id){
		if(id > 0){
			if(id < 21){
				return true;
			}else if(id < 51){
				if(((double)(id-1)/5 % 2 < 1)){
					return true;
				}else{
					return false;
				}
			}else if(id < 61){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public void render(Graphics g, float x, float y, int width, int height){
		if(width/tiledmap.getTileWidth() > tiledmap.getWidth())
			width = tiledmap.getWidth();
		else
			width = (width/tiledmap.getTileWidth()) +1;
		if(height/tiledmap.getTileHeight() > tiledmap.getHeight())
			height = tiledmap.getHeight();
		else
			height = (height/tiledmap.getTileHeight())+1;
		int sx= (int)x/tiledmap.getTileWidth();
		int sy = (int)y/tiledmap.getTileHeight();
		for(int i = sx; i < (sx+width); i ++){
			for(int j = sy; j < (sy+height); j++){
				System.out.println(height);
				if(background[i][j] < 91 && background[i][j] > 0)
					g.drawImage(pictureName.get(background[i][j]), 
							((i-sx)*tiledmap.getTileWidth())-((x)%tiledmap.getTileWidth()), 
							(j-sy)* tiledmap.getTileHeight()-((y)%tiledmap.getTileHeight()));
			}
		}
	}
	
	public void setBounds(){
		listOfWorldShapes.add(new WorldShape(w, 0, 0, tiledmap.getWidth()*tiledmap.getTileWidth(), tiledmap.getHeight()*tiledmap.getTileHeight(), 1));
	}
	
	public List<IEntity> getListOfBodies(){
		return bodies;
	}
	
	public Vec2 getHeroPosition(){
		return positionHero;
	}
	
	public int getWorldWidth(){
		return tiledmap.getWidth()*tiledmap.getTileWidth();
	}
	
	public int getWorldHeight(){
		return tiledmap.getHeight()*tiledmap.getTileHeight();
	}
	
	public List<WorldShape> getListOfShapes(){
		return listOfWorldShapes;
	}
	
	public String getMapName(){
		return levelName;
	}
	
	public boolean isInGoalArea(Vec2 posInMeter){
		Vec2 tmp = posInMeter.mul(Utils.METER_IN_PIXELS);
		return goallocationrect.contains(tmp.x, tmp.y);
	}

	public void destroyWorld() {
		for(int i=listOfWorldShapes.size()-1 ; i >= 0; i--){
			listOfWorldShapes.get(i).destroyBody();
			listOfWorldShapes.remove(i);
		}
		
	}
}
