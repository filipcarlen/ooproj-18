package controller;

import model.HeroModel;

public interface IPlayStateController {

	public HeroController getHeroController();
	
	public HeroModel GetHeroModel();
	
	public void removeHero();
	
	public void removeEntity();
	
}
