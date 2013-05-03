package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class BulletModel implements IEntityModel{

	private int range;
	private int damage;
	private Body body;
	private World world;
	
	public BulletModel(){
		this.range = 400;
		this.damage = 20;
	}
	public BulletModel(int range){
		this();
		this.range = range;
	}
	public BulletModel(int range, int damage){
		this(range);
		this.damage = damage;
	}
	
	
	public int getDamage(){
		return damage;
	}
	public void setDamage(int damage){
		this.damage = damage;
	}
	public int getRange(){
		return range;
	}
	public void setRange(int range){
		this.range = range;
	}
	public void fight(){
		
	}
	@Override
	public Vec2 getPosMeters() {
		return null;
	}
	@Override
	public Vec2 getPosPixels() {
		return null;
	}
	@Override
	public Body getBody() {
		return null;
	}
}
