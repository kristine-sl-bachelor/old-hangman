package stuff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHandler {

	// Variables
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private String table;

	// Constructor
	public DBHandler(String table) {
		this.table = table;
	}

	// Methods
	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/Hangman?user=root");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from hangman." + table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertIntoDatabase(String columns, String values) {
		try {
			connect();

			preparedStatement = connect.prepareStatement("insert into " + table + " ("
					+ columns + ") values (?)");
			preparedStatement.setString(1, values);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void deleteFromDatabase(String column, String value) {
		try {
			connect();
			preparedStatement = connect.prepareStatement("Delete from " + table + " where "
					+ column + " = ?");
			preparedStatement.setString(1, value);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public String columnNames() {
		String columns = "";
		try {
			connect();
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				columns += resultSet.getMetaData().getColumnName(i) + ", ";
			}

			columns = columns.substring(0, columns.length() - 2);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return columns;
	}

	public void updateTable(String column, String value, String username) {
		int usr_id = findUsr_id(username);

		try {
			preparedStatement = connect.prepareStatement("update table " + table + " set "
					+ column + " = ? where usr_id = ?");
			preparedStatement.setString(1, value);
			preparedStatement.setInt(2, usr_id);
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	private int findUsr_id(String username) {
		try {
			preparedStatement = connect
					.prepareStatement("select * from username where username = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			int usr_id = resultSet.getInt("id");
			return usr_id;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return 0;
	}

	private void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		String output = ""; 
		
		try {
			connect();
			for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
				output += resultSet.getMetaData().getColumnName(i).toUpperCase() + "\n";
			}
	
			while (resultSet.next()) {
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					output += resultSet.getString(i) + "\n";
				}
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		} finally {
			close(); 
		}
		
		return output; 
	}
}
