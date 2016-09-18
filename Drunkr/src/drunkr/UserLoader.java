package drunkr;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class UserLoader {
	public static Entity getUserByUsername(String username) {
		/* Init a datastore session to perform the check */
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		/* Create Filter for Username */
		Filter uf = new FilterPredicate("Username", FilterOperator.EQUAL, username);
		
		/* Apply Filter to a Query on the Datastore */
		Query q = null;
		Entity u = null;

		/* Form Query for execution */
		q = new Query("User").setFilter(uf);
		
		/* Run Query on Datastore */
		PreparedQuery pq = datastore.prepare(q);
		
		/* There should only be one result since usernames are unique */
		u = pq.asSingleEntity();
		
		return u;
	}
}
