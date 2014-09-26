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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LogIn extends JFrame {
	
	private String username; 
	private String password; 
	private JTextField tfUsername; 
	private JPasswordField pfPassword; 

	public LogIn() {

		super("Log in");
		
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
				
				setLayout(new GridLayout(2,1));
				
				JTextArea taUsername = new JTextArea("Username"); 
				taUsername.setEditable(false); 
				add(taUsername); 
				
				JTextArea taPassword = new JTextArea("Password"); 
				taPassword.setEditable(false); 
				add(taPassword); 
			}
		}
		
		private class TopRight extends JPanel {
			public TopRight() {
				
				setLayout(new GridLayout(2,1)); 
				
				tfUsername = new JTextField(); 
				add(tfUsername); 
				pfPassword = new JPasswordField(); 
				add(pfPassword); 
			}
		}
	}

	private class Bottom extends JPanel {
		public Bottom() {
			
			setLayout(new GridLayout(3,1)); 
			EventHandler scanner = new EventHandler(); 
			
			JButton btnLogIn = new JButton("Log in"); 
			btnLogIn.addActionListener(scanner); 
			add(btnLogIn); 
			
			JLabel lblSignUp = new JLabel("\nNot registered?");
			lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
			add(lblSignUp); 
			
			JButton btnSignUp = new JButton("Sign up"); 
			btnSignUp.addActionListener(scanner); 
			add(btnSignUp); 
		}
	}

	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String data = e.getActionCommand(); 
			
			if (data.equals("Log in")) {
				setUsername(tfUsername.getText());
				
				
				
				new MainMenu(); 
			} else if (data.equals("Sign up")) {
				new SignUp(); 
			}
		}
		
	}
	
	public String getUsername() {
		return username; 
	}
	
	public void setUsername(String username) {
		this.username = username; 
	}
	
	public String getPassword() {
		return password; 
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
//	public static void main(String[] args) {
//		new LogIn(); 
//	}
}