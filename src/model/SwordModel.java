package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import utils.Navigation;
import utils.Utils;
import utils.WeaponType;

/** A class representing a Sword
 * 
 * @author elinljunggren
 * @version 1.0 
 */

public class SwordModel extends AbstractWeaponModel implements IEntityModel, ActionListener{

	private Timer timer;
	private Body body;
	private Vec2 firstPos;
	private Navigation navigation;
	private boolean isAlive;
	private boolean isMoving;
	int ID;
	
	Vec2[] vertices = {new Vec2(0,0), new Vec2(0.1f, 0.05f), new Vec2(0, 0.1f)};
	
	public final float RADIUS = 0.15f;
	
	public SwordModel(World world, int reloadTime, int damage, int ID){
		super(world, damage, 1f, WeaponType.SWORD);
		this.ID = ID;
		this.timer = new Timer(reloadTime, this);

	}
	
	public void init(Vec2 firstPos){
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DYNAMIC;
		bd.gravityScale = 0;

		this.firstPos = firstPos;
		
		if(this.navigation == Navigation.EAST){
			this.firstPos.x += this.RADIUS + 0.2f;
			bd.position.set(firstPos.x, firstPos.y);
			
		} else if(this.navigation == Navigation.WEST){
			this.firstPos.x -= (this.RADIUS + 0.2f);
			bd.position.set(firstPos.x, firstPos.y);
		}
		
		PolygonShape ps = new PolygonShape();
		ps.set(vertices, 3);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.4f;
		fd.friction = 0f;
		fd.restitution = 0.5f;
		fd.filter.maskBits = 555;
		fd.filter.categoryBits = 4;
		
		this.body = getWorld().createBody(bd);
		this.body.createFixture(fd);
		this.body.setUserData(this);
		
	}
	
	@Override
	public boolean fight(IAliveModel fighterModel, Navigation navigation) {
		super.setFighterModel(fighterModel);
		Vec2 firstPos = fighterModel.getPosMeters().clone();
		
		if(navigation == Navigation.EAST){
			firstPos.x += fighterModel.getWidth()/2;
		}
		
		if(navigation == Navigation.WEST){
			firstPos.x -= fighterModel.getWidth()/2;
		}
		
		if(!timer.isRunning()){
			if(!isAlive){
				this.isAlive = true;
				this.navigation = navigation;
				init(firstPos);	
				timer.start();
				return true;
			}
		}
		return false;
	}
	
	public void destroyEntity(){
		this.isAlive = false;
		this.isMoving = false;
	}

	public boolean isAlive(){
		return isAlive;
	}
	
	public void setAlive(boolean isAlive){
		this.isAlive = isAlive;
	}
	
	public boolean isMoving(){
		return isMoving;
	}
	
	public void setMoving(boolean moving){
		this.isMoving = moving;
	}
	
	public Vec2 getFirstPos(){
		return this.firstPos;
	}
	
	@Override
	public Vec2 getPosMeters() {
		return this.body.getPosition();
	}
	
	@Override
	public Vec2 getPosPixels() {
		return Utils.metersToPixels(this.body.getPosition());
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	public void setBody(Body body){
		this.body = body;
	}
	
	public Navigation getNavigation(){
		return this.navigation;
	}
	
	@Override
	public int getID() {
		return this.ID;
	}
	
	@Override
	public float getHeight() {
		return this.RADIUS*2;
	}
	
	@Override
	public float getWidth() {
		return this.RADIUS*2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.timer.stop();		
	}

}
