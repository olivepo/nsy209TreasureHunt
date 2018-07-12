package treasurehunt.model;

import java.util.Hashtable;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="account")
public class Account {
	
	private static Hashtable<String,Account> accounts = new Hashtable<String,Account>();
	
	@XmlElement
	public String email;
	@XmlElement
	public String login;
	@XmlElement
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	@XmlTransient
	public void setPassword(String password) {
		
		try {
			this.hashedPassword = PasswordHashTool.getSaltedHash(password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
