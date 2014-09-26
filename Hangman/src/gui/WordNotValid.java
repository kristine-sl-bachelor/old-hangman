package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WordNotValid extends JFrame{

	public WordNotValid() {
		super("Word not valid"); 
		EventHandler scanner = new EventHandler(); 
		
		JLabel lbl = new JLabel("The word you have entered is not valid."); 
		JButton btn = new JButton("OK"); 
		btn.addActionListener(scanner); 
		
		add(lbl, BorderLayout.CENTER); 
		add(btn, BorderLayout.SOUTH); 
		
		setSize(300, 200); 
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLocationRelativeTo(null); 
	}
	
	public class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false); 
		}
	}
}
