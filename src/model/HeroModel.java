package model;

import utils.Navigation;
import utils.Utils;
import utils.WeaponType;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.newdawn.slick.geom.Vector2f;

public class HeroModel implements IAliveModel{
	
	/* Hero Weapon*/
	private AbstractWeaponModel weapon;
	
	private int maxHp = 100;
	private int hp = maxHp;
	
	/* A Integer to count the number of jumps*/
	private int doubleJump= 0;
	
	/* Integer to count kills and Coins*/
	private int killCount = 0;
	private int score = 0;
	
	/* Loads the amoun of coins and gems*/
	private int coinAmount= 0;
	private int gemAmount = 0;
	
	/* The Dimension of the hero*/
	private float width;
	private float height;
	
	/* Boolean to check if the model is alive or dead*/
	private boolean dead = true;
	
	private Body body;
	
	private Navigation direction;
	
	private WeaponType weapontype = null;
	
	private String characterName;
	
	public HeroModel(World w, String characterName){
		this(w, characterName, new Vector2f(0,0), 50, 50, null);
	}
	
	public HeroModel(World w, String characterName, AbstractWeaponModel awm){
		this(w, characterName, new Vector2f(0,0), 50, 50, awm);
	}

	public HeroModel(World w, String characterName, Vector2f pos, int width, int height, AbstractWeaponModel weapon){
		this.characterName = characterName;
		
		this.width = width/2/Utils.METER_IN_PIXELS;
		this.height = height/2/Utils.METER_IN_PIXELS;
		
		init(w, pos);
		dead = false;
		/* Gives The hero a weapon*/
		if(weapon != null){
			this.weapon = weapon;
			this.weapontype = weapon.getWeaponType();
		}
	}
	
	/**
	 * Method that tells its weapon to fight.
	 * @return - If your able to fight
	 */
	public boolean attack(){
		return weapon.fight(this, direction);
	}
	
	/**
	 * Adds the value of a coin to your coin count.
	 * @param c - The value of a Coin
	 */
	public void incrementScore(int c){
		score += c;
	}
	
	public void destroyBody(){
		body.getWorld().destroyBody(body);
	}
	
	/**
	 * @return - The Current value of the amount of coin points
	 */
	public int getCollectedCoins(){
		return this.score;
	}
	
	@Override
	public Body getBody(){
		return body;
	}
	
	/**
	 * 
	 * @return - The current amount of coins the hero has collected
	 */
	public int getCoinAmount(){
		return coinAmount;
	}
	
	/**
	 * @return - The Direction that the hero is heading
	 */
	public Navigation getDirection(){
		return direction;
	}

	/**
	 * @return - The value of number of jumps done by hero
	 */
	public int getDoubleJump(){
		return doubleJump;
	}
	
	/**
	 * 
	 * @return - the current amount of gems
	 */
	public int getGemAmount(){
		return gemAmount;
	}
	
	@Override
	public float getHeight(){
		return height*2;
	}
	
	@Override
	public int getHp(){
		return hp;
	}
	
	@Override
	public int getID(){
		return -1;
	}
	
	/**
	 * @return - The number of victims
	 */
	public int getKills(){
		return this.killCount;
	}
	
	@Override
	public int getMaxHp(){
		return maxHp;
	}
	
	/**
	 * @return- The name of the hero
	 */
	public String getName() {
		return characterName;
	}
	
	@Override
	public Vec2 getPosMeters(){
		return body.getPosition();
	}
	
	@Override
	public Vec2 getPosPixels(){
		return body.getPosition().add(new Vec2(width,height).mul(-1)).mul(Utils.METER_IN_PIXELS);
	}
	
	/**
	 * This is a method to locate the position on forehead of the characeter
	 * @return - A position in the top right or left corner
	 */
	public Vec2 getFrontPosPixels(){
		if(direction == Navigation.WEST){
			return body.getPosition().add(new Vec2(width,height).mul(-1)).mul(Utils.METER_IN_PIXELS);
		}else if(direction == Navigation.EAST){
			return body.getPosition().add(new Vec2(-width,height).mul(-1)).mul(Utils.METER_IN_PIXELS);
		}else
			return getPosPixels();
	}
	
	/**
	 * Method to get the weapon
	 * @return - weapon
	 */
	public AbstractWeaponModel getWeapon(){
		return weapon;
	}
	
	/**
	 * This return the type like Gun or Sword as a kind of text
	 * @return
	 */
	public WeaponType getWeaponType(){
		return weapontype;
	}
	
	@Override
	public float getWidth(){
		return width*2;
	}
	
	@Override
	public void hurt(int hpDecrement){
		setHp(getHp()-hpDecrement);
	}
	
	/**
	 * Increments the amount of coins
	 */
	public void incrementCoin(){
		coinAmount +=1;
	}
	
	/**
	 * Increments the amount of gems
	 */
	public void incrementGem(){
		gemAmount +=1;
	}
	
	/**
	 * Increments the jumpcount(call this whenever you push jump button)
	 */
	public void incrementJumps(){
		doubleJump +=1;
	}
	
	/**
	 * Increments the kills you got
	 */
	public void incrementKillCount(){
		killCount += 1;
	}
	
	public void init(World w, Vector2f pos){
		/* Create the Body defination*/
		BodyDef b = new BodyDef();
		b.type = BodyType.DYNAMIC;
		b.position.set(pos.x/Utils.METER_IN_PIXELS , pos.y/Utils.METER_IN_PIXELS );
		b.angle = MathUtils.PI;
		/* Creating the structure*/
		PolygonShape pg = new PolygonShape();
		pg.setAsBox(this.width, this.height);
		/* The Fixture*/
		FixtureDef fd = new FixtureDef();
		fd.filter.categoryBits = 2;
		fd.filter.maskBits = 333;
		fd.density = 0.1f;
		fd.friction = 0.0f;
		fd.restitution = 0.0f;
		fd.shape = pg;
		/* Creating an body in the world and applying a Fixture to the body*/
		body = w.createBody(b);
		body.createFixture(fd);
		body.setUserData(this);
		body.setFixedRotation(true);
	}

	/**
	 * Method to se if you are dead or not
	 * @return - true if your dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * This is a method to call whenever you get a contact with ground
	 */
	public void setGroundContact(){
		doubleJump= 0;
	}
	
	/**
	 * Method to update the Direction
	 * @param n - The new Direction
	 */
	public void setDirection(Navigation n){
		this.direction = n;
	}
	
	@Override
	public void setHp(int hp){
		if(hp <= 0){
			dead= true;
		}else if(hp >getMaxHp()){
			this.hp = getMaxHp();
		}else
			this.hp = hp;
	}
	
	/**
	 * This method sets the Weapon
	 * @param w - The Weapon
	 * @param wt - Which type your weapon is
	 */
	public void setWeapon(AbstractWeaponModel w, WeaponType wt){
		this.weapon = w;
		this.weapontype = wt;
	}
	
}
