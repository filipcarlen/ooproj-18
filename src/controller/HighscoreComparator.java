package controller;
import java.util.Comparator;
import model.HighscoreModel;

public class HighscoreComparator implements Comparator<HighscoreModel> {
	
	public int compare(HighscoreModel score1, HighscoreModel score2){
		
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
