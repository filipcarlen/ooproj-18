package model;
import java.util.Comparator;

import utils.Highscore;
/**
 * Class that compares different highscores with each other
 * @author group 18
 *
 */
public class HighscoreComparator implements Comparator<Highscore> {
	
	public int compare(Highscore score1, Highscore score2){
		
		int hs1 = score1.getTotalScore();
		int hs2 = score2.getTotalScore();
		
		if(hs1 > hs2){
			return -1;
		}
		else if(hs2 > hs1){
			return 1;
		}
		else{
			return 0;
		}
	}
}
