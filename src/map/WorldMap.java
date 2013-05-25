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
 * A cLass to read a Map from a tmx file
 * 
 * Program to make tmx files is founded on..... 
 * 
 * @author Marcus
 *
 */
public class WorldMap{
	
	private World w;
	private TiledMap tm;
	//Contains all the shaps that is used for collisions and so on.....
	private List<WorldShape> wsm = new ArrayList<WorldShape>();
	//HashMap that contains the path and id to pictures 
	private HashMap<Integer, Image> pictureName = new HashMap<Integer, Image>();
	//List Containing all images that is needed to load the Map
	private List<Image> mapImage = new ArrayList<Image>();
	private String levelName;
	
	private Rectangle goallocationrect;
	
	//Background
	private int [][] background;
	private Vec2 positionHero;
	
	private ArrayList<IEntity> bodies = new ArrayList <IEntity>();

	
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
			tm = new TiledMap("res/map/" + levelName + ".tmx");
			background = new int[tm.getWidth()][tm.getHeight()];
			int collision = tm.getLayerIndex("collision");
			int numberOfTiles = 0;
			int idtile;
			int id = 1;
			List<Vec2> goallocation = new ArrayList<Vec2>();
			Vec2 pos;
			//Adds all tiles to the list represented by a world shape
			for(int j = 0; j < tm.getHeight(); j++){
				for(int i = 0; i <tm.getWidth(); i ++){
					background[i][j] = tm.getTileId(i, j, collision);
					pos = new Vec2(i*tm.getTileWidth(), j*tm.getTileHeight());
					idtile = tm.getTileId(i, j, collision);
					if(isWorldTile(idtile)){
						while(isWorldTile(idtile) && i < tm.getWidth()-1){
							if(!pictureName.containsKey(tm.getTileId(i, j, collision)))
								pictureName.put(idtile, tm.getTileImage(i, j, collision));
							++numberOfTiles;
							++i;
							idtile = tm.getTileId(i, j, collision);
							pos = new Vec2(i*tm.getTileWidth(), j*tm.getTileHeight());
							background[i][j] = tm.getTileId(i, j, collision);
						}
						addWorldShape(i - numberOfTiles, j, tm.getTileWidth(), tm.getTileHeight(), numberOfTiles);
						numberOfTiles = 0;
					}
					if(idtile < 61 && !isWorldTile(idtile)){
						if(!pictureName.containsKey(tm.getTileId(i, j, collision)))
							pictureName.put(idtile, tm.getTileImage(i, j, collision));
						
					}else if(idtile <91 && idtile > 80){
						if(!pictureName.containsKey(tm.getTileId(i, j, collision))){
							pictureName.put(idtile, tm.getTileImage(i, j, collision));
						}
						goallocation.add(new Vec2(i*tm.getTileWidth(), j*tm.getTileHeight()));
					}else if(idtile == 91){
						positionHero = new Vec2(i*tm.getTileWidth()+(tm.getTileWidth()/2), j*tm.getTileHeight()+ (tm.getTileHeight()/2));
					}else if(idtile == 92){
						bodies.add(new MovingFoe(w, pos, 50, new Sword(w, 200 ,35, id), 5, id));
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
					goallocation.get(1).x-goallocation.get(0).x+tm.getTileWidth(), 
					goallocation.get(goallocation.size()-1).y- goallocation.get(0).y+tm.getTileHeight());
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void addWorldShape(float xCoordinate, float yCoordinate, int width, int height, int numberOfTiles){
		wsm.add(new WorldShape(w, xCoordinate, yCoordinate, width, height, numberOfTiles));
		
	}
	
	public void render(Graphics g){
		for(int i = 0; i < background.length; i ++){
			for(int j = 0; j < background[i].length; j++){
				if(background[i][j] > 0)
					g.drawImage(pictureName.get(background[i][j]), (i * tm.getTileWidth()), j* tm.getTileHeight());
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
	public boolean isWorldTile(int id){
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
		if(width/tm.getTileWidth() >= tm.getWidth())
			width = tm.getWidth();
		else
			width = (width/tm.getTileWidth()) +1;
		if(height/tm.getTileHeight() >= tm.getHeight())
			height = tm.getHeight();
		else
			height = (height/tm.getTileHeight())+1;
		int sx= (int)x/tm.getTileWidth();
		int sy = (int)y/tm.getTileHeight();
		for(int i = sx; i < (sx+width); i ++){
			for(int j = sy; j < (sy+height); j++){
				if(background[i][j] < 91 && background[i][j] > 0)
					g.drawImage(pictureName.get(background[i][j]), 
							((i-sx)*tm.getTileWidth())-((x)%tm.getTileWidth()), 
							(j-sy)* tm.getTileHeight()-((y)%tm.getTileHeight()));
			}
		}
	}
	
	public void setBounds(){
		wsm.add(new WorldShape(w, 0, 0, tm.getWidth()*tm.getTileWidth(), tm.getHeight()*tm.getTileHeight(), 1));
	}
	
	public ArrayList<IEntity> getListOfBodies(){
		return bodies;
	}
	
	public Vec2 getHeroPosition(){
		return positionHero;
	}
	
	public int getWorldWidth(){
		return tm.getWidth()*tm.getTileWidth();
	}
	
	public int getWorldHeight(){
		return tm.getHeight()*tm.getTileHeight();
	}
	
	public List<WorldShape> getListOfShapes(){
		return wsm;
	}
	
	public List<Image> getImages(){
		return mapImage;
	}
	
	public String getMapName(){
		return levelName;
	}
	
	public boolean isInGoalArea(Vec2 posInMeter){
		Vec2 tmp = posInMeter.mul(Utils.METER_IN_PIXELS);
		return goallocationrect.contains(tmp.x, tmp.y);
	}

	public void destroyWorld() {
		for(int i=0 ; i < wsm.size(); i++){
			wsm.get(i).destroyBody();
			wsm.remove(i);
		}
		
	}
}
