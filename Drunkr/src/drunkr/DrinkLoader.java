package drunkr;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

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
import com.google.gson.Gson;

public class DrinkLoader {
	public static Entity getDrinkByName(String name) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey("Name", name);
		Filter filter = new FilterPredicate("Name", FilterOperator.EQUAL, name);

		Query q = null;
		Entity result = null;

		q = new Query("Drink").setFilter(filter);
		PreparedQuery pq = datastore.prepare(q);

		result = pq.asSingleEntity();
		
		return result;
	}
	public static Entity getDrinkById(long drinkID) {
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

	public static void deleteAll() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query("Drink");

		Iterable<Entity> it = datastore.prepare(q).asIterable();

		List<Key> keys = new ArrayList<Key>();
		for (Entity entity : it) {
			keys.add(entity.getKey());
		}

		datastore.delete(keys);
	}

	public static Entity saveDrink(String drinkName, String description, List<Ingredient> ingredients, 
			int tasteRating, double averageRating, int totalRatings)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		Entity d = new Entity("Drink");
		if (drinkName.trim().isEmpty() == true || ingredients.size() == 0) {
			return null;
		}
		double volume = 0.0;
		double alcoholVolume = 0.0;
		//Calculate the alcohol content of the drink
		for (int i = 0; i < ingredients.size(); i++) {
			volume += ingredients.get(i).amount;
			alcoholVolume += ingredients.get(i).amount * ingredients.get(i).abv;
		}
		double alcoholPercentage = alcoholVolume / volume;
		alcoholPercentage = Math.round(alcoholPercentage * 100.0) / 100.0;
		d.setProperty("Name", drinkName);
		d.setProperty("Description", description);
		d.setProperty("TasteRating", tasteRating);
		d.setProperty("averageRating", 0.0);
		d.setProperty("totalRatings", 0);
		Gson g = new Gson();
		d.setProperty("Ingredients", g.toJson(ingredients));
		d.setProperty("AlcoholContent", alcoholPercentage);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(d);

		return d;
	}
}
