package stuff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DBHandler2 {
	private ConnectToDB connect; 
	private Connection con; 
	
	public DBHandler2() {
		connect = new ConnectToDB(); 
		con = connect.getConnection(); 
	}
	
	// CRUD (Create, Read, Update, Delete) 
	
	// Create
	
	// Read 
	public String getElement(String key) throws SQLException {
		ResultSet rs = null ;
		try {
			Statement stmt = (Statement) con.createStatement();
			stmt.executeQuery("SELECT word FROM words");
			rs = stmt.getResultSet();
			while(rs.next()){
				rs.getString(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return rs.getString("word"); 
	}
	
	// Update 
	public boolean updateElement(String newValues, String oldValuesKey) {
		return true; 
	}
	
	// Delete 
	public boolean deleteElement(String key) {
		return true; 
	}

	private class ConnectToDB {
		private Connection con; 
		
		public ConnectToDB() {
			this("username", "password"); 
		}
		public ConnectToDB(String user, String pw) {
			String url = "jdbc:mysql://localhost/Hangman?" + user; 
			
			try {
				con = DriverManager.getConnection(url, user, pw); 
			} catch (SQLException e) {
				e.printStackTrace(); 
			}
		}
		
		public boolean close() {
			try {
				con.close(); 
				return true; 
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			
			return false; 
		}
		
		public Connection getConnection() {
			return con; 
		}
	}
}
