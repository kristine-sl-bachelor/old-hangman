package hangman;

public class UsernamePassword {

	private String username; 
	private String password; 
	
	public UsernamePassword(String username, String password) {
		setUsername(username); 
		setPassword(password); 
	}
	
	public String getUsername() {
		return username; 
	}
	
	public String getPassword() {
		return password; 
	}
	
	public void setUsername(String username) {
		this.username = username; 
	}
	
	public void setPassword(String password) {
		this.password = password; 
	}
}
