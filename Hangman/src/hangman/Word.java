package hangman;

public class Word {
	
	private String word; 
	
	public Word() {
		this(null); 
	}
	
	public Word(String word) {
		setWord(word); 
	}
	
	public String getWord() {
		return word; 
	}
	
	public void setWord(String word) {
		this.word = word; 
	}
	
	public void addToDB() {
		
	}
	
	/**
	 * Selects word from database 
	 * @param random: Decides whether or not the word is a random one. 
	 */
	public void getFromDB(boolean random) {
		
		if(random) {
			//random word
		} else {
			// get spesific word
		}
	}
}
