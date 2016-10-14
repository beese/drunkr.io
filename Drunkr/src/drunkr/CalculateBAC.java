package drunkr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import drunkr.api.APIError;
import drunkr.api.JsonServlet;
import drunkr.api.APIError.APIErrorCode;

public class CalculateBAC extends JsonServlet {
		
	class BAC {
		
		private String bac;
		
		public BAC(double bac) {
			this.bac =  String.format("%.2f", bac);
		}
	}
	
	//if there is invalid input you can send a non-200 status code, and then an object similar to what create drink sends back. 

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		
		String weightStr = req.getParameter("weight");
		String ozStr = req.getParameter("ouncesConsumed");
		String ABVStr = req.getParameter("ABV");
		String genderStr = req.getParameter("gender");
		
		double weight = 0, oz = 0, ABV = 0, gender = 0;
		
		try {
			weight = Double.parseDouble(weightStr);
			oz = Double.parseDouble(ozStr);
			ABV = Double.parseDouble(ABVStr);
			gender = Double.parseDouble(genderStr);
		} catch (NumberFormatException e) {
			jsonServerError(res, new APIError(APIErrorCode.InvalidBACInput, "Input must be a number."));
		}
		if (weight <= 0) {
			jsonServerError(res, new APIError(APIErrorCode.InvalidBACInput, "Input weight must be greater than 0."));
		}
		if (gender != 0 && gender != 1) {
			jsonServerError(res, new APIError(APIErrorCode.InvalidBACInput, "Invalid gender."));
		}
		// gConstant = a gender constant of alcohol distribution (.73 for men and .66 for women)*
		double gConstant = (gender == 0) ? 0.66 : 0.73;
		
		double bac = (oz * 5.14) / (weight * gConstant);
		
		String json = new Gson().toJson(new BAC(bac));
		
		res.setContentType("application/json");
		res.getWriter().write(json);
		jsonOk(res, json);
		
	}
	
}
