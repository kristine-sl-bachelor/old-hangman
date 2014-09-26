package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddWord extends JFrame {

	public AddWord() {
		super("Add a word");

		add(new Top(), BorderLayout.NORTH);
		add(new Bottom(), BorderLayout.CENTER);

		setSize(300, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private class Top extends JPanel {

		public Top() {

			setLayout(new GridLayout(1, 2));
			JLabel lblWord = new JLabel("Word");
			add(lblWord);
			JTextField tfInput = new JTextField();
			add(tfInput);
		}
	}

	private class Bottom extends JPanel {
		public Bottom() {
			setLayout(new GridLayout(2, 1));
			EventHandler scanner = new EventHandler();

			JButton btnAdd = new JButton("Add");
			btnAdd.addActionListener(scanner);
			add(btnAdd);

			JButton btnBack = new JButton("Back");
			btnBack.addActionListener(scanner);
			add(btnBack);
		}
	}

	private class EventHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String data = e.getActionCommand();

			if (data.equals("Add")) {
				System.out.println("add");
			} else if (data.equals("Back")) {
				new MainMenu();
			}
		}
	}

	public static void main(String[] args) {
		new AddWord();
	}
}
