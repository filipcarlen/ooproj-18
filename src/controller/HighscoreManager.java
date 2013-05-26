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
	
	/**
	 * This method makes a list of arrays, an array contains a name, a number of coins, a number of foes killed, 
	 * a number of gems and a total score and there's an array for every high score.
	 * @return a list of arrays with strings
	 */
	public List<String[]> getHighScores() {
		List<String[]> strings = new ArrayList<String[]>();
		String[] highScore = new String[5];
		
		int max = 0;
		
		List<Highscore> highScores = getScores();
		if(highScores.size() < 8) {
			max = highScores.size();
		} else {
			max = 8;
		}
		
		for(int i = 0; i < max; i++) {
			highScore = new String[5];
			highScore[0] = highScores.get(i).getName();
			highScore[1] = "" + highScores.get(i).getCoins();
			highScore[2] = "" + highScores.get(i).getMobs();
			highScore[3] = "" + highScores.get(i).getGems();
			highScore[4] = "" + highScores.get(i).getTotalScore();
			strings.add(highScore);
		}
		
		return strings;
	}
	
	public void clearFile() throws IOException{
		highscores.clear();
		updateScoreFile();
	}
}	
