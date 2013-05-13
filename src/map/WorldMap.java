package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import utils.EntityType;

/**
 * A cLass to read a Map from a tmx file
 * 
 * Program to make tmx files is founded on..... 
 * 
 * @author Marcus
 *
 */
public class WorldMap{
	
	World w;
	TiledMap tm;
	//Contains all the shaps that is used for collisions and so on.....
	List<WorldShapes> wsm = new ArrayList<WorldShapes>();
	//HashMap that contains the path and id to pictures 
	HashMap<Integer, Image> pictureName = new HashMap<Integer, Image>();
	//List Containing all images that is needed to load the Map
	List<Image> mapImage = new ArrayList<Image>();
	//Background
	int [][] background;
	
	public WorldMap(World world){
		this(world,  false, null);
	}

	public WorldMap(World world, boolean loadMap, String levelName){
		this.w = world;
		if(loadMap){
			loadMapFromTMX(levelName);
		}		
	}
	
	public void loadAllPictures(String pathName){
		
	}
	
	
	/*
	 * Egna Anteckningar för att göra render bättre 
	 *  * kanske hashmap för att kunna <Integer, wsm> vilket hade gjort att man kan få alla x 
	 *  men gör också så att man renderar alla som är y i den xleden
	 *  * hashmap med hashMap funkar de !? <Integer, HashMap> kolla upp när allt e klar;
	 * 
	 */
	 
	//Loads a file from from the resourses folder with a levelName
	public void loadMapFromTMX(String levelName){
		try {
			//Ladda en mapp
			//skapa en 2d Array
			//kolla upp en punkt
			// ifall punkten inte är använd 
			// använd en metod som skapar worldShapes om det tar slut lixom
			tm = new TiledMap("res/Map/" + levelName + ".tmx");
			background = new int[tm.getWidth()][tm.getHeight()];
			int collision = tm.getLayerIndex("collision");
			int numberOfTiles = 0;
			//Adds all tiles to the list represented by a world shape
			for(int j = 0; j < tm.getHeight(); j++){
				for(int i = 0; i <tm.getWidth(); i ++){
					background[i][j] = tm.getTileId(i, j, collision);
					if(tm.getTileId(i, j, collision) > 0){
						while(tm.getTileId(i, j, collision)> 0){
							if(!pictureName.containsKey(tm.getTileId(i, j, collision)))
								pictureName.put(background[i][j], tm.getTileImage(i, j, collision));
							++numberOfTiles;
							++i;
							background[i][j] = tm.getTileId(i, j, collision);
						}
						addWorldShape(i - numberOfTiles, j, tm.getTileWidth(), tm.getTileHeight(), numberOfTiles);
						numberOfTiles = 0;
					}
				}
			}
		} catch (SlickException e) {
			System.out.println("Couldn't load Map");
			e.printStackTrace();
		}
		
		
		//loadAllPictures("hej");
	}
	
	
	
	public void addWorldShape(float xCoordinate, float yCoordinate, int width, int height, int numberOfTiles){
		wsm.add(new WorldShapes(w, xCoordinate, yCoordinate, width, height, numberOfTiles));
		
	}
	
	public void render(Graphics g){
		for(int i = 0; i < background.length; i ++){
			for(int j = 0; j < background[i].length; j++){
				if(background[i][j] > 0)
					g.drawImage(pictureName.get(background[i][j]), (i * tm.getTileWidth()), j* tm.getTileHeight());
			}
		}
	}
	
	public void render(Graphics g, float x, float y, int width, int height){
		if(width/tm.getTileWidth() > tm.getWidth())
			width = tm.getWidth();
		else
			width = width/tm.getTileWidth();
		if(height/tm.getTileHeight() > tm.getHeight())
			height = tm.getHeight();
		else
			height = height/tm.getTileHeight();
		int sx= (int)x/tm.getTileWidth();
		int sy = (int)y/tm.getTileHeight();
		for(int i = sx; i < (x+width); i ++){
			for(int j = sy; j < (y+height); j++){
				if(background[i][j] > 0)
					g.drawImage(pictureName.get(background[i][j]), 
							((i-sx)*tm.getTileWidth())-((x)%tm.getTileWidth()), 
							(j-sy)* tm.getTileHeight()-((y)%tm.getTileHeight()));
			}
		}
	}
	
	public List<WorldShapes> getListOfShapes(){
		return wsm;
	}
	
	public List<Image> getImages(){
		return mapImage;
	}
}
