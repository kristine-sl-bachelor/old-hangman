package hangman;

import gui.CharNotValid;
import gui.WordNotValid;
import gui.LogIn;

public class Game {

	private XP xp; 
	private Word word; 
	private Score score; 
	private String input; 
	private int wins; 
	private int losses; 
	
	public Game() {
		new LogIn(); 
	}

//	public XP getXp() {
//		return xp;
//	}
//
//	public void setXp(XP xp) {
//		this.xp = xp;
//	}

//	public Word getWord() {
//		return word;
//	}
//
//	public void setWord(Word word) {
//		this.word = word;
//	}

//	public Score getScore() {
//		return score;
//	}
//
//	public void setScore(Score score) {
//		this.score = score;
//	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLosses() {
		return losses;
	}

	public void setLosses(int losses) {
		this.losses = losses;
	}
	
	public void guess() {
		InputCheck check = new InputCheck(input, null);
		
		if(input.length() > 1) {
			if(!check.validWord()) {
				new WordNotValid(); 
			} 
		} else {
			if(!check.validChar()) {
				new CharNotValid(); 
			}
		}
		
		
	}
}
