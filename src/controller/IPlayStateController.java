package controller;

import model.HeroModel;

public interface IPlayStateController {
	
	public HeroModel getHeroModel();
		
	public void removeEntity(int ID);
	
}
