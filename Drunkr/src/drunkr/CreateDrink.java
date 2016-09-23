package drunkr;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.HttpStatusCodes;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;

import drunkr.api.APIError;
import drunkr.api.JsonServlet;
import drunkr.api.APIError.APIErrorCode;


/**
 * This class processes drink creation POST requests from the create drink page.
 * @author Garrett Lewis
 */


@SuppressWarnings("serial")
public class CreateDrink extends JsonServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		String drinkName = request.getParameter("name");
		if (drinkName.trim() == "") {
			
		}
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
			System.out.println(e.getMessage());
		}
		for (int i = 0; i < arr.length(); i++) {
			Ingredient ing; 
			try {
				obj = arr.getJSONObject(i);
				ing = new Ingredient(obj.getString("name"), obj.getDouble("amount"), obj.getString("unit"), obj.getDouble("abv"));
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
		try {
			d = DrinkLoader.saveDrink(drinkName, description, ingredients, tasteRating, tasteRating, 1);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			json(resp, HttpStatusCodes.STATUS_CODE_SERVER_ERROR, new APIError(APIErrorCode.UnhandledException, e.toString()));
			return;
		}
		
		jsonOk(resp, d);
	}
	
}
