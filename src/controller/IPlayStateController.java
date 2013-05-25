package controller;

import model.Hero;

public interface IPlayStateController {
	
	public Hero getHeroModel();
		
	public void removeEntity(int ID);
	
}
