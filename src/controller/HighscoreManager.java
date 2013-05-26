package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import utils.Highscore;

import model.HighscoreComparator;
/**
 * Class managing the highscore list
 * @author group 18
 *
 */
public class HighscoreManager {
	
	private List<Highscore> highscores;
	private static final String HIGHSCORE_FILE = "highscores.dat";
	
	ObjectOutputStream outputStream = null;
	ObjectInputStream inputStream = null;
	
	public HighscoreManager(){
		highscores = new ArrayList<Highscore>();
	}
	
	public List<Highscore> getScores(){
		loadScoreFile();
		sort();
		return highscores;
	}
	
	private void sort(){
		HighscoreComparator comparator = new HighscoreComparator();
		Collections.sort(highscores, comparator);
	}
	
	public void addScore(String name, int totalScore, int coins, int gems, int mobs){
		loadScoreFile();
		highscores.add(new Highscore(name,totalScore,coins,gems,mobs));
		updateScoreFile();
	}
	
	@SuppressWarnings("unchecked")
	public void loadScoreFile(){
		try{
			inputStream = new ObjectInputStream(new FileInputStream(HIGHSCORE_FILE));
			highscores = (ArrayList<Highscore>) inputStream.readObject();
			
		} catch (FileNotFoundException e) {
            System.out.println("[Laad] FNF Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[Laad] IO Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("[Laad] CNF Error: " + e.getMessage());
        } finally {
              	try {
              		if(outputStream != null){
           				outputStream.flush();
           				outputStream.close();
           			}
           		} catch (IOException e) {
              		System.out.println("[Laad] IO Error: " + e.getMessage());
              	}
        }
	}

	public void updateScoreFile(){
		try{
			outputStream = new ObjectOutputStream(new FileOutputStream(HIGHSCORE_FILE));
			outputStream.writeObject(highscores);
		} catch (FileNotFoundException e) {
			System.out.println("[Update] FNF Error: " + e.getMessage() + ",the program will try and make a new file");
		} catch (IOException e) {
			System.out.println("[Update] IO Error: " + e.getMessage());
		} finally {
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException e) {
				System.out.println("[Update] Error: " + e.getMessage());
			}
		}
	}
	
	public String getHighscoreString(){
		String highscoreString = "";
		int max = 8;
		
		List<Highscore> highscores;
		highscores = getScores();
		int x = highscores.size();
		if(x>max){
			x = max;
		}
		
		for (int i = 0; i<x; i++){
			String name = highscores.get(i).getName();
			String coins = ""+highscores.get(i).getCoins();
			String gem = ""+highscores.get(i).getGems();
			String mob = ""+highscores.get(i).getMobs();
			String score = ""+highscores.get(i).getTotalScore();
			String format = "%-20s" +"%-"+(20-name.length())+"s " + "%-"+(15-gem.length())+"s "
						+"%-"+(15-gem.length())+"s " + "%-"+(15-mob.length())+"s "
						+"%s";
			highscoreString +=String.format(format,(i+1),name,coins,gem,mob,score+"\n");
		}	
		return highscoreString;
	}
	
	public void clearFile() throws IOException{
		highscores.clear();
		updateScoreFile();
	}
}	
