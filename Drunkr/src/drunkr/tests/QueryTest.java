package drunkr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.client.http.HttpStatusCodes;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import drunkr.QueryDrink;
import drunkr.UserLoader;
import drunkr.CreateDrink;
import drunkr.DrinkLoader;
import drunkr.Ingredient;
public class QueryTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()); 
	private QueryDrink s;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		s = new QueryDrink();
	}

	@After
	public void tearDown() throws Exception {
		helper.tearDown();
	}

	/**
	 * Attempt simple query "Rum and Coke".
	 */
	@Test
	public void testQuery_001() throws IOException{
	    
		/* Create Drinks */
		loadDrinks();
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    
	    /* Assign parameters to mock request */
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    when(request.getParameter("query")).thenReturn("Rum and Coke");
	    when(response.getWriter()).thenReturn(pw);
	    when(request.getSession()).thenReturn(session);
	   
	    
	    /* Perform request */
	    try 
	    {
			s.doGet(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(response);
	    System.out.println("Output: " + sw.toString());
	    
	    // Success
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_OK);

	}
	public void loadDrinks()
	{
		/* Create Drink */
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Rum", 1, "part", 100.0));
		ingredients.add(new Ingredient("Coke", 1, "part", 100.0));
		
		try {
			DrinkLoader.saveDrink("Rum and Coke", "Splash of Rum with Coke", ingredients, 2, 2.0, 4);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* Create Drink */
		ingredients = new ArrayList<Ingredient>();
		ingredients.add(new Ingredient("Vodka", 1, "part", 100.0));
		ingredients.add(new Ingredient("Tonic", 1, "part", 100.0));
		
		try {
			DrinkLoader.saveDrink("Vodka and Tonic", "Splash of Vodka with Tonic", ingredients, 2, 2.0, 4);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
