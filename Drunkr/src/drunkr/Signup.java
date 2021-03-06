package drunkr;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.http.HttpStatusCodes;
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
		
		/* Remove white space */
		user = user.replaceAll("\\s+","");
		
		String pw = request.getParameter("password");
		String email = request.getParameter("email");
		
		/* Check password for length validity */
		if(pw.length() < 12)
		{
			jsonForbidden(resp, new APIError(APIErrorCode.InvalidPassword, "Password length must be greater than 12 characters."));
			return;
		}
		
		/* Check if password contains a number */
		if(!pw.matches(".*\\d+.*"))
		{
			jsonForbidden(resp, new APIError(APIErrorCode.InvalidPassword, "Password must include a number."));
			return;
		}
		
		/* Check for empty username */
		if(user.length() == 0)
		{
			jsonForbidden(resp, new APIError(APIErrorCode.InvalidUsername, "No username provided."));
			return;
		}
		
		/* Check email */
		if(email.length() == 0)
		{
			jsonForbidden(resp, new APIError(APIErrorCode.InvalidUsername, "No email provided."));
			return;
		}
		/* Check if email taken */
		else {
			Entity u = UserLoader.getUserByEmail(email);
			if (u != null) {
				jsonForbidden(resp, new APIError(APIErrorCode.InvalidEmail, "Email is taken."));

				return;
			}
		}
		Entity u = UserLoader.getUserByUsername(user);
		
		/* Existence check. U will not be null if an existing
		 * User with this username exists.
		 */
		if(u != null)
		{
			jsonForbidden(resp, new APIError(APIErrorCode.UsernameAlreadyTaken, "Username is taken."));
			
			return;
		}
		
		try {
			u = UserLoader.saveUser(user, pw, email);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			json(resp, HttpStatusCodes.STATUS_CODE_SERVER_ERROR, new APIError(APIErrorCode.UnhandledException, e.toString()));
			return;
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("User", u.getProperty("Username"));

		session.setMaxInactiveInterval(365*24*60*60);
		
		jsonOk(resp, u);
	}
}
