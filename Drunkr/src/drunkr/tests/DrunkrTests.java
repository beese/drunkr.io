package drunkr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
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

import com.google.api.client.http.HttpStatusCodes;
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
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
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
	public void testDRINKCREATE_001() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Orange Juice", 3.0, "oz", 0.0));
		ingredients.add(new Ingredient("Vodka", 1.5, "oz", .4));
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		when(request.getParameter("name")).thenReturn(" ");
	    when(request.getParameter("description")).thenReturn("test");
	    when(request.getParameter("tasteRating")).thenReturn("0");
	    when(request.getParameter("averageRating")).thenReturn("0");
	    Gson g = new Gson();
	    
	    when(request.getParameter("ingredients")).thenReturn(g.toJson(ingredients));
	    
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	   
		createServlet.doPost(request, response);
		
		verify(response).setStatus(HttpStatusCodes.STATUS_CODE_SERVER_ERROR);
		//System.out.println(response);
		//Entity d = DrinkLoader.saveDrink(" ", "test", ingredients, 0, 0, 0);
		
		
		
		
		
		
	
	}
	@Test
	public void testDRINKCREATE_002() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		when(request.getParameter("name")).thenReturn("empty");
	    when(request.getParameter("description")).thenReturn("test");
	    when(request.getParameter("tasteRating")).thenReturn("0");
	    when(request.getParameter("averageRating")).thenReturn("0");
	    Gson g = new Gson();
	    
	    when(request.getParameter("ingredients")).thenReturn(g.toJson(ingredients));
	    
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	   
		createServlet.doPost(request, response);
		
		verify(response).setStatus(HttpStatusCodes.STATUS_CODE_SERVER_ERROR);
		
	}
	@Test
	public void testDRINKCREATE_005() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		when(request.getParameter("name")).thenReturn("");
	    when(request.getParameter("description")).thenReturn("");
	    when(request.getParameter("tasteRating")).thenReturn("0");
	    when(request.getParameter("averageRating")).thenReturn("0");
	    Gson g = new Gson();
	    
	    when(request.getParameter("ingredients")).thenReturn(g.toJson(ingredients));
	    
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	   
		createServlet.doPost(request, response);
		
		verify(response).setStatus(HttpStatusCodes.STATUS_CODE_SERVER_ERROR);
		
	}
	@Test
	public void testDRINKCREATE_006() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient(" Orange Juice ", 3.0, "oz", 0.0));
		ingredients.add(new Ingredient("Vodka", 1.5, "oz", .4));
		
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		
		when(request.getParameter("name")).thenReturn(" ScrewDriver ");
	    when(request.getParameter("description")).thenReturn("test");
	    when(request.getParameter("tasteRating")).thenReturn("0");
	    when(request.getParameter("averageRating")).thenReturn("0");
	    Gson g = new Gson();
	    
	    when(request.getParameter("ingredients")).thenReturn(g.toJson(ingredients));
	    
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	   
		createServlet.doPost(request, response);
		
		verify(response).setStatus(HttpStatusCodes.STATUS_CODE_OK);
		
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
	
	/* Adding a rating to a new drink */
	@Test
	public void DRINKRATE_001() throws JSONException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse res = mock(HttpServletResponse.class);
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("alcohol", 2, "oz", 0.4));
		ingredients.add(new Ingredient("water", 2, "oz", 0));
		
		Entity d = DrinkLoader.saveDrink("DRINKRATE_001", "DRINKRATE_001 Drink", ingredients, 3, 0, 0);
		long drinkID = d.getKey().getId();
		
		when(req.getParameter("rating")).thenReturn("4");
		when(req.getParameter("drinkID")).thenReturn(Long.toString(drinkID));
	    	when(res.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
		
		try {
			rateServlet.doPost(req, res);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	    Entity rating = RatingLoader.getRatingById(drinkID);
	    
	    assertEquals(1l, rating.getProperty("totalRatings"));
	}
	
	/* Adding a rating to an existing drink */
	@Test
	public void DRINKRATE_002() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("alcohol", 2, "oz", 0.4));
		ingredients.add(new Ingredient("water", 2, "oz", 0));
		
		Entity d = DrinkLoader.saveDrink("DRINKRATE_002", "DRINKRATE_002 Drink", ingredients, 3, 0, 0);
		long drinkID = d.getKey().getId();
		
		for(int i = 1; i < 5; i++) {

			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse res = mock(HttpServletResponse.class);
			
			when(req.getParameter("rating")).thenReturn(Integer.toString(i));
			when(req.getParameter("drinkID")).thenReturn(Long.toString(drinkID));
			when(res.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
			
			try {
				rateServlet.doPost(req, res);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
	    Entity rating = RatingLoader.getRatingById(drinkID);
	    
	    assertEquals(2.5, rating.getProperty("averageRating"));
	}
	
	/* Assign a drink with a 5 star rating a 1 star rating */
	@Test
	public void DRINKRATE_003() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("alcohol", 2, "oz", 0.4));
		ingredients.add(new Ingredient("water", 2, "oz", 0));
		
		Entity d = DrinkLoader.saveDrink("DRINKRATE_003", "DRINKRATE_003 Drink", ingredients, 3, 0, 0);
		long drinkID = d.getKey().getId();
		
		int[] ratings = {5, 1};
		for(int i = 0; i < 2; i++) {

			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse res = mock(HttpServletResponse.class);
			
			when(req.getParameter("rating")).thenReturn(Integer.toString(ratings[i]));
			when(req.getParameter("drinkID")).thenReturn(Long.toString(drinkID));
			when(res.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
			
			try {
				rateServlet.doPost(req, res);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
	    Entity rating = RatingLoader.getRatingById(drinkID);
	    
	    assertEquals(3.0, rating.getProperty("averageRating"));
	}
	
	/* Assign a drink with two 1 star ratings two 5 star ratings */
	@Test
	public void DRINKRATE_004() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("alcohol", 2, "oz", 0.4));
		ingredients.add(new Ingredient("water", 2, "oz", 0));
		
		Entity d = DrinkLoader.saveDrink("DRINKRATE_004", "DRINKRATE_004 Drink", ingredients, 3, 0, 0);
		long drinkID = d.getKey().getId();
		
		int[] ratings = {1, 1, 5, 5};
		for(int i = 0; i < 4; i++) {

			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse res = mock(HttpServletResponse.class);
			
			when(req.getParameter("rating")).thenReturn(Integer.toString(ratings[i]));
			when(req.getParameter("drinkID")).thenReturn(Long.toString(drinkID));
			when(res.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
			
			try {
				rateServlet.doPost(req, res);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
	    Entity rating = RatingLoader.getRatingById(drinkID);
	    
	    assertEquals(3.0, rating.getProperty("averageRating"));
	}	
	
	/* Assign a rating to a drink with no ratings */
	@Test
	public void DRINKRATE_005() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse res = mock(HttpServletResponse.class);
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("alcohol", 2, "oz", 0.4));
		ingredients.add(new Ingredient("water", 2, "oz", 0));
		
		Entity d = DrinkLoader.saveDrink("DRINKRATE_005", "DRINKRATE_005 Drink", ingredients, 3, 0, 0);
		long drinkID = d.getKey().getId();
		
		when(req.getParameter("rating")).thenReturn("5");
		when(req.getParameter("drinkID")).thenReturn(Long.toString(drinkID));
	    when(res.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
		
		try {
			rateServlet.doPost(req, res);
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	    Entity rating = RatingLoader.getRatingById(drinkID);
	    
	    assertEquals(5.0, rating.getProperty("averageRating"));
	}
	
	/* Assign a rating to a drink with 1000 ratings */
	@Test
	public void DRINKRATE_006() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		// Assign a rating to a drink with 1000 ratings
		// - create a drink
		// - give 1000 random ratings
		// - check average + #
		// - add one 5 star rating
		// compare # + rating
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("alcohol", 2, "oz", 0.4));
		ingredients.add(new Ingredient("water", 2, "oz", 0));
		
		Entity d = DrinkLoader.saveDrink("DRINKRATE_006", "DRINKRATE_006 Drink", ingredients, 3, 0, 0);
		long drinkID = d.getKey().getId();
		
		for(int i = 0; i < 1000; i++) {

			HttpServletRequest req = mock(HttpServletRequest.class);
			HttpServletResponse res = mock(HttpServletResponse.class);
			
			int nextRating = (int) Math.floor(Math.random() * 5 + 1);
			when(req.getParameter("rating")).thenReturn(Integer.toString(nextRating));
			when(req.getParameter("drinkID")).thenReturn(Long.toString(drinkID));
			when(res.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
			
			try {
				rateServlet.doPost(req, res);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
				
	    Entity oldRating = RatingLoader.getRatingById(drinkID);
	    double oldR = (double) oldRating.getProperty("averageRating");
	    

		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse res = mock(HttpServletResponse.class);
		
		when(req.getParameter("rating")).thenReturn("5");
		when(req.getParameter("drinkID")).thenReturn(Long.toString(drinkID));
		when(res.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
		
		try {
			rateServlet.doPost(req, res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    double newR = (oldR*1000 + 5)/1001; 
	    Entity newRating = RatingLoader.getRatingById(drinkID);
	    
	    assertEquals(newR, (double) newRating.getProperty("averageRating"), 0.001);
	}

}
