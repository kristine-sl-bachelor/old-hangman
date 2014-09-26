// HANGMAN 
// Made by Kristine Sundt Lorentzen

// To do:
// - Add event listener to JFrame so that one can use Enter 
// - Fix formatting
// - Print scores in JFrame with scroll
// - Use database 
// - Online? 
// - Parse to iPhone? 

package stuff;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Hangman {

	static final int PLAY_GAME = 0;
	static final int ADD_WORD = 1;
	static final int PERSONAL_SCORES = 0;
	static final int HIGHSCORES = 1;
	static final int QUIT = 2;

	private static JFrame frame;
	private static JPanel panel;
	private static JTextArea taScores;
	private static JTextField tfUsername;
	private static JPasswordField tfPassword;
	private static JTextArea taOutput;
	private static JTextField tfInput;

	public static void main(String[] args) {
		logIn();
	}

	/**
	 * The game itself, utilizing the methods wordsFromFile, output, draw,
	 * writeToFile and startMenu.
	 */
	public static void hangman(int countWin, int countLose, String username) {
		// the correct word
		String correct = wordsFromFile();

		// how to play
		JOptionPane
				.showMessageDialog(
						null,
						"Guess the letters you think are included in the word.\n"
								+ "When you think you know the answer, you can also write the full word.\n"
								+ "If you guess the wrong letters or words 7 times, you have lost.\n\n"
								+ "The word consist of " + correct.length() + " letters.",
						"How to play", JOptionPane.INFORMATION_MESSAGE);

		// variables for feedback or keeping track of progress
		String alreadyGuessedCorrect = "";
		String alreadyGuessedWrong = "";
		String wrongWords = "";
		String messageToUser = null;
		int wrong = 0;
		boolean winner = false;
		boolean correctWord = false;
		boolean correctLetter = false;
		boolean wrongWord = false;
		boolean wrongLetter = false;

		// makes placement a string of stars with the same length as correct
		String placement = "";
		for (int i = 0; i < correct.length(); i++) {
			placement += "*";
		}

		// gets input from user
		String answer = JOptionPane.showInputDialog("Guess a character or word:\n");

		// game
		do {
			if (answer == null) {
				startMenu(countWin, countLose, username);
			}
			answer = answer.toLowerCase();

			// if input contains invalid characters
			if (answer.contains(" ")) {
				messageToUser = "You can only guess one word at a time.\nPlease try again.";
			} else if (!checkInput(answer)) {
				messageToUser = "You have entered an invalid character.\nPlease try again.";
				// if input is longer that one character
			} else if (answer.length() > 1) {
				if (answer.equals(correct)) {
					winner = true;
					correctWord = true;
				} else {
					wrongWord = true;
					wrong++;
					wrongWords += answer + " ";

					if (wrong < 7) {
						messageToUser = "This is the wrong word, please try again.";
					}
				}
				// if no input
			} else if (answer.length() == 0) {
				messageToUser = "Please guess a character or word";
				// if input is one character
			} else {
				// if the character is correct
				if (alreadyGuessedCorrect.contains(answer)) {
					messageToUser = "You have already guessed that character,\nand it was correct.\nPlease guess again.";
				} else if (alreadyGuessedWrong.contains(answer)) {
					messageToUser = "You have already guessed that character,\nand it was wrong.\nPlease guess again.";
				} else if (correct.contains(answer)) {
					correctLetter = true;

					// adds character to alreadyGuessedCorrect
					alreadyGuessedCorrect += answer;
					placement = "";

					// adds character to the correct spot in placement
					for (int i = 0; i < correct.length(); i++) {
						if (alreadyGuessedCorrect.contains(Character.toString(correct
								.charAt(i)))) {
							placement += correct.charAt(i);
						} else {
							placement += '*';
						}
					}
					winner = placement.equals(correct);

					if (!winner && wrong < 7) {
						messageToUser = "Correct!";
					}
					// if the character is wrong
				} else {
					wrongLetter = true;
					wrong++;
					alreadyGuessedWrong += answer + " ";

					if (wrong < 7) {
						messageToUser = "Wrong.";
					}
				}
			}
			
			answer = output(placement, wrong, alreadyGuessedWrong, wrongWords,
					messageToUser);
//			updateXp(correctWord, correctLetter, wrongWord, wrongLetter, username,
//					alreadyGuessedCorrect);

			correctWord = false;
			correctLetter = false;
			wrongWord = false;
			wrongLetter = false;
		} while (wrong < 7 && !winner);

		// results
		if (wrong == 7) {
			JOptionPane.showMessageDialog(null, "You lost.\n" + "The correct word was "
					+ correct + "\n\n" + draw(wrong), "Game over", JOptionPane.ERROR_MESSAGE);
			countLose++;
		}
		
		if (winner) {
			JOptionPane.showMessageDialog(null, "Congratulations, you won! The word was "
					+ correct, "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
			countWin++;
		}

		startMenu(countWin, countLose, username);
	}

	/**
	 * Selects a word from the file "words.txt" randomly.
	 * 
	 * @return returns the word for use in the game.
	 */
	public static String wordsFromFile() {
		Random rand = new Random();
		int count = 0;
		String correct = null;
		File datafile = new File("words.txt");
		try {
			Scanner input = new Scanner(datafile);

			while (input.hasNextLine()) {
				count++;
				input.nextLine();
			}
			String[] array = new String[count];
			input = new Scanner(datafile);

			for (int i = 0; i < count; i++) {
				array[i] = input.nextLine();
			}

			correct = array[rand.nextInt(count - 1)];
			input.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return correct;
	}

	/**
	 * Creates an output which is used to show progress to the user.
	 * 
	 * @param placement
	 *            if a correct character is guessed, this shows where in the
	 *            word it is located.
	 * @param wrong
	 *            number of wrong guesses
	 * @param alreadyGuessedWrong
	 *            a list of characters which have been guessed that are wrong.
	 * @param wrongWords
	 *            a list of words which have been guessed that are wrong.
	 * @return returns the output in form of a string.
	 */
	public static String output(String placement, int wrong, String alreadyGuessedWrong,
			String wrongWords, String messageToUser) {

		String hangmanDraw = draw(wrong);
		String out = messageToUser + "\n\n" + placement + "\n\nWrong: " + wrong
				+ "\nWrong guesses: " + alreadyGuessedWrong + "\nWrong words: " + wrongWords
				+ "\n\n" + hangmanDraw + "\n";

		frame = new JFrame("Feedback");
		frame.setLayout(new BorderLayout());

		taOutput = new JTextArea();
		taOutput.setText(out);
		taOutput.setEditable(false);
		taOutput.setMargin(new Insets(10, 10, 10, 10));

		tfInput = new JTextField();

		frame.add(taOutput, BorderLayout.NORTH);
		frame.add(tfInput, BorderLayout.CENTER);

		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));

		JButton buyLetter = new JButton("Buy a letter");
		buyLetter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		panel.add(buyLetter);
		panel.add(submit);

		frame.add(panel, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(350, 250);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		return tfInput.getText();
	}

	/**
	 * Draws the hangman figure by adding one line for each time the user
	 * guesses something incorrect.
	 * 
	 * @param wrong
	 *            number of wrong guesses, indicating how many lines the program
	 *            should print
	 * @return returns the drawing
	 */
	public static String draw(int wrong) {
		String draw = "";

		String[] drawLines = {
				"  ------------\n",
				"  |                      |\n",
				"  |                      |\n",
				"  |                     O\n",
				"  |                    /|\\\n",
				"  |                      |\n",
				"  |                    / \\\n " + " /_\\___________\n"
						+ "(                           )" };

		if (wrong > 0) {
			draw = drawLines[0];
			for (int i = 1; i < wrong; i++) {
				draw += drawLines[i];
			}
		}

		return draw;
	}

	/**
	 * Gives the user the opportunity to add a word to the game by adding it to
	 * the text file used in wordsFromFile.
	 */
	public static void writeToFile(int countWin, int countLose, String username) {

		File datafile = new File("words.txt");

		String answer = JOptionPane.showInputDialog("What word do you want to add?");

		// checks if the word already exists in the file
		try {
			Scanner fileScanner = new Scanner(datafile);

			while (fileScanner.hasNextLine()) {
				if (answer == null) {
					startMenu(countWin, countLose, username);
				}
				String word = fileScanner.nextLine();
				if (word.equalsIgnoreCase(answer.trim())) {
					answer = JOptionPane
							.showInputDialog("This word is already in the game.\nPlease enter another");
				}
			}
			fileScanner.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		boolean validWord = false;

		// checks if the entered word is valid
		do {

			if (answer == null) {
				startMenu(countWin, countLose, username);
			}

			answer = answer.toLowerCase().trim();
			if (answer.equals("")) {
				answer = JOptionPane
						.showInputDialog("You did not enter a word. Please try again.");
			} else if (answer.equals(" ")) {
				answer = JOptionPane
						.showInputDialog("You did not enter a word. Please try again.");
			} else if (answer.contains(" ")) {
				answer = JOptionPane
						.showInputDialog("You can only enter one word. Please try again.");
			} else if (checkInput(answer) == false) {
				answer = JOptionPane
						.showInputDialog("You have entered a character that is not valid.\nPlease try again.");
			} else {
				answer = "\n" + answer;
				validWord = true;
			}
		} while (validWord == false);

		// adds the word
		try {
			FileWriter fw = new FileWriter(datafile, true); // in
			BufferedWriter bw = new BufferedWriter(fw); // out
			bw.write(answer);
			bw.close();

			JOptionPane.showMessageDialog(null, "Your word:\n" + answer
					+ " \n\nhas been added to the game.", "Word has been added",
					JOptionPane.INFORMATION_MESSAGE);

		} catch (IOException e) {
			e.printStackTrace();
		}

		startMenu(countWin, countLose, username);
	}

	/**
	 * Gives the user the choice between playing the game, adding a word or
	 * quitting.
	 */
	public static void startMenu(int countWin, int countLose, String username) {
		String[] options = { "Play Hangman", "Add a word", "Quit" };

		int optionsAns = JOptionPane.showOptionDialog(null,
				"Do you want to play hangman, or add a word?", "What do you want?",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]);

		if (optionsAns == PLAY_GAME) {
			hangman(countWin, countLose, username);
		} else if (optionsAns == ADD_WORD) {
			writeToFile(countWin, countLose, username);
		} else {
			int ans = JOptionPane.showConfirmDialog(null, "You won " + countWin
					+ " times and lost " + countLose
					+ " times.\nDo you want to add your score to the highscore?", "Score",
					JOptionPane.YES_NO_OPTION);

			if (ans == JOptionPane.YES_OPTION) {
				addScores(countWin, countLose, username);
				scoreView(username);
			} else if (ans == JOptionPane.NO_OPTION) {
				scoreView(username);
			} else {
				System.exit(0);
			}
		}
	}

	/**
	 * Check if input is valid
	 * 
	 * @param answer
	 *            input from user.
	 * @return true or false where true = input valid.
	 */
	public static boolean checkInput(String answer) {
		answer = answer.toLowerCase();
		String letters = "abcdefghijklmnopqrstuvwxyz";
		boolean answerOk = true;

		for (int i = 0; i < answer.length(); i++) {
			if (!answerOk) {
				return answerOk;
			}
			if (!letters.contains(Character.toString(answer.charAt(i)))) {
				answerOk = false;
			} else {
				answerOk = true;
			}
		}

		return answerOk;
	}

	//Score
	/**
	 * Adds the user's score to a textfile with scores.
	 * 
	 * @param countWin
	 *            number of times the user has won
	 * @param countLose
	 *            number of times the user has lost
	 */
	public static void addScores(int countWin, int countLose, String username) {
		File datafile = new File("highscore.txt");

		String countWinAsString = Integer.toString(countWin);
		String countLoseAsString = Integer.toString(countLose);

		// adds the name and score
		try {
			FileWriter fw = new FileWriter(datafile, true); // in
			BufferedWriter bw = new BufferedWriter(fw); // out
			bw.write(username + "\n");
			bw.write(countWinAsString + "\n");
			bw.write(countLoseAsString + "\n");
			bw.close();

			JOptionPane.showMessageDialog(null, "Your score has been added.", "scoreView",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Score
	/**
	 * Prints a list of all the scores, sorted.
	 */
	public static void scoreView(String username) {

		String[] options = { "Personal scores", "Highscores", "Quit" };

		int choice = JOptionPane.showOptionDialog(null,
				"Do you want to see your personal scores\nor the highscores?", "Scores",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[1]);

		if (choice == PERSONAL_SCORES) {
			personalScores(username);
		} else if (choice == HIGHSCORES) {
			highscores(username);
		} else if (choice == QUIT) {
			System.exit(0);
		} else {
			System.exit(0);
		}
	}

	//UsernamePassword
	/**
	 * Allows the user to log in with an existing username and password, or
	 * click "Sign up" to register.
	 */
	public static void logIn() {
		frame = new JFrame("Log in");

		frame.setLayout(new GridLayout(4, 0));

		tfUsername = new JTextField();
		tfPassword = new JPasswordField();

		tfPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File datafile = new File("loginInfo.txt");
				try {
					int count = 0;
					Scanner input = new Scanner(datafile);
					while (input.hasNextLine()) {
						count++;
						input.nextLine();
					}
					input = new Scanner(datafile);
					String[][] usrCheck = new String[count / 2][2];
					for (int i = 0; i < usrCheck.length; i++) {
						usrCheck[i][0] = input.nextLine();
						usrCheck[i][1] = input.nextLine();
					}

					boolean usernameExists = false;

					String username = tfUsername.getText();
					@SuppressWarnings("deprecation")
					String password = tfPassword.getText();
					int position = 0;

					for (int i = 0; i < usrCheck.length; i++) {
						if (usrCheck[i][0].equals(username)) {
							usernameExists = true;
							position = i;
						}
					}
					if (!usernameExists) {
						// username does not exist, do you want to sign up?
						int option = JOptionPane.showConfirmDialog(null,
								"The username does not exist.\nDo you want to register?",
								"Username does not exist", JOptionPane.ERROR_MESSAGE,
								JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.YES_OPTION) {
							signUp();
						} else {
							System.exit(0);
						}
						// if password is correct
					} else if (usrCheck[position][1].equals(password)) {
						frame.setVisible(false);
						int countWin = 0;
						int countLose = 0;
						startMenu(countWin, countLose, username);
					} else {
						// wrong password, try again.
						JOptionPane.showMessageDialog(null,
								"Wrong password, please try again!");
						tfPassword.setText("");
					}

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

			}
		});

		frame.add(tfUsername);
		frame.add(tfPassword);

		JButton signUp = new JButton("Sign up");
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Register username and password
				frame.setVisible(false);
				signUp();
			}
		});

		JButton logIn = new JButton("Log in");
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File datafile = new File("loginInfo.txt");
				try {
					int count = 0;
					Scanner input = new Scanner(datafile);
					while (input.hasNextLine()) {
						count++;
						input.nextLine();
					}
					input = new Scanner(datafile);
					String[][] usrCheck = new String[count / 2][2];
					for (int i = 0; i < usrCheck.length; i++) {
						usrCheck[i][0] = input.nextLine();
						usrCheck[i][1] = input.nextLine();
					}

					boolean usernameExists = false;

					String username = tfUsername.getText();
					@SuppressWarnings("deprecation")
					String password = tfPassword.getText();
					int position = 0;

					for (int i = 0; i < usrCheck.length; i++) {
						if (usrCheck[i][0].equals(username)) {
							usernameExists = true;
							position = i;
						}
					}
					if (!usernameExists) {
						// username does not exist, do you want to sign up?
						int option = JOptionPane.showConfirmDialog(null,
								"The username does not exist.\nDo you want to register?",
								"Username does not exist", JOptionPane.ERROR_MESSAGE,
								JOptionPane.YES_NO_OPTION);
						if (option == JOptionPane.YES_OPTION) {
							signUp();
						} else {
							System.exit(0);
						}
						// if password is correct
					} else if (usrCheck[position][1].equals(password)) {
						frame.setVisible(false);
						int countWin = 0;
						int countLose = 0;
						startMenu(countWin, countLose, username);
					} else {
						// wrong password, try again.
						JOptionPane.showMessageDialog(null,
								"Wrong password, please try again!");
						tfPassword.setText("");
					}

					input.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

			}
		});

		frame.add(logIn);
		frame.add(signUp);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 140);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	//UsernamePassword
	/**
	 * Allows the user to register a username and password for logging in
	 */
	public static void signUp() {
		frame = new JFrame("Sign up");

		frame.setLayout(new GridLayout(4, 1));

		tfUsername = new JTextField();
		tfPassword = new JPasswordField();

		frame.add(tfUsername);
		frame.add(tfPassword);

		JButton signUp = new JButton("Sign up");
		signUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Register username and password
				File datafile = new File("loginInfo.txt");
				try {
					String username = tfUsername.getText();
					@SuppressWarnings("deprecation")
					String password = tfPassword.getText();

					FileWriter fw = new FileWriter(datafile, true); // in
					BufferedWriter bw = new BufferedWriter(fw); // out
					bw.write(username);
					bw.write("\n" + password + "\n");
					bw.close();
					frame.setVisible(false);
					logIn();

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton logIn = new JButton("Back to log in");
		logIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				logIn();
			}
		});
		frame.add(signUp);
		frame.add(logIn);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 140);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	//Score
	/**
	 * Shows individual results based on username
	 * 
	 * @param username
	 */
	public static void personalScores(final String username) {

		File datafile = new File("highscore.txt");
		int count = 0;
		try {
			Scanner in = new Scanner(datafile);
			int countForArray = 0;

			while (in.hasNextLine()) {
				count++;
				in.nextLine();
			}
			String[] array = new String[count];
			in = new Scanner(datafile);

			for (int i = 0; i < array.length; i++) {
				array[i] = in.nextLine();
				if (array[i].equals(username)) {
					countForArray++;
				}
			}

			int place = 0;

			String[] personalArray = new String[countForArray * 3];
			for (int i = 0; i < array.length; i++) {
				if (array[i].equals(username)) {
					personalArray[place] = array[i];
					personalArray[place + 1] = array[i + 1];
					personalArray[place + 2] = array[i + 2];
					place += 3;
				}
			}

			ArrayList<Score> scores = new ArrayList<Score>();
			for (int i = 0; i < personalArray.length; i += 3) {
				scores.add(new Score(personalArray[i], Integer.parseInt(personalArray[i + 1]),
						Integer.parseInt(personalArray[i + 2])));
			}

			Collections.sort(scores);
			Collections.reverse(scores);

			String out = "";
			for (Score s : scores) {
				out += s + "\n";
			}
			String head = String.format("%-30s %10s %10s %10s \n\n", "Name", "Wins", "Losses",
					"Score");

			frame = new JFrame("Personal scores");
			frame.setLayout(new BorderLayout());

			taScores = new JTextArea();
			taScores.setText(head + out);
			taScores.setEditable(false);
			taScores.setMargin(new Insets(10, 10, 10, 10));

			frame.add(taScores, BorderLayout.CENTER);

			panel = new JPanel();
			panel.setLayout(new GridLayout(1, 2));

			JButton highscores = new JButton("Highscores");
			highscores.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					highscores(username);
				}
			});

			JButton quit = new JButton("Quit");
			quit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			panel.add(highscores);
			panel.add(quit);

			frame.add(panel, BorderLayout.SOUTH);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(350, 250);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//Score
	/**
	 * Shows a sorted list of all the scores from all the users.
	 * 
	 * @param username
	 */
	public static void highscores(final String username) {

		File datafile = new File("highscore.txt");
		int count = 0;

		try {
			Scanner in = new Scanner(datafile);
			while (in.hasNextLine()) {
				count++;
				in.nextLine();
			}
			String[] array = new String[count];
			in = new Scanner(datafile);
			for (int i = 0; i < count; i++) {
				array[i] = in.nextLine();
			}

			ArrayList<Score> scores = new ArrayList<Score>();
			for (int i = 0; i < array.length; i += 3) {
				scores.add(new Score(array[i], Integer.parseInt(array[i + 1]), Integer
						.parseInt(array[i + 2])));
			}

			Collections.sort(scores);
			Collections.reverse(scores);

			String out = "";
			for (Score s : scores) {
				out += s + "\n";
			}
			String head = String.format("%-30s %10s %10s %10s \n\n", "Name", "Wins", "Losses",
					"Score");

			frame = new JFrame("Highscores");

			frame.setLayout(new BorderLayout());

			taScores = new JTextArea();
			taScores.setText(head + out);
			taScores.setEditable(false);
			taScores.setMargin(new Insets(10, 10, 10, 10));

			frame.add(taScores, BorderLayout.CENTER);

			panel = new JPanel();
			panel.setLayout(new GridLayout(1, 2));

			JButton personalScores = new JButton("Personal Scores");
			personalScores.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					personalScores(username);
				}
			});

			JButton quit = new JButton("Quit");
			quit.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});

			panel.add(personalScores);
			panel.add(quit);

			frame.add(panel, BorderLayout.SOUTH);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(350, 250);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	//XP 
//	/**
//	 * Gives the user xp according to what the user answers
//	 * 
//	 * @param correctWord
//	 *            checks if the user has guessed a word that is correct
//	 * @param correctLetter
//	 *            checks if the user has guesses a letter that is correct
//	 * @param wrongWord
//	 *            checks if the user has guessed a word that is wrong
//	 * @param wrongLetter
//	 *            checks of the user has guessed a letter that is wrong
//	 * @param username
//	 *            the username of the current user
//	 * @param alreadyGuessedCorrect
//	 *            the words that the user has already guessed that were correct.
//	 * @return an xp value which can be used further on in the game
//	 */
//	public static int xp(boolean correctWord, boolean correctLetter, boolean wrongWord,
//			boolean wrongLetter, String username, String alreadyGuessedCorrect) {
//		// returns a xp value based on the guesses of the user
//		// need to overwrite the file to update xp!
//
//		int xp = 0;
//
//		File datafile = new File("xp.txt");
//		try {
//			int count = 0;
//			Scanner in = new Scanner(datafile);
//			while (in.hasNextLine()) {
//				count++;
//				in.nextLine();
//			}
//
//			String[] array = new String[count];
//			in = new Scanner(datafile);
//
//			for (int i = 0; i < count; i++) {
//				array[i] = in.nextLine();
//			}
//
//			for (int i = 0; i < array.length; i++) {
//				if (array[i].equals(username)) {
//					xp = Integer.parseInt(array[i + 1]);
//				}
//			}
//			in.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		// if you guess a letter that is correct: 10xp
//		if (correctLetter) {
//			xp += 10;
//		}
//		// // if you guess a word that is correct: 200xp - 10*number of letters
//		// // already guessed correct
//		if (correctWord) {
//			xp += (200 - (alreadyGuessedCorrect.length() + 1));
//		}
//		// // if you guess a letter that is wrong: - 10xp
//		if (wrongLetter) {
//			xp -= 10;
//		}
//		// // if you guess a word that is wrong: -50xp
//		if (wrongWord) {
//			xp -= 50;
//		}
//
//		return xp;
//	}
//
//	/**
//	 * Updates the file containing the amount of xp that the user has.
//	 * 
//	 * @param correctWord
//	 *            checks if the user has guessed a word that is correct
//	 * @param correctLetter
//	 *            checks if the user has guesses a letter that is correct
//	 * @param wrongWord
//	 *            checks if the user has guessed a word that is wrong
//	 * @param wrongLetter
//	 *            checks of the user has guessed a letter that is wrong
//	 * @param username
//	 *            the username of the current user
//	 * @param alreadyGuessedCorrect
//	 *            the words that the user has already guessed that were correct.
//	 */
////	public static void updateXp(boolean correctWord, boolean correctLetter, boolean wrongWord,
//			boolean wrongLetter, String username, String alreadyGuessedCorrect) {
//		int xp = xp(correctWord, correctLetter, wrongWord, wrongLetter, username,
//				alreadyGuessedCorrect);
//		int count = 0;
//
//		File datafile = new File("xp.txt");
//		try {
//			Scanner in = new Scanner(datafile);
//
//			while (in.hasNextLine()) {
//				count++;
//				in.nextLine();
//			}
//
//			String[] array = new String[count];
//			in = new Scanner(datafile);
//
//			for (int i = 0; i < array.length - 1; i++) {
//				array[i] = in.nextLine();
//			}
//
//			FileWriter fw = new FileWriter(datafile);
//			BufferedWriter bw = new BufferedWriter(fw);
//
//			String toFile = "";
//			for (int i = 0; i < array.length; i++) {
//				if (array[i].equals(username)) {
//					array[i + 1] = Integer.toString(xp);
//				}
//				toFile += array[i] + "\n";
//			}
//
//			bw.write(toFile);
//			bw.close();
//			in.close();
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}