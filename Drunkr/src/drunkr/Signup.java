package drunkr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.HttpStatusCodes;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

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
		
		/* Init a datastore session to perform the check */
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		/* Create Filter for Username */
		Filter uf = new FilterPredicate("Username", FilterOperator.EQUAL, user);
				
		/* Apply Filter to a Query on the Datastore */
		Query q = null;
		Entity u = null;
		try
		{
			/* Form Query for execution */
			q = new Query("User").setFilter(uf);
			
			/* Run Query on Datastore */
			PreparedQuery pq = datastore.prepare(q);
			
			/* There should only be one result since usernames are unique */
			u = pq.asSingleEntity();
			
		}
		catch(TooManyResultsException tm)
		{
			/* If there is more than one result, this exception will be thrown */
			// Fail handling
			jsonForbidden(resp, new APIError(APIErrorCode.UsernameAlreadyTaken, "Username is taken."));
			
			return;

		}
		catch(Exception e)
		{
			/* No results, login information is junk */
			// Fail handling
			json(resp, HttpStatusCodes.STATUS_CODE_SERVER_ERROR, new APIError(APIErrorCode.UnhandledException, e.toString()));
			
			return;
		}
		
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
		datastore.put(u);
		
		jsonOk(resp, u);
	}
}
