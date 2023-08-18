package pojo;

public class BookingAPI_Creds_POJO {
	
	private String username;
	private String password;
	
	public BookingAPI_Creds_POJO(String userName, String password) {
		super();
		this.username = userName;
		this.password = password;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
