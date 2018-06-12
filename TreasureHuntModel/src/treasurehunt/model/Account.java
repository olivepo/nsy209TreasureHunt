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
	private byte[] passwordSalt;
	
	private Account(String id) {
		
	}
	
	@XmlElement
	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public void setPassword(String password) {
		
		this.hashedPassword = hash(password);
	}
	
	public static Account getInstance(String id) {
		if(!accounts.containsKey(id)) {
			accounts.put(id, new Account(id));
		}
		return accounts.get(id);
	}
	
	public static String hash(String password) {
		// TODO : hashage du mot de passe
		return password;
	}
	

}
