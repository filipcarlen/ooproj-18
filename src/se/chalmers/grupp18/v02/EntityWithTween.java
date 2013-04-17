package se.chalmers.grupp18.v02;

import it.marteEngine.entity.Entity;
import it.marteEngine.tween.Ease;
import it.marteEngine.tween.LinearMotion;
import it.marteEngine.tween.Tween;
import it.marteEngine.tween.Tweener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class EntityWithTween extends Entity {

	private Tweener tweener;

	public EntityWithTween(float x, float y) throws SlickException {
		super(x, y);
		setGraphic(new Image("res/testwall.jpg"));
                // add to this entity a tweener
		tweener = new Tweener();
		// add a new Tween to tweener
		tweener.add(new LinearMotion(x, y, x, y-100, 1, Tween.TweenerMode.PERSIST, 200, Ease.BACK_IN));
                // start tweens into tweener
		tweener.start();		
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		super.update(container, delta);

		// update player position according to tween
		//setPosition(tweener.);
	}
}
