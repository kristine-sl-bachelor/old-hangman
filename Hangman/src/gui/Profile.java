package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Profile extends JFrame {

	private String[] btnLabels = { "Personal Scores", "Change Password", "Back" };

	public Profile() {
		this("default username");
	}

	public Profile(String username) {
		super("Profile");

		add(new Top(username), BorderLayout.NORTH); 
		add(new Bottom(), BorderLayout.CENTER);

		setSize(300, 100 + (btnLabels.length * 50));
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private class Top extends JPanel {
		public Top(String username) {

			JLabel lblUsername = new JLabel(username);
			add(lblUsername); 
		}
	}

	private class Bottom extends JPanel {
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

	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String data = e.getActionCommand();
			
			if (data.equals("Personal Scores")) {
				System.out.println("personal scores");
			} else if(data.equals("Change Password")) {
				System.out.println("change password");
			} else if(data.equals("Back")) {
				new MainMenu(); 
			}
		}

	}

	public static void main(String[] args) {
		new Profile(); 
	}
}
