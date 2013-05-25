package model;

import java.awt.Dimension;

import utils.Navigation;
import utils.Utils;
import utils.WeaponType;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;


public class HeroModel implements IAliveModel{
	
	/* Hero Weapon*/
	private AbstractWeaponModel weapon;
	
	private final int maxHp = 100;
	private int hp;
	
	/* A Integer to count the number of jumps*/
	private int doubleJump= 0;
	
	/* Integer to count kills and the scores*/
	private int killCount = 0;
	private int score = 0;
	
	/* Loads the amount of coins and gems*/
	private int coinAmount= 0;
	private int gemAmount = 0;
	
	/* The Dimension of the hero*/
	private float width;
	private float height;
	
	/* Boolean to check if the model is alive or dead*/
	private boolean dead = true;
	
	private boolean hurted = false;
	
	/* This is added only because we want to now if the hurt gets hurt from the back or front*/
	private boolean hurtedFront = false;
	
	private boolean isBodyCreated = false;
	
	private boolean hasLoadedDimension = false;
	
	public enum HealthAction{HURT, DEAD, NONE};
	
	private HealthAction currentHealthAction = HealthAction.NONE;
	
	private Body body;
	
	private World world;
	
	private Navigation direction = Navigation.WEST;
	
	private String characterName;
	
	private String playerName;
	
	public HeroModel(World w, String characterName, Vec2 pos){
		this.world = w;
		this.characterName = characterName;
		this.playerName = "Player";						//Default name on the player is player
		init(pos, true);
	}

	public HeroModel(World w, String characterName, Vec2 pos, int width, int height, AbstractWeaponModel weapon, boolean createHero){
		this.characterName = characterName;
		this.world = w;
		setDimension(new Dimension(width, height));
		if(createHero)
			createNewHero(pos, weapon);
		if(weapon != null){
			this.weapon = weapon;
		}
	}
	
	/**
	 * Method that tells its weapon to fight.
	 * @return - If your able to fight
	 */
	public boolean attack(){
		return weapon.fight(this, direction);
	}
	
	public boolean hasLoadedDimension(){
		return hasLoadedDimension;
	}

	public void createNewHero(Vec2 pos, AbstractWeaponModel awm){
		score = 0;
		coinAmount = 0;
		gemAmount = 0;
		killCount = 0;
		resetHurted();
		hp = maxHp;
		dead = true;
		init(pos, false);
		dead = false;
		setDirection(Navigation.EAST);
	}
	
	/**
	 * This method is used if you want to restart a new/old
	 * track with the same score as you got when you finished the track
	 * @param pos - pos in meters
	 */
	public void continueUseHero(Vec2 pos){
		int gems = this.getGemAmount();
		int coins = this.getCoinAmount();
		int kills = this.getKills();
		int score = this.getScore();
		createNewHero(pos.mul(Utils.METER_IN_PIXELS), weapon);
		this.gemAmount = gems;
		this.coinAmount = coins;
		this.killCount = kills;
		this.score = score;
	}
	
	public void destroyBody(){
		world.destroyBody(body);
		body.setActive(false);
	}

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
	
	public HealthAction getCurrentHealthAction(){
		HealthAction tmp = currentHealthAction;
		currentHealthAction = HealthAction.NONE;
		return tmp;
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
	
	public String getPlayerName(){
		return playerName;
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
	 * @return - The Current value of the amount of coin points
	 */
	public int getScore(){
		return this.score;
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
		return weapon.getWeaponType();
	}
	
	@Override
	public float getWidth(){
		return width*2;
	}
	
	public void heal(int hpIncrement){
		setHp(getHp()+ hpIncrement);
	}
	
	@Override
	public void hurt(int hpDecrement){
		setHp(getHp()-hpDecrement);
		hurted = true;
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
	 * Adds the value of a coin to your coin count.
	 * @param c - The value of a Coin
	 */
	public void incrementScore(int c){
		score += c;
	}

	/**
	 * Increments the kills you got
	 */
	public void incrementKillCount(){
		killCount += 1;
	}
	
	public void init(Vec2 pos, boolean onlyBodyDef){
		/* Create the Body defination*/
		BodyDef b = new BodyDef();
		b.type = BodyType.DYNAMIC;
		b.position.set(pos.x/Utils.METER_IN_PIXELS , pos.y/Utils.METER_IN_PIXELS );
		b.angle = MathUtils.PI;
		body = world.createBody(b);
		if(!onlyBodyDef){
			/* Creating the structure*/
			PolygonShape pg = new PolygonShape();
			pg.setAsBox(this.width, this.height);
			/* The Fixture*/
			FixtureDef fd = new FixtureDef();
			fd.filter.categoryBits = 2;
			fd.filter.maskBits = 333;
			fd.density = 0.15f;
			fd.friction = 0.0f;
			fd.restitution = 0.0f;
			fd.shape = pg;
			/* Creating an body in the world and applying a Fixture to the body*/
			body.createFixture(fd);
			isBodyCreated = true;
		}
		body.setUserData(this);
		body.setFixedRotation(true);

	}
	
	public boolean isBodyCreated(){
		return isBodyCreated;
	}

	/**
	 * Method to se if you are dead or not
	 * @return - true if your dead
	 */
	public boolean isDead() {
		return dead;
	}
	
	public boolean isFalling(){
		return body.m_linearVelocity.y > 0.001;
	}
	
	public boolean isHurted(){
		return hurted;
	}
	
	public boolean isHurtedFront(){
		return hurtedFront;
	}
	
	public boolean isInAir(){
		return doubleJump>0 && !isFalling();
	}
	
	public void resetHurted(){
		hurtedFront = false;
		hurted = false;
	}
	
	public void resurrection(Vec2 pos){
		createNewHero(pos, weapon);
		dead = false;
		body.setActive(true);
	}
	
	/**
	 * Method to update the Direction
	 * @param n - The new Direction
	 */
	public void setDirection(Navigation n){
		this.direction = n;
	}
	
	/**
	 * This is a method to call whenever you get a contact with ground
	 */
	public void setGroundContact(){
		doubleJump= 0;
	}
	
	/**
	 * 
	 * @param dimension - the Dimension of the character in pixels
	 */
	public void setDimension(Dimension dimension){
		this.width = (float) (dimension.getWidth()/2/Utils.METER_IN_PIXELS);
		this.height = (float) (dimension.getHeight()/2/Utils.METER_IN_PIXELS);
		hasLoadedDimension = true;
	}
	
	public void setDimension(int width, int height) {
		setDimension(new Dimension(width, height));
	}
	
	public void setHurted(Navigation direction, int decrementHp){
		hurt(decrementHp);
		if(direction != getDirection()){
			this.hurtedFront = true;
		}
	}
	
	@Override
	public void setHp(int hp){
		if(hp <= 0){
			this.hp = 0;
			dead= true;
			currentHealthAction = HealthAction.DEAD;
		}else if(hp >getMaxHp()){
			this.hp = getMaxHp();
		}else{
			if(this.hp > hp)
				currentHealthAction = HealthAction.HURT;
			this.hp = hp;
		}
	}
	
	public void setPlayerName(String playerName){
		this.playerName = playerName;
	}
	
	/**
	 * This method sets the Weapon
	 * @param w - The Weapon
	 * @param wt - Which type your weapon is
	 */
	public void setWeapon(AbstractWeaponModel w){
		this.weapon = w;
	}
	
}
