package drunkr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import drunkr.api.JsonServlet;

/**
 * This class processes rating POST requests from ratedrink.html.
 * @author Garrett Lewis
 */

@SuppressWarnings("serial")
public class RateDrink extends JsonServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		int rating = 0;
		int drinkID = 0;
		
		try {
			rating = Integer.parseInt(request.getParameter("rating"));
			drinkID = Integer.parseInt(request.getParameter("drinkID"));
		}
		catch (Exception e) {
			
		}
		//Get the current rating stats of the drink
		Entity r = RatingLoader.getRatingById(drinkID);
		
		double averageRating = (double)r.getProperty("averageRating");
		int totalRatings = (int)r.getProperty("totalRatings");
		int ratingSum = (int)(averageRating * totalRatings);
		//Normalize the alcoholPercentage to be out of 5
		double alcoholRating= (((double)r.getProperty("alcoholContent") * 100) / 10) / 2;
		
		totalRatings++;
		ratingSum += rating;
		//Calculate new average rating
		averageRating = ratingSum / totalRatings;
		
		//Generate average between alcoholRating and the averageRating
		double gradePoint = (alcoholRating + averageRating) / 5;
		
		String grade = "";
		if (gradePoint <= 5.0 && gradePoint > 3.0) {
			grade = "A";
		}
		else if (gradePoint <= 3.0 && gradePoint > 2.0) {
			grade = "B";
		}
		else if (gradePoint <= 2.0 && gradePoint > 1.5) {
			grade = "C";
		}
		else if (gradePoint <= 1.5 && gradePoint > 1.0) {
			grade = "D";
		}
		else if (gradePoint <= 1.0) {
			grade = "F";
		}
		
		r.setProperty("averageRating", averageRating);
		r.setProperty("totalRatings", totalRatings);
		r.setProperty("grade", grade);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(r);
		
		
		
		jsonOk(resp, r);
		resp.getWriter().println("RateDrink POST");
		
		
		
	}
	
	
}
