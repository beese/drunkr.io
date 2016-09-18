package drunkr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.Entity;

import drunkr.api.APIError;
import drunkr.api.JsonServlet;
import drunkr.api.APIError.APIErrorCode;

@SuppressWarnings("serial")
public class MeServlet extends JsonServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		HttpSession session = request.getSession();
		
		try {
			String username = session.getAttribute("User").toString();
			
			Entity u = UserLoader.getUserByUsername(username);
			
			jsonOk(resp, u);
		}
		catch(NullPointerException e) {
			// if getAttribute returns null
			jsonForbidden(resp, new APIError(APIErrorCode.CombinationNotFound, "User & Password Combination not found."));
		}
	}
}
