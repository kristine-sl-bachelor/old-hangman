package hangman;

public class InputCheck {

	private String input;
	private String word;

	public InputCheck() {
		this(null, null); 
	}
	
	public InputCheck(String input, String word) {
		setInput(input); 
		setWord(word); 
	}

	public String wordOrChar() {
		return "";
	}

	/**
	 * Get-method for input
	 * 
	 * @return Returns input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * Get-method for word
	 * 
	 * @return Returns word
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Set-method for input
	 * 
	 * @param input
	 *            : Value for input to be checked
	 */
	public void setInput(String input) {
		this.input = input;
		input = input.toLowerCase().trim(); 
	}

	/**
	 * Set-method for word
	 * 
	 * @param word
	 *            : Value for word to be checked against
	 */
	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * Checks to see whether or not the character is valid. Checks against all
	 * the letters in the alphabet.
	 * 
	 * @return Returns true if the character is valid.
	 */
	public boolean validChar() {
		String validChars = "abcdefghijklmnopqrstuvwxyz";

		if (!validChars.contains(input)) {
			return false;
		}

		return true;
	}

	/**
	 * Checks to see whether or not the word is valid. Checks all the characters
	 * in the word with validChar();
	 * 
	 * @return Returns true if the word is valid.
	 */
	public boolean validWord() {
		input = input.trim();
		String[] characters = input.split("");

		// Checks all characters in the word
		for (int i = 0; i < characters.length; i++) {
			InputCheck test = new InputCheck();
			test.setInput(characters[i]); 
			if (!test.validChar()) {
				return false;
			}
		}

		if (input.contains(" ")) {
			System.out.println("You can only enter one word.");
			return false;
		}

		return true;
	}

	/**
	 * Checks whether or not the word already is in the database. Used when
	 * player is adding word to database.
	 * 
	 * @return Returns true if the word already is in the database.
	 */
	public boolean inDatabase(String[] words) {
		for (int i = 0; i < words.length; i++) {
			if(input.equals(words[i])) {
				return true; 
			}
		}
		
		return false; 
	}

	public boolean inWord() {
		return true; 
	}
	
	public boolean isWord() {
		if(input.equals(word)) {
			return true; 
		}
		
		return false; 
	}
}