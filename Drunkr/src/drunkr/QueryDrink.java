package drunkr;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import drunkr.api.JsonServlet;


@SuppressWarnings("serial")
public class QueryDrink extends JsonServlet {
	
	// Search/filter drinks
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
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
		
		// Create Filters for options 
		
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
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Query q = new Query("Drink");
		
		String id = request.getParameter("id");
		
		/* If a specific drink is requested, a field 
		 * for id will be provided. This should be the
		 * only item fetched and returned.
		 */
		if (id != null) {
			long idLong = Long.valueOf(id);
			
			Key k = KeyFactory.createKey("Drink", idLong);
			
			Filter uf = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, k);
			
			q.setFilter(uf);
			
			PreparedQuery pq = datastore.prepare(q);
			
			Entity u = pq.asSingleEntity();
			
			String json = new Gson().toJson(u);
			
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			return;
		}
		
		List<Entity> drinks = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());
		
		String query = request.getParameter("query");
		
		/* The query parameter will be null when a "view all" request is made.
		 * OR if the query is the empty string, return the full list
		 */
		// defect: empty search doesn't return full list
		if(query == null)
		{
			String json = new Gson().toJson(drinks);
			
			resp.setContentType("application/json");
			resp.getWriter().write(json);
			
			return;
		}
		
		/* Since we have a query, we are going to refine our list 
		 * and weight by the number of matching components.
		 */
		
		/* Remove commas */
		query = query.replaceAll(",", "");
		
		/* Get String array of query terms, split by white space */
		String[] terms = query.split(" ");
		
		/* Remove stop words from array */
		ArrayList<String> queryTerms = new ArrayList<String>();
		for(String term : terms)
		{
			// defect: remove check white space cleanup
			// term = term.replaceAll(" " , "");
			if(!queryTerms.contains(term) && (!term.equalsIgnoreCase("and") && !term.equalsIgnoreCase("the") 
					&& !term.equalsIgnoreCase("in")))
				queryTerms.add(term);
		}
		/* Prepare ArrayList of Result objects <weight, Entity> */
		List<Result> results = new ArrayList<Result>();
		
		for(Entity e : drinks)
		{
			/* Create a new Result for this Entity */
			Result r = new Result(e);
			
			if(results.contains(r))
			{
				/* Get the existing version of this Entity */
				r = results.get(results.indexOf(r));
				results.remove(r);
			}
			for(String str : queryTerms)
			{
				/* Get properties for this entity */
				
				/*
				String name = (String) e.getProperty("Name");
				String desc = (String) e.getProperty("Description");
				double tasteRating = Double.parseDouble((String) e.getProperty("TasteRating"));
				double avgRating = Double.parseDouble((String) e.getProperty("averageRating"));
				double totalRatings = Double.parseDouble((String) e.getProperty("totalRatings"));
				
				String[] ingredients = new Gson().fromJson((String) e.getProperty("Ingredients"), new TypeToken<List<String>>(){}.getType());
				double alcohol = Double.parseDouble((String) e.getProperty("AlcoholContent"));
				
				*/
				
				/* Get Map of Properties */
				Map<String, Object> name = e.getProperties();
				
				for(Map.Entry<String, Object> entry : name.entrySet())
				{
					/* Cast Value as a String */
					String value = "";

					value = entry.getValue().toString();
					
					/* If the property contains the query, increment the weight */
					// defect: do not ignore casing of str
					if(value != null && value.contains(str))
						r.incrementWeight();
				}
			}	
			
			/* Add Result to the results list */
			results.add(r);
		
		}
		
		/* Sort results by weight */
		Collections.sort(results, new Comparator<Result>() {
			@Override
		    public int compare(Result r1, Result r2)
		    {
				/* Return -1, 0, -1 depending on weight */
				// defect: weight will return inverted order (should be r1 - r2)
		    	return r2.getWeight() - r1.getWeight();
		    }
		});
		
		/* Build Entity list to return */
		drinks = new ArrayList<Entity>();
		
		/* Iterate over Result list */
		
		int i = 0;
		for(Result r : results)
		{
			/* Produce 50 results */
			// defect: removed check for weight > 0
			if(i < 50)
				drinks.add(r.getEntity());
			i++;
		}
		
		/* Convert List to JSON and return */
		String json = new Gson().toJson(drinks);
		
		resp.setContentType("application/json");
		resp.getWriter().write(json);
	}	
}
