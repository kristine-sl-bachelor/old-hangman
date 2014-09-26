package stuff;
/*
HAR KUN EKSEMPEL METODER. OM DU VIL PRÿVE DETTE, S≈ KAN DU SI IFRA HVIS DU TRENGER HJELP.
public class DBHandler {
	
	private ConnectToDB connect;
	private Connection con;
	
	/*
	 * creates methods for extraction of data from a database
	 *
	public DBHandler() {
		connect = new ConnectToDB();
		con = connect.getConnection();
	}
	
	/*
	 * CRUD for elements in the Database
	 * (CREATE, READ, UPDATE, DELETE
	 *
	
	//CREATE (POJO = Plain old Java Object)
	public boolean addElement(String pojo) {
		//Add element to database
		return true;
	}
	
	//READ
	public String getElement(String key) {
		/*
		 * Standard to create a POJO to hold data for a row
		 * in the database, and then return it based on a unique key, if exists.
		 * 
		 * if it does not, return null.  
		 *
		return "";
	}
	
	//UPDATE
	public boolean udateElement(String newValuesPOJO, String oldValuesKey) {
		//inserts the new POJO where the key matches.
		
		return true;
	}
	
	//DELETE
	public boolean deleteElement(String key) {
		//Delete element from DB if found based on key
		return true;
	}
	
	
	/*
	 * Sets up the connection to the database, this contains
	 * Connection, url, username and password for the connection
	 *
	private class ConnectToDB {
		
		private Connection con; 
		
		public ConnectToDB() {
			this("gulrob10", "gulrob10");
		}
		
		public ConnectToDB(String user, String pw) {
			String url = "jdbc:mysql://mysql.nith.no/" + user;
			
			try{ 
				con = DriverManager.getConnection(url, user, pw);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		public boolean close() {
			try{
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
*/