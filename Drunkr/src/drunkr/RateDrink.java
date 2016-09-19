package drunkr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;

import drunkr.api.JsonServlet;


@SuppressWarnings("serial")
public class RateDrink extends JsonServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		int rating = 0;
		try {
			rating = Integer.parseInt(request.getParameter("rating"));
		}
		catch (Exception e) {
			
		}
		//Entity r = RatingLoader.getRatingById(ratingID)
		
		
		
		resp.getWriter().println("RateDrink POST");
		
		
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		
		
	}
	
}
