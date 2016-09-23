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

import drunkr.Login;
import drunkr.Signup;
import drunkr.UserLoader;

public class CreateAccountTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()); 
	private Signup s;

	@Before
	public void setUp() throws Exception {
		helper.setUp();
		s = new Signup();
		UserLoader.deleteAll();
	}

	@After
	public void tearDown() throws Exception {
		UserLoader.deleteAll();
		helper.tearDown();
	}

	/**
	 * Attempt to create an account with a password that contains
	 * too few characters.
	 */
	@Test
	public void testACCOUNT_001() throws IOException{
	    
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "short";
	    String username = "Andrew";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(response);
	    
	    // Success
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);

	}
	/**
	 * Attempt to create an account with a password that does
	 * not contain a number.
	 */
	@Test
	public void testACCOUNT_002() throws IOException{
	    
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "shortshortshosrt";
	    String username = "Andrew";
	    String email = "andrew@domain.com";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getParameter("email")).thenReturn(email);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Fail
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);

	}
	/**
	 * Create a valid account. Should return jsonOK().
	 */
	@Test
	public void testACCOUNT_003() throws IOException{
	    
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "password123456";
	    String username = "Andrew";
	    String email = "andrew@domain.com";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getParameter("email")).thenReturn(email);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request */
	    try 
	    {
	    	if(s == null)
	    		s = new Signup();
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Success
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_OK);

	}
	/**
	 * Test situation where account username is already taken.
	 */
	@Test
	public void testACCOUNT_004() throws IOException{
	    
		/* Create and insert a new User */
		try 
		{
			UserLoader.saveUser("Andrew", "password123456", "andrew@domain.com");
		} 
		catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    /* Create user to insert with same username as above */
	    String password = "password123456";
	    String username = "Andrew";
	    String email = "andrew@domain.com";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getParameter("email")).thenReturn(email);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(response);
	    
	    // Fail
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);
	}
	/**
	 * Try empty password field.
	 */
	@Test
	public void testACCOUNT_005password() throws IOException {
	    
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "";
	    String username = "Andrew";
	    String email = "andrew@domain.com";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getParameter("email")).thenReturn(email);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Fail, expect failed password
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);
	}
	/**
	 * Try and create an account with an empty username
	 */
	@Test
	public void testACCOUNT_005username() throws IOException{
	    
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "pasword123456";
	    String username = "";
	    String email = "andrew@domain.com";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getParameter("email")).thenReturn(email);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Fail, expect failed username
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);
	}
	/**
	 * Try and create an account with an empty email
	 */
	@Test
	public void testACCOUNT_005email() throws IOException{
	    
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "pasword123456";
	    String username = "Andrew";
	    String email = "";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getParameter("email")).thenReturn(email);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    // Fail, expect failed email
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);
	}
	/**
	 * Try and create an account with an empty username
	 */
	@Test
	public void testACCOUNT_006() throws IOException{
	    
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "pasword123456";
	    String username = "Andrew       ";
	    String email = "andrew@domain.com";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getParameter("email")).thenReturn(email);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(response);
	    // Succeed, no white space
	    
	    Entity e = UserLoader.getUserByUsername("Andrew");
	    if(e != null)
	    {
		    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_OK);
	    }
	    else
	    {
	    	// Fail 
	    }
	}
}
