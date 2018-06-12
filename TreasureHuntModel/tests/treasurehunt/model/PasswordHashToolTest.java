package treasurehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PasswordHashToolTest {

	@Test
	void testCheck() {
		final String pwd = "myPassword";
		try {
			assertTrue(PasswordHashTool.check(pwd, PasswordHashTool.getSaltedHash(pwd)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
