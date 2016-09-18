package drunkr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import drunkr.api.APIError;
import drunkr.api.APIError.APIErrorCode;
import drunkr.api.JsonServlet;
/**
 * This class processes signup POST requests from signup.html.
 * @author Andrew Blejde
 */
@SuppressWarnings("serial")
public class Signup extends JsonServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {		
		System.out.println("Attempting sign up");
		/* Get parameters from login attempt */
		String user = request.getParameter("username");
		String pw = request.getParameter("password");
		String email = request.getParameter("email");
		
		Entity u = UserLoader.getUserByUsername(user);
		
		/* Existence check. U will not be null if an existing
		 * User with this username exists.
		 */
		if(u != null)
		{
			jsonForbidden(resp, new APIError(APIErrorCode.UsernameAlreadyTaken, "Username is taken."));
			
			return;
		}
		
		/* Encrypt password using PBKDF2 */
		try
		{
			pw = Password.getHash(pw);
		}
		catch(Exception e)
		{
			// Error occured
		}
		/* Set Entity properties */
		u = new Entity("User");
		u.setProperty("Username", user);
		u.setProperty("Password", pw);
		u.setProperty("Email", email);
		
		/* Add new User to the datastore */
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(u);
		
		HttpSession session = request.getSession();
		session.setAttribute("User", u.getProperty("Username"));

		session.setMaxInactiveInterval(365*24*60*60);
		
		jsonOk(resp, u);
	}
}
