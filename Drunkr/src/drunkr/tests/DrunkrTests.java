package drunkr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import drunkr.Password;
import drunkr.UserLoader;

public class DrunkrTests {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()); 
	
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		UserLoader.deleteAll();
	}

	@After
	public void tearDown() throws Exception {
		UserLoader.deleteAll();
		helper.tearDown();
	}

	@Test
	public void testPassword() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String pw = Password.getHash("sample");
		
		assertTrue(Password.validate("sample", pw));
		assertFalse(Password.validate("not sample", pw));
	}
	
	@Test
	public void testCreateUser() throws InvalidKeySpecException, NoSuchAlgorithmException {
		Entity e = UserLoader.saveUser("a", "b", "a@b.com");
		
		assertNotNull(e);

		Entity n = UserLoader.getUserByUsername("a");
		
		assertNotNull(n);
		
		assertEquals(e.getProperty("Username"), n.getProperty("Username"));
		assertTrue(Password.validate("b", e.getProperty("Password").toString()));
	}
	
}
