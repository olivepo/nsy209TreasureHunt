package treasurehunt.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Hashtable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class Account {
	
	private static Hashtable<String,Account> accounts = new Hashtable<String,Account>();
	
	@XmlElement
	public String email;
	@XmlElement
	public String login;
	
	private String hashedPassword;
	
	private Account(String id) {
		
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
