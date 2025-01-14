package bean;


public class User implements java.io.Serializable{


	private int userID;
	private String EmailAddress;
	private String password;

	private boolean isAuthenticated;


	public int getUserID(){
		return userID;
	}
	public String getEmailAddress(){
		return EmailAddress;
	}
	public String getPassword(){
		return password;
	}
	public boolean isAuthenticated() {
		return isAuthenticated;
	}


	public void setUserID(int userID){
		this.userID=userID;
	}
	public void setEmailAddress(String EmailAddress){
		this.EmailAddress=EmailAddress;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	
}


