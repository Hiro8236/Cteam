package bean;


public class Staff implements java.io.Serializable{


	private int staffID;
	private String staffname;
	private String password;
	private String staffrole;

	private boolean isAuthenticated;


	public int getStaffID(){
		return staffID;
	}
	public String getStaffName(){
		return staffname;
	}
	public String getPassword(){
		return password;
	}

	public String getStaffRole(){
		return staffrole;
	}

	public boolean isAuthenticated() {
		return isAuthenticated;
	}


	public void setStaffID(int staffID){
		this.staffID=staffID;
	}
	public void setStaffName(String staffname){
		this.staffname=staffname;
	}
	public void setPassword(String password){
		this.password=password;
	}

	public void setStaffRole(String staffrole){
		this.staffrole=staffrole;
	}

	public void setAuthenticated(boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}


	}