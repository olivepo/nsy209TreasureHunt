package treasurehunt.client;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.List;

import treasurehunt.client.AccountRESTMethods;
import treasurehunt.model.Account;

public class AccountRESTMethodsTest {
	
	private final static String email = "test@montest.fr";
	private final static String login = "monLogin";
	private final static String password = "pass";
	
	@Test
	public void testAll() {
		
		testDelete();
		// base vide : Echec des op�rations GET,DELETE
		assertFalse(testGet());
		//assertFalse(testGetAll());
		assertFalse(testDelete());
		
		// insertion de 1 �l�ment
		assertTrue(testPut());
		
		// base compl�te : r�ussite des op�rations GET,DELETE
		assertTrue(testGet());
		//assertTrue(testGetAll());
		assertTrue(testDelete());
		
		// base � nouveau vide
		assertFalse(testGet());
	}
	
	
	private boolean testPut() {
		
		Account a = Account.getInstance(email);
		a.login = login;
		a.setPassword(password);
		try {
			return AccountRESTMethods.put(a);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	private boolean testGet() {
		
		Account a;
		try {
			a = AccountRESTMethods.get(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (a == null) return false;
		if (!a.login.equals(login)) return false;
		if (!a.checkPassword(password)) return false;
		return true;
		
	}
	
	private boolean testDelete() {
		
		try {
			return AccountRESTMethods.delete(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	private boolean testGetAll() {
		
		List<Account> list;
		try {
			list = AccountRESTMethods.getAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return !list.isEmpty();
		
	}

}