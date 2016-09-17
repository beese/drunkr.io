package drunkr;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class Login extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		
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
			resp.getWriter().println("Too many results found.");

		}
		catch(Exception e)
		{
			/* No results, login information is junk */
			// Fail handling
			resp.getWriter().println("User & Password Combination not found.");

		}
		
		if(u == null)
			resp.getWriter().println("User & Password combination not found.");
		else
		{
			/* Now test the Password for this User entity */
			try 
			{
				if(Password.validate(pw, (String)u.getProperty("Password")))
				{
					HttpSession session = request.getSession();
					session.setAttribute("User", u.getProperty("Username"));
					//setting session to expiry in 30 mins
					session.setMaxInactiveInterval(30*60);
					Cookie userName = new Cookie("User", (String) u.getProperty("Username"));
					
					userName.setMaxAge(30*60);
					resp.addCookie(userName);
					resp.sendRedirect("app/index.jsp");
				}
				else
				{
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
					PrintWriter out= resp.getWriter();
					out.println("<font color=red>Either user name or password is wrong.</font>");
					try {
						rd.include(request, resp);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} 
			catch (NoSuchAlgorithmException | InvalidKeySpecException e) 
			{
				e.printStackTrace();
				resp.getWriter().println("An error has occured.");
			}
		}

	}
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.sendRedirect("/login.html");
	}
}
