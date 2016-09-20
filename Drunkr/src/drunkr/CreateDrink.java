package drunkr;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;

import drunkr.api.JsonServlet;


/**
 * This class processes drink creation POST requests from the create drink page.
 * @author Garrett Lewis
 */


@SuppressWarnings("serial")
public class CreateDrink extends JsonServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		String drinkName = request.getParameter("name");
		String description = request.getParameter("description");
		int tasteRating = 0;
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		String ingredientJson = request.getParameter("ingredients");
		JSONObject obj = null;
		JSONArray arr = null;
		try {
			obj = new JSONObject(ingredientJson);
			arr = obj.getJSONArray("ingredients");
		}
		catch (JSONException e) {
			//JSON parsing error
		}
		for (int i = 0; i < arr.length(); i++) {
			Ingredient ing; 
			try {
				obj = arr.getJSONObject(i);
				ing = new Ingredient(obj.getString("name"), obj.getInt("amount"), obj.getString("unit"), obj.getDouble("abv"));
				ingredients.add(ing);
			}
			catch (JSONException e) {
				continue;
			}
			
			
			
			
		}
		
		double volume = 0.0;
		double alcoholVolume = 0.0;
		//Calculate the alcohol content of the drink
		for (int i = 0; i < ingredients.size(); i++) {
			volume += ingredients.get(i).amount;
			alcoholVolume += ingredients.get(i).amount * ingredients.get(i).abv;
		}
		double alcoholPercentage = alcoholVolume / volume;
		
		try {
			tasteRating = Integer.parseInt(request.getParameter("tasteRating"));
		}
		catch (Exception e) {
			//Parse error
		}
		Gson g = new Gson();
		Entity d = new Entity("Drink");
		d.setProperty("Name", drinkName);
		d.setProperty("Decripiton", description);
		d.setProperty("TasteRating", tasteRating);
		d.setProperty("averageRating", 0.0);
		d.setProperty("totalRatings", 0);
		d.setProperty("Ingredients", g.toJson(ingredients));
		d.setProperty("AlcoholContent", alcoholPercentage);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(d);
		jsonOk(resp, d);
	}
	
}
