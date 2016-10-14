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
	/**
	 * Attempt simple query for 2.0
	 */
	@Test
	public void testQuery_002() throws IOException{
	    
		/* Create Drinks */
		loadDrinks();
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    
	    /* Assign parameters to mock request */
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    when(request.getParameter("query")).thenReturn("2.0");
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
	/**
	 * From a bunch of drinks, search on a unique ingredient
	 */
	@Test
	public void DRINKFILTER_001() throws IOException{
	    
		/* Create Drinks */
		loadDrinks();
				
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    
	    /* Assign parameters to mock request */
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    when(request.getParameter("query")).thenReturn("2.0");
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
	    
	    int lastIndex = 0;
	    int count = 0;
	    
	    String str = sw.toString();
	    while(lastIndex != -1){

	        lastIndex = str.indexOf("Sprite",lastIndex);

	        if(lastIndex != -1){
	            count ++;
	            lastIndex += "Sprite".length();
	        }
	    }

	    assertTrue("1 results returned", count == 1);
	    
	}
	/**
	 * From a bunch of drinks, search on a unique ingredient
	 */
	@Test
	public void DRINKFILTER_002() throws IOException{
	    
		/* Create Drinks */
		loadDrinks();
				
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    
	    /* Assign parameters to mock request */
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    when(request.getParameter("query")).thenReturn("Rum and Sprite");
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
	    
	    int lastIndex = 0;
	    int count = 0;
	    
	    String str = sw.toString();
	    while(lastIndex != -1){

	        lastIndex = str.indexOf("Sprite",lastIndex);

	        if(lastIndex != -1){
	            count ++;
	            lastIndex += "Sprite".length();
	        }
	    }

	    assertTrue("5 results returned", count == 5);
	    
	}
	/**
	 * From a bunch of drinks, search on a unique ingredient
	 */
	@Test
	public void DRINKFILTER_003() throws IOException{
	    
		/* Create Drinks */
		loadThousandDrinks();
		
		/* Add 5 Sprite drinks */
		loadUnique();
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    
	    /* Assign parameters to mock request */
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    when(request.getParameter("query")).thenReturn("Tequila and Sprite");
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
	    
	    int lastIndex = 0;
	    int count = 0;
	    
	    String str = sw.toString();
	    while(lastIndex != -1){

	        lastIndex = str.indexOf("Tequila and Sprite",lastIndex);

	        if(lastIndex != -1){
	            count ++;
	            lastIndex += "Tequila and Sprite".length();
	        }
	    }

	    assertTrue("1 result returned", count == 1);
	    
	}
	/**
	 * From a bunch of drinks, just get Sprite drinks (5).
	 */
	@Test
	public void DRINKFILTER_004() throws IOException{
	    
		/* Create Drinks */
		loadThousandDrinks();
		
		/* Add 5 Sprite drinks */
		loadSprite();
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    
	    /* Assign parameters to mock request */
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    when(request.getParameter("query")).thenReturn("Sprite");
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
	    
	    int lastIndex = 0;
	    int count = 0;
	    
	    String str = sw.toString();
	    while(lastIndex != -1){

	        lastIndex = str.indexOf("Sprite",lastIndex);

	        if(lastIndex != -1){
	            count ++;
	            lastIndex += "Sprite".length();
	        }
	    }

	    assertTrue("5 results returned", count == 5);
	    
	}
	/**
	 * Attempt simple query for 2.0
	 */
	@Test
	public void DRINKFILTER_005() throws IOException{
	    
		/* Create Drinks */
		loadDrinks();
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    
	    /* Assign parameters to mock request */
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    when(request.getParameter("query")).thenReturn("Whiskey");
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
	    
	    // Should return an empty array of 0 results.
	    assertTrue("0 results returned", sw.toString().equals("[]"));


	}
	/**
	 * Attempt simple query for 2.0
	 */
	@Test
	public void DRINKFILTER_006() throws IOException{
	    
		/* Create Drinks */
		loadThousandDrinks();
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    
	    /* Assign parameters to mock request */
	    StringWriter sw = new StringWriter();
	    PrintWriter pw = new PrintWriter(sw);
	    
	    when(request.getParameter("query")).thenReturn("Rum");
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
	    
	    int lastIndex = 0;
	    int count = 0;
	    
	    String str = sw.toString();
	    while(lastIndex != -1){

	        lastIndex = str.indexOf("Splash of Rum with Coke",lastIndex);

	        if(lastIndex != -1){
	            count ++;
	            lastIndex += "Splash of Rum with Coke".length();
	        }
	    }
	    assertTrue("50 results returned", count == 50);
	    
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
			DrinkLoader.saveDrink("Vodka and Tonic", "Splash of Vodka with Tonic", ingredients, 1, 1.0, 1);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public void loadThousandDrinks()
	{
		for(int i = 0; i < 1000; i++)
		{
			/* Create Drink */
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			ingredients.add(new Ingredient("Rum" + i, 1, "part", 100.0));
			ingredients.add(new Ingredient("Coke", 1, "part", 100.0));
			
			try {
				DrinkLoader.saveDrink("Rum and Coke" + i, "Splash of Rum with Coke", ingredients, 2, 2.0, 4);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	public void loadSprite()
	{
		for(int i = 0; i < 5; i++)
		{
			/* Create Drink */
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			ingredients.add(new Ingredient("Rum" + i, 1, "part", 100.0));
			ingredients.add(new Ingredient("Sprite", 1, "part", 100.0));
			
			try {
				DrinkLoader.saveDrink("Rum and Sprite" + i, "Splash of Rum with Sprite", ingredients, 2, 2.0, 4);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}	
	public void loadUnique()
	{
		for(int i = 0; i < 5; i++)
		{
			/* Create Drink */
			List<Ingredient> ingredients = new ArrayList<Ingredient>();
			ingredients.add(new Ingredient("Tequila" + i, 1, "part", 100.0));
			ingredients.add(new Ingredient("Sprite", 1, "part", 100.0));
			
			try {
				DrinkLoader.saveDrink("Tequila and Sprite" + i, "Splash of Rum with Sprite", ingredients, 2, 2.0, 4);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
