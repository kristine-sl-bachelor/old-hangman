package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class SignUp extends JFrame{

	public SignUp() {
		
		super("Sign up"); 
		
		add(new Top(), BorderLayout.NORTH); 
		add(new Bottom(), BorderLayout.CENTER);
		
		setSize(300, 200); 
		setVisible(true); 
		setDefaultCloseOperation(EXIT_ON_CLOSE); 
		setLocationRelativeTo(null); 
	}
	
	private class Top extends JPanel {
		public Top() {
			
			setLayout(new GridLayout(1,2));
			
			add(new TopLeft()); 
			add(new TopRight()); 
		}
		
		
		private class TopLeft extends JPanel {
			
			public TopLeft() {
				setLayout(new GridLayout(3,1)); 
				
				JLabel lblUsername = new JLabel("Username "); 
				add(lblUsername); 
				
				JLabel lblPassword1 = new JLabel("Password ");
				add(lblPassword1); 
				
				JLabel lblPassword2 = new JLabel("Repeat password"); 
				add(lblPassword2); 
			}
		}
		
		private class TopRight extends JPanel {
			public TopRight() {
				setLayout(new GridLayout(3,1)); 
				
				JTextField tfUsername = new JTextField();
				add(tfUsername); 
				
				JPasswordField pfPassword1 = new JPasswordField(); 
				add(pfPassword1); 
				
				JPasswordField pfPassword2 = new JPasswordField(); 
				add(pfPassword2); 
			}
		}
	}

	private class Bottom extends JPanel {
		public Bottom() {
			setLayout(new GridLayout(3,1)); 
			EventHandler scanner = new EventHandler(); 
			
			JButton btnSignUp = new JButton("Sign up"); 
			btnSignUp.addActionListener(scanner); 
			add(btnSignUp); 
			
			JLabel lblBack = new JLabel("Back to log in?");
			lblBack.setHorizontalAlignment(SwingConstants.CENTER); 
			add(lblBack); 
			
			JButton btnBack = new JButton("Back");
			btnBack.addActionListener(scanner); 
			add(btnBack); 
		}
	}

	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String data = e.getActionCommand(); 
			
			if (data.equals("Sign up")) {
				new MainMenu(); 
			} else if (data.equals("Back")) {
				new LogIn(); 
			}
		}
		
	}

	public static void main(String[] args) {
		new SignUp(); 
	}
}
