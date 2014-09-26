package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Instructions extends JFrame {

	private String[] instructions = {
			"In the game you can either guess a character\n"
					+ "or a word, by writing it into the input-field.\n"
					+ "Press 'enter' or the Submit-button to submit.\n",
			"If you guess a character, this will be checked\n"
					+ "to see if it is a part of the word. If you guess\n"
					+ "a more than one character, this will only be \n"
					+ "checked against the entire word.",
			"Until you either guess the correct\n"
					+ "word or lose, you will get feedback explaining\n"
					+ "what characters or words you have gotten\n"
					+ "correct or wrong. You will also see how close\n"
					+ "your man is to getting hanged." };

	private JTextArea taText;
	private int instructionNumber = 0;
	private JButton btnPrevious = new JButton("Previous");
	private JButton btnNext = new JButton("Next");

	public Instructions() {
		super("How to play: ");

		add(new Top(), BorderLayout.CENTER);
		add(new Bottom(), BorderLayout.SOUTH);

		setSize(300, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private class Top extends JPanel {
		public Top() {
			taText = new JTextArea(instructions[instructionNumber]);
			taText.setEditable(false);
			taText.setBackground(getBackground());
			add(taText);
		}
	}

	private class Bottom extends JPanel {
		public Bottom() {
			setLayout(new GridLayout(2, 1));

			add(new TopBottom());
			add(new BottomBottom());
		}

		private class TopBottom extends JPanel {
			public TopBottom() {
				setLayout(new GridLayout(1, 2));
				EventHandler scanner = new EventHandler();

				btnPrevious.addActionListener(scanner);
				btnPrevious.setVisible(false);
				btnNext.addActionListener(scanner);

				add(btnPrevious);
				add(btnNext);
			}
		}

		private class BottomBottom extends JPanel {
			public BottomBottom() {
				EventHandler scanner = new EventHandler();

				JButton backToMenu = new JButton("Back");
				backToMenu.addActionListener(scanner);

				add(backToMenu);
			}
		}
	}

	private class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String data = e.getActionCommand();

			if (data.equals("Next")) {
				instructionNumber++;
			} else if (data.equals("Previous")) {
				instructionNumber--;
			} else {
				new MainMenu();
				setVisible(false);
			}

			taText.setText(instructions[instructionNumber]);

			if (instructionNumber == 0) {
				btnPrevious.setVisible(false);
			} else if (instructionNumber == 2) {
				btnNext.setVisible(false);
			} else {
				btnPrevious.setVisible(true);
				btnNext.setVisible(true);
			}
		}
	}

	public static void main(String[] args) {
		new Instructions();
	}
}
