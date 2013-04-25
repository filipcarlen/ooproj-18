package se.chalmers.grupp18.v01;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;

public class EnemyEntity implements IEntityController {
	
	//Private EnemyEntityModel model;
	//Private EnemyEntityView view;
	
	private World world;
	private Vec2 pos;
	private Image img;
	private int hp;
	private Weapon weapon;
	
	private Body body;
	
	private boolean isAlive;
	private int LENGTH;
	
	public EnemyEntity(World world, Vec2 pos, Image img, int hp, Weapon weapon){
		
		init();
		this.world = world;
		this.pos = pos;
		this.img = img;
		this.hp = hp;
		this.weapon = weapon;
		
	}
	
	@Override
	public void init() {
		// This will be where the model and the view are
		// instantiated.
		
		// model = new EnemyEntityModel();
		// view = new EnemyEntityView(model);
		
		this.LENGTH = 600;
		
		this.isAlive = true;
		
		BodyDef bd = new BodyDef();
		bd.position.set(this.pos);
		bd.type = BodyType.DYNAMIC;
		
		PolygonShape ps = new PolygonShape();
		ps.setAsBox(this.pos.x, this.pos.y);
		
		FixtureDef fd = new FixtureDef();
		fd.shape = ps;
		fd.density = 0.5f;
		fd.friction = 0.3f;
		fd.restitution = 0.2f;
		
		body = this.world.createBody(bd);
		body.createFixture(fd);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) {
		
		// Somehow get a hold of the players coordinates...
		Vec2 heroPos = PlayState.getHeroPos();
		Vec2 diffVec = this.body.getPosition().sub(heroPos);
		
		if(diffVec.length() < 600)){
			if(heroPos.x < this.body.getPosition().x){
				this.body.getPosition().x -= 0.4f*delta;
				if(this.weapon.isWithinRange(this.body.getPosition(), heroPos)){
					this.weapon.fight(heroPos);
				}
			}
			if(heroPos.x > this.body.getPosition().x){
				this.body.getPosition().x += 0.4f*delta;
				if(this.weapon.isWithinRange(this.body.getPosition(), heroPos)){
					this.weapon.fight(heroPos);
				}
			}
		}
	}

	

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		img.draw(this.body.getPosition().x, this.body.getPosition().y);
	}

	public void doDamage(int damage){
		this.hp -= damage;
		if(this.hp < 0){
			this.isAlive = false;
			// Play some animation.
		} else {
			// Play some animation.
		}
	}
	
	public boolean isAlive(){
		return isAlive;
	}

	
	public Vec2 getPosition() {
		return this.pos;
	}


	public void setPosition(Vec2 pos) {
		this.pos = pos;
	}
}
