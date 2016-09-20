package drunkr;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import drunkr.api.JsonServlet;


@SuppressWarnings("serial")
public class QueryDrink extends JsonServlet {
	
	// Search/filter drinks
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("QueryDrink POST");
		
		// Get POST body as JSON
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = req.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } finally {
	        reader.close();
	    }

	    // Convert JSON to QueryOptions object
		QueryOptions options = new Gson().fromJson(sb.toString(), QueryOptions.class);
	
		// Format and run query
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("Drink");
		
		// TODO: Add search terms :(
		
		// Add filter options
		// What properties can we filter on?? TBD
		
		// filter example
		// Filter propertyFilter =
	    //              new FilterPredicate("height", FilterOperator.GREATER_THAN_OR_EQUAL, minHeight);
	    // Query q = new Query("Person").setFilter(propertyFilter);
		
		// Set sort order
		String[] sortOptions = options.getSortOptions();

		if(sortOptions != null && sortOptions.length == 2) {
			if(sortOptions[1].equalsIgnoreCase("ascending")) {
				q.addSort(sortOptions[0], SortDirection.ASCENDING);
			} else if(sortOptions[1].equalsIgnoreCase("descending")) {
				q.addSort(sortOptions[0], SortDirection.DESCENDING);
			}
		}	
		
		PreparedQuery pq = datastore.prepare(q);
		
		List<Entity> drinks = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		String json = new Gson().toJson(drinks);
		
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		
	}
	
	// View all drinks
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("QueryDrink GET");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("Drink");
		PreparedQuery pq = datastore.prepare(q);
		
		List<Entity> drinks = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		String json = new Gson().toJson(drinks);
		
		resp.setContentType("application/json");
		resp.getWriter().write(json);
		
	}
}
