package treasurehunt.model;

import static org.junit.Assert.*;
import org.junit.Test;

public class PasswordHashToolTest {

	@Test
	public void testCheck() {
		final String pwd = "myPassword";
		try {
			assertTrue(PasswordHashTool.check(pwd, PasswordHashTool.getSaltedHash(pwd)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
