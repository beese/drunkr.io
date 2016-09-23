package drunkr.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
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
import drunkr.Password;
import drunkr.Signup;
import drunkr.UserLoader;

public class LoginTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()); 
	private Login s;
	
	@Before
	public void setUp() throws Exception {
		helper.setUp();
		s = new Login();
		UserLoader.deleteAll();
	}

	@After
	public void tearDown() throws Exception {
		UserLoader.deleteAll();
		helper.tearDown();
	}

	/**
	 * Attempt to create a new account and then successfully login
	 */
	@Test
	public void testLOGIN_001() throws IOException {
	    
		/* Test creating a new account */
		CreateAccountTest cat = new CreateAccountTest();
		cat.testACCOUNT_003();
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "password123456";
	    String username = "Andrew";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request to test login */
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
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_OK);

	}
	/**
	 * Attempt to successfully login
	 */
	@Test
	public void testLOGIN_003() throws IOException{
	    
		/* Add straight to datastore */
		try {
			UserLoader.saveUser("Andrew", "password123456", "andrew@domain.com");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "password123456";
	    String username = "Andrew";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request to test login */
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
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_OK);

	}
	/**
	 * Attempt to successfully login
	 */
	@Test
	public void testLOGIN_004() throws IOException {
	    
		/* Add straight to datastore */
		try {
			UserLoader.saveUser("Andrew", "password123456", "andrew@domain.com");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "password987654";
	    String username = "Andrew";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request to test login */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(response);
	    
	    // Should fail, invalid password
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);

	}
	/**
	 * Attempt to successfully login
	 */
	@Test
	public void testLOGIN_005passwordEmpty() throws IOException {
	    
		/* Add straight to datastore */
		try {
			UserLoader.saveUser("Andrew", "password123456", "andrew@domain.com");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "";
	    String username = "Andrew";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request to test login */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    // Fail, no password provided.
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);	    
	}
	/**
	 * Attempt to successfully login
	 * @throws IOException 
	 */
	@Test
	public void testLOGIN_005usernameEmpty() throws IOException {
	    
		/* Add straight to datastore */
		try {
			UserLoader.saveUser("Andrew", "password123456", "andrew@domain.com");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);

	    String password = "password123456";
	    String username = "";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    
	    /* Perform request to test login */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(response);
	    
	    // Fail, no username provided. Should be forbidden.
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);

	}
	/**
	 * Attempt to successfully login
	 * @throws IOException 
	 */
	@Test
	public void testLOGIN_006() throws IOException {
	    
		/* Add straight to datastore */
		try {
			UserLoader.saveUser("Andrew", "password123456", "andrew@domain.com");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* Get mock request / response for servelt */
		HttpServletRequest request = mock(HttpServletRequest.class);
	    HttpServletResponse response = mock(HttpServletResponse.class);
	    HttpSession session = mock(HttpSession.class);
	    String password = "password123456";
	    String username = "Andrew             ";
	    
	    /* Assign parameters to mock request */
	    when(request.getParameter("username")).thenReturn(username);
	    when(request.getParameter("password")).thenReturn(password);
	    when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
	    when(request.getSession()).thenReturn(session);
	    /* Perform request to test login */
	    try 
	    {
			s.doPost(request, response);
		} 
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    // Success
	    verify(response).setStatus(HttpStatusCodes.STATUS_CODE_OK);
	}
}
