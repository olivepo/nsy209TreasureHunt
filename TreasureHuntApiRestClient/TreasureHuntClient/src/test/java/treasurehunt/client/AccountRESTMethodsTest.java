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
	private final static String baseUrl = "http://35.234.90.191/TreasureHuntApiRestServer/api/";

	@Test
	public void testAll() {
		
		//Configuration.baseUrl = baseUrl;
		
		testDelete();
		// base vide : Echec des operations GET,DELETE
		assertFalse(testGet());
		assertFalse(testDelete());

		// insertion de 1 element
		assertTrue(testPut());

		// base complete : réussite des opérations GET,DELETE
		assertTrue(testGet());
		assertTrue(testGetAll());
		/*assertTrue(testDelete());

		// base a nouveau vide
		assertFalse(testGet());*/
	}


	private boolean testPut() {

		Account a = Account.getInstance(email);
		a.login = login;
		a.setPassword(password);
		try {
			return AccountRESTMethods.put(a);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean testGet() {

		Account a;
		try {
			a = AccountRESTMethods.get(email);
		} catch (Exception e) {
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
			e.printStackTrace();
			return false;
		}

	}

	private boolean testGetAll() {

		List<Account> list;
		try {
			list = AccountRESTMethods.getAll();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return !list.isEmpty();

	}

}