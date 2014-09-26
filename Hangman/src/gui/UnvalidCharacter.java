package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class UnvalidCharacter extends JFrame{
	
	public UnvalidCharacter() {
		super("Unvalid word"); 
		
		EventHandler scanner = new EventHandler(); 
		
		JTextArea text = new JTextArea(); 
		text.setText("You have guessed an unvalid character.\nPlease guess again."); 
		
		JButton btnOk = new JButton("OK"); 
		btnOk.addActionListener(scanner); 
		
		add(text, BorderLayout.CENTER); 
		add(btnOk, BorderLayout.SOUTH); 
		
		setSize(300, 200); 
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLocationRelativeTo(null); 
	}
	
	private class EventHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setVisible(false); 
		}
	}
}
