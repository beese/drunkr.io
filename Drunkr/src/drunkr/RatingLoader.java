package drunkr;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class RatingLoader {
	public static Entity getRatingById(long drinkID) {
		/* Init a datastore session to perform the check */
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Key k = KeyFactory.createKey("Drink", drinkID);
		
		/* Create Filter for Username */
		Filter uf = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, k);
		
		/* Apply Filter to a Query on the Datastore */
		Query q = null;
		Entity d = null;

		/* Form Query for execution */
		q = new Query("Drink").setFilter(uf);
		
		/* Run Query on Datastore */
		PreparedQuery pq = datastore.prepare(q);
		
		/* There should only be one result since Rating IDs are unique */
		d = pq.asSingleEntity();
		
		return d;
	}
}
