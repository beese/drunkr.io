package drunkr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import drunkr.DrinkLoader;
import drunkr.Ingredient;
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
	@Test
	public void testCreateDrink() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 3.0, "oz", 0.0));
		ingredients.add(new Ingredient("Vodka", 1.5, "oz", .4));
		
		
		Entity d = DrinkLoader.saveDrink(" ", "test", ingredients, 0, 0, 0);
		
		assertEquals(d, null);
		
		d = DrinkLoader.saveDrink("Screwdriver", "test", ingredients, 0, 0, 0);
		
		assertNotNull(d);
		
		ingredients.clear();
		
		d = DrinkLoader.saveDrink("Screwdriver", "test", ingredients, 0, 0, 0);
		
		assertEquals(d, null);
		
		ingredients.add(new Ingredient("Orange Juice", 3.0, "oz", 0.0));
		
		d = DrinkLoader.saveDrink("Orange Juice", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("AlcoholContent"), 0.0);
		
		ingredients.clear();
		
		ingredients.add(new Ingredient("Everclear", 1.5, "oz", 0.95));
		
		d = DrinkLoader.saveDrink("Death", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("AlcoholContent"), 0.95);
		
		ingredients.clear();
		
		ingredients.add(new Ingredient("Cola", 8.0, "oz", 0.0));
		
		ingredients.add(new Ingredient("vodka", 2.0, "oz", 0.40));
		
		d = DrinkLoader.saveDrink("Vodka Coke", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("AlcoholContent"), 0.08);
		
		
		
	}
	
}
