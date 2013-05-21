package model;

import java.io.Serializable;

public class HighscoreModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private int totalScore;
	private int gems;
	private int coins;
	private int mobs;
	private String name;
	
	public HighscoreModel(String name, int totalScore, int coins, int gems, int mobs){
		this.name = name;
		this.totalScore = totalScore;
		this.coins = coins;
		this.gems = gems;
		this.mobs = mobs;
	}
	
	public int getTotalScore(){
		return totalScore;
	}
	
	public int getGems(){
		return gems;
	}
	
	public int getCoins(){
		return coins;
	}
	
	public int getMobs(){
		return mobs;
	}
	
	public String getName(){
		return name;
	}

}
