package model;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class BulletModel implements IEntityModel{

	private float range;
	private int damage;
	private Body body;
	private World world;
	
	public BulletModel(){
		this.range = 400f;
		this.damage = 20;
	}
	public BulletModel(float range){
		this(20, range);
	}
	public BulletModel(int damage, float range){
		this.range = range;
		this.damage = damage;
	}
	
	
	public int getDamage(){
		return damage;
	}
	public void setDamage(int damage){
		this.damage = damage;
	}
	public float getRange(){
		return range;
	}
	public void setRange(float range){
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
