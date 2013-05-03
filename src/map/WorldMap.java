package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jbox2d.dynamics.World;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

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
	//Contains all the shaps that is used for collisions and so on.....
	List<WorldShapes> wsm = new ArrayList<WorldShapes>();
	//HashMap that contains the path and id to pictures 
	HashMap<Integer, Image> pictureName = new HashMap<Integer, Image>();
	//List Containing all images that is needed to load the Map
	List<Image> mapImage = new ArrayList<Image>();
	
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
		TiledMap tm;
		try {
			tm = new TiledMap("res/Map/" + levelName + ".tmx");
			int collision = tm.getLayerIndex("collision");
			//Adds all tiles to the list represented by a world shape
			for(int i = 0; i < tm.getWidth(); i++){
				for(int j = 0; j <tm.getHeight(); j ++){
					if(tm.getTileId(i, j, collision) > 0){
						addWorldShapesModel(i, j, tm.getTileWidth(), 0, tm.getTileId(i, j, collision));
						pictureName.put(tm.getTileId(i, j, collision), tm.getTileImage(i, j, collision));
					}
				}
			}
		} catch (SlickException e) {
			System.out.println("Couldn't load Map");
			e.printStackTrace();
		}
		
		
		//loadAllPictures("hej");
	}
	
	public void addWorldShapesModel(float xCoordinate, float yCoordinate, int width, int height, int id){
		wsm.add(new WorldShapes(w, xCoordinate, yCoordinate, width, height, id));
		
	}
	
	public void render(Graphics g){
		for(int i = 0; i < wsm.size(); i++){
			g.drawImage(pictureName.get(wsm.get(i).getId()), wsm.get(i).getPosPixels().x, wsm.get(i).getPosPixels().y);
		}
	}
	
	public List<WorldShapes> getListOfShapes(){
		return wsm;
	}
	
	public List<Image> getImages(){
		return mapImage;
	}
}
