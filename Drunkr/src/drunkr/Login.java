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
 * This class processes login POST requests from the login page.
 * @author Andrew Blejde
 */
@SuppressWarnings("serial")
public class Login extends JsonServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {		
		/* Get parameters from login attempt */
		String user = request.getParameter("username");
		String pw = request.getParameter("password");
		
		
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
			jsonForbidden(resp, new APIError(APIErrorCode.TooManyResultsFound, "Too many results found."));
			
			return;

		}
		catch(Exception e)
		{
			/* No results, login information is junk */
			// Fail handling			
			json(resp, HttpStatusCodes.STATUS_CODE_SERVER_ERROR, new APIError(APIErrorCode.UnhandledException, e.toString()));

		}
		
		if(u == null)
		{
			jsonForbidden(resp, new APIError(APIErrorCode.CombinationNotFound, "User & Password Combination not found."));
		}
		else
		{
			/* Now test the Password for this User entity */
			try 
			{
				if(Password.validate(pw, (String)u.getProperty("Password")))
				{
					HttpSession session = request.getSession();
					session.setAttribute("User", u.getProperty("Username"));

					session.setMaxInactiveInterval(365*24*60*60);
					
					jsonOk(resp, u);
				}
				else
				{
					jsonForbidden(resp, new APIError(APIErrorCode.CombinationNotFound, "User & Password Combination not found."));
				}
			} 
			catch (NoSuchAlgorithmException | InvalidKeySpecException e) 
			{
				e.printStackTrace();
				json(resp, HttpStatusCodes.STATUS_CODE_SERVER_ERROR, new APIError(APIErrorCode.UnhandledException, e.toString()));
			}
		}

	}
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.sendRedirect("/login.html");
	}
}
