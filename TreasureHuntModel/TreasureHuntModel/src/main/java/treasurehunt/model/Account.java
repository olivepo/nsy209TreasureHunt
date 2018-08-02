package treasurehunt.model;

import java.util.Hashtable;

import com.fasterxml.jackson.annotation.*;


public class Account {
	
	private static Hashtable<String,Account> accounts = new Hashtable<String,Account>();
	@JsonProperty
	public String email;
	@JsonProperty
	public String login;
	@JsonProperty
	public String hashedPassword;
	
	// N�cessaire pour JACKSON ObjectMapper.ReadValue(), � ne pas utiliser !!!
	public Account() {
		
	}
	
	private Account(String id) {
		email = id;
	}
	
	public boolean checkPassword(String password) {
		
		boolean result = false;
		
		try {
			result = PasswordHashTool.check(password,hashedPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@JsonIgnore
	public void setPassword(String password) {
		
		try {
			this.hashedPassword = PasswordHashTool.getSaltedHash(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Account getInstance(String id) {
		if(!accounts.containsKey(id)) {
			accounts.put(id, new Account(id));
		}
		return accounts.get(id);
	}

}
