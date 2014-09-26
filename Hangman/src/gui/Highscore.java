package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Highscore extends JFrame{

	public Highscore() {
		super("Highscores"); 
		
		add(new Top(), BorderLayout.NORTH); 
		add(new Middle(), BorderLayout.CENTER); 
		add(new Bottom(), BorderLayout.SOUTH); 
		
		setSize(300, 400); 
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLocationRelativeTo(null); 
	}
	
	private class Top extends JPanel {
		public Top() {
			JLabel lblHighscores = new JLabel("Highscores"); 
			add(lblHighscores); 
		}
	}
	
	private class Middle extends JPanel {
		public Middle() {
			
			JTextArea taHighscores = new JTextArea(); 
			add(taHighscores); 
		}
	}
	
	private class Bottom extends JPanel {
		public Bottom() {
			EventHandler scanner = new EventHandler(); 
			
			JButton btnBack = new JButton("Back"); 
			btnBack.addActionListener(scanner); 
			add(btnBack); 
		}
	}
	
	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String data = e.getActionCommand(); 
			
			if (data.equals("Back")) {
				new MainMenu(); 
			}
		}
	}
	
	public static void main(String[] args) {
		new Highscore(); 
	}
}
