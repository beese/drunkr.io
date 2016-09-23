package drunkr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
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

	// Make sure only one entry is made in datastore for each drink creation	
	@Test
	public void INFOSECURE_01() throws NoSuchAlgorithmException, InvalidKeySpecException {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 3.0, "oz", 0.0));

		//Use random to make sure we create a unique entry in datastore for test
		Random r = new Random();
		int temp = r.nextInt();

		String testName = "Test Drink Name:" + temp;
		
		DrinkLoader.saveDrink(testName, "test", ingredients, 0, 0, 0);
		
		// Format Filter for Query
		Filter filter = new FilterPredicate("Name", FilterOperator.EQUAL, testName);

		Query q = null;

		q = new Query("Drink").setFilter(filter);

		//Get all entities matching filtered query, get count
		List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
        int count = results.size();

		assertNotNull(count);
		assertEquals(1, count);
	}

	// Test password encryption and validation
	@Test
	public void PASSWORDSEC_01() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Entity e = UserLoader.saveUser("a", "b", "a@b.com");
		
		assertNotNull(e);

		Entity n = UserLoader.getUserByUsername("a");
		
		assertNotNull(n);
		
		assertEquals(e.getProperty("Username"), n.getProperty("Username"));
		assertTrue(Password.validate("b", e.getProperty("Password").toString()));
	}

	// Test if password is correct
	@Test
	public void PASSWORDSEC_03() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Entity e = UserLoader.saveUser("a", "b", "a@b.com");
		
		assertNotNull(e);
		
		assertTrue(Password.validate("b", e.getProperty("Password").toString()));
	}
	
	// Test what happens when password is incorrect
	@Test
	public void PASSWORDSEC_04() throws NoSuchAlgorithmException, InvalidKeySpecException {
		Entity e = UserLoader.saveUser("a", "b", "a@b.com");
		
		assertNotNull(e);
		
		assertFalse(Password.validate("c", e.getProperty("Password").toString()));
	}

	// Empty string provided for password
	@Test
	public void PASSWORDSEC_05() throws NoSuchAlgorithmException, InvalidKeySpecException {
		/* TODO:
		*  This test fails while UserLoader still takes empty password string
		*  Change UserLoader to send error to server OR make sure form cannot be submittited with empty password field
		*/
		Entity e = UserLoader.saveUser("abcdef", "", "a@b.com");
		
		assertNotNull(e);
		
		Entity n = UserLoader.getUserByUsername("abcdef");
		
		assertEquals(n, null);
	}
	
}
