package drunkr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
<<<<<<< HEAD
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
=======
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
>>>>>>> origin/master
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import drunkr.DrinkLoader;
import drunkr.CreateDrink;
import drunkr.Ingredient;
import drunkr.Password;
import drunkr.RateDrink;
import drunkr.UserLoader;
import drunkr.RatingLoader;

import static org.mockito.Mockito.*;

public class DrunkrTests {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()); 
	private RateDrink rateServlet;
	public CreateDrink createServlet;
	private final String host = "http://localhost:8888/";
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		rateServlet = new RateDrink();
		createServlet = new CreateDrink();
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
	public void testDRINKCREATE_001() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 3.0, "oz", 0.0));
		ingredients.add(new Ingredient("Vodka", 1.5, "oz", .4));
		
		
		Entity d = DrinkLoader.saveDrink(" ", "test", ingredients, 0, 0, 0);
		
		assertEquals(d, null);
	
	}
	@Test
	public void testDRINKCREATE_002() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		
		
		Entity d = DrinkLoader.saveDrink("empty", "test", ingredients, 0, 0, 0);
		
		assertEquals(d, null);
		
	}
	@Test
	public void testDRINKCREATE_005() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		
		
		Entity d = DrinkLoader.saveDrink("", "test", ingredients, 0, 0, 0);
		
		assertEquals(d, null);
		
	}
	@Test
	public void testDRINKCREATE_006() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 3.0, "oz", 0.0));
		ingredients.add(new Ingredient("Vodka", 1.5, "oz", .4));
		
		
		Entity d = DrinkLoader.saveDrink(" ScrewDriver ", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("Name"), "ScrewDriver");
		
	}
	@Test
	public void testSTRENGTH_001() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 8.0, "oz", 0.0));
		ingredients.add(new Ingredient("Vodka", 2.0, "oz", .4));
		
		
		Entity d = DrinkLoader.saveDrink("ScrewDriver", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("AlcoholContent"), 8.0);
		
	}
	
	@Test
	public void testSTRENGTH_003() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 7.0, "oz", 0.0));
		ingredients.add(new Ingredient("Vodka", 2.0, "oz", .35));
		
		
		Entity d = DrinkLoader.saveDrink("ScrewDriver", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("AlcoholContent"), 7.8);
		
	}
	
	@Test
	public void testSTRENGTH_004() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 1.0, "oz", 0.0));
		ingredients.add(new Ingredient("Vodka", 2.0, "oz", .4));
		
		
		Entity d = DrinkLoader.saveDrink("ScrewDriver", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("AlcoholContent"), 26.7);
		
	}
	
	@Test
	public void testSTRENGTH_005() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 3.0, "oz", 0.0));
		
		
		Entity d = DrinkLoader.saveDrink("Orange Juice", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("AlcoholContent"), 0.0);
		
	}
	
	@Test
	public void testSTRENGTH_006() throws InvalidKeySpecException, NoSuchAlgorithmException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Vodka", 2.0, "oz", .4));
		
		
		Entity d = DrinkLoader.saveDrink("death", "test", ingredients, 0, 0, 0);
		
		assertEquals(d.getProperty("AlcoholContent"), 40.0);
		
	}
	
	public String postRequest(String path, JSONObject input) throws IOException {
		String abs_path = host + path;
		URL url = new URL(abs_path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(input.toString());
        
        // get output
        StringBuilder sb = new StringBuilder();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } finally {
	        reader.close();
	    }
	    conn.disconnect();
		String output = sb.toString();
		return output;
	}
	
	@Test
	public void DRINKRATE_001() throws IOException, JSONException, NoSuchAlgorithmException, InvalidKeySpecException {
		// Adding a rating to a new drink
		// aveDrink(String drinkName, String description, List<Ingredient> ingredients, 
		// int tasteRating, double averageRating, int totalRatings)

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse res = mock(HttpServletResponse.class);
		
		// Add a drink
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("alcohol", 2, "oz", 0.4));
		ingredients.add(new Ingredient("water", 2, "oz", 0));
		
		Entity d = DrinkLoader.saveDrink("DRINKRATE_001", "DRINKRATE_001 Drink", ingredients, 3, 0, 0);
		long drinkID = d.getKey().getId();
		
		when(req.getParameter("rating")).thenReturn(RatingLoader.getRatingById(drinkID).toString());
		when(req.getParameter("drinkID")).thenReturn(Long.toString(drinkID));
		
		
		rateServlet.doPost(req, res);
		
		System.out.println(res);
		// - assign a rating to it
		
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

		// ER - drink goes from no ratings to 1
	}
	
	@Test
	public void DRINKRATE_002() throws IOException {
		
		// Adding a rating to an already existing drink		
		// - add a drink + a few ratings
		// - record old rating
		// - give a new rating
		// ER - # of ratings + average rating update correctly
	}
	
	@Test
	public void DRINKRATE_003() {
		// Assign a drink with a 5 star rating a 1 star rating
		// - create a drink
		// - give it a 5 star rating
		// - give it a 1 star rating
		// ER - 2 ratings, ave 2.5
	}
	
	@Test
	public void DRINKRATE_004() {
		// Assign a drink with 2 1 star ratings 2 5 star ratings
		// - create a drink
		// - give it two 1 star ratings
		// - give it two 5 star ratings
		// ER - 4 ratings, average 2.5 stars
	}	
	
	@Test
	public void DRINKRATE_005() {
		// Assign a rating two a drink with no ratings
		// - create a drink
		// - give it a 5 star rating
		// ER - drink no rating 0 stars -> 1 rating 5 stars
	}
	
	@Test
	public void DRINKRATE_006() {
		// Assign a rating to a drink with 1000 ratings
		// - create a drink
		// - give 1000 random ratings
		// - check average + #
		// - add one 5 star rating
		// compare # + rating
	}

}
