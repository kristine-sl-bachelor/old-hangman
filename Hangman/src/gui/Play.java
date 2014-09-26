package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Play extends JFrame {

	private EventHandler1 scanner = new EventHandler1();
	private Warning warning;

	public Play() {
		super("Play");

		add(new Top(), BorderLayout.CENTER);
		add(new Bottom(), BorderLayout.SOUTH);

		setSize(300, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private class Top extends JPanel {
		public Top() {
			JLabel lblFeedback = new JLabel("-feedback-");
			add(lblFeedback);
		}
	}

	private class Bottom extends JPanel {
		public Bottom() {
			setLayout(new GridLayout(2, 1));

			JTextField txtInput = new JTextField();
			txtInput.addActionListener(scanner);

			add(txtInput);
			add(new Buttons());
		}

		private class Buttons extends JPanel {
			public Buttons() {
				setLayout(new GridLayout(1, 2));

				JButton btnQuit = new JButton("Quit");
				JButton btnSubmit = new JButton("Submit");

				btnQuit.addActionListener(scanner);
				btnSubmit.addActionListener(scanner);

				add(btnQuit);
				add(btnSubmit);
			}
		}
	}

	private class Warning extends JFrame {
		public Warning() {
			super("Warning");

			add(new Top(), BorderLayout.CENTER);
			add(new Bottom(), BorderLayout.SOUTH);

			setSize(300, 100);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
		}

		private class Top extends JPanel {
			public Top() {
				JTextArea taWarning = new JTextArea();
				taWarning.setText("If you quit, this will be counted as a loss.\n"
								+ "Are you sure you want to quit?");
				taWarning.setBackground(getBackground()); 
				add(taWarning);
			}
		}

		private class Bottom extends JPanel {
			public Bottom() {
				setLayout(new GridLayout(1, 2));

				JButton btnYes = new JButton("Yes");
				btnYes.addActionListener(scanner);
				JButton btnNo = new JButton("No");
				btnNo.addActionListener(scanner);

				add(btnYes);
				add(btnNo);
			}
		}
	}

	private class EventHandler1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String data = e.getActionCommand();

			if (data.equalsIgnoreCase("submit")) {

			} else if (data.equalsIgnoreCase("yes")) {
				warning.setVisible(false);
				setVisible(false);
				new MainMenu();
			} else if (data.equalsIgnoreCase("no")) {
				warning.setVisible(false);
			} else {
				warning = new Warning();
			}
		}
	}

	private class EventHandler2 implements KeyListener {
		public void keyPressed(KeyEvent e) {

		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyTyped(KeyEvent e) {

		}
	}

	public static void main(String[] args) {
		new Play();
	}
}
