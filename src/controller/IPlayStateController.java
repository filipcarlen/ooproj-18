package controller;

import model.HeroModel;

public interface IPlayStateController {

	public HeroController getHeroController();
	
	public HeroModel getHeroModel();
	
	public void removeHero();
	
	public void removeEntity();
	
}
