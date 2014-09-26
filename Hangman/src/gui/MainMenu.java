package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenu extends JFrame {

	private String[] btnLabels = {"Play", "Instructions", "Add word", "Profile",
			"Highscores", "Quit" };

	public MainMenu() {

		super("Main Menu");
		
		add(new Top(), BorderLayout.NORTH); 
		add(new Bottom(), BorderLayout.CENTER); 

		setSize(300, 100 + (btnLabels.length * 50));
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public class Top extends JPanel {
		public Top() {
			JLabel lblText = new JLabel("Welcome to Hangman 3.0!"); 
			add(lblText); 
		}
	}
	
	public class Bottom extends JPanel {
		public Bottom() {
			setLayout(new GridLayout(btnLabels.length, 1));
			EventHandler scanner = new EventHandler(); 
			
			JButton btn; 
			for (int i = 0; i < btnLabels.length; i++) {
				btn = new JButton(btnLabels[i]); 
				btn.addActionListener(scanner); 
				add(btn); 
			}
		}
	}
	
	public class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String data = e.getActionCommand(); 
			
			if (data.equals("Play")) {
				new Play(); 
				
			} else if(data.equals("Instructions")) {
				new Instructions(); 
				
			} else if(data.equals("Add word")) {
				new AddWord(); 
				
			} else if(data.equals("Profile")) {
				new Profile(); 
				
			} else if(data.equals("Highscores")) {
				new Highscore(); 
				
			} else if(data.equals("Quit")) {
				System.exit(0); 
			}
		
		setVisible(false); 
		
		}
	}
	
	public static void main(String[] args) {
		new MainMenu(); 
	}

}
