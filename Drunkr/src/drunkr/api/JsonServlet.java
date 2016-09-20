package drunkr.api;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public abstract class JsonServlet extends HttpServlet {
	private static Gson gson = new Gson();
	
	protected void json(HttpServletResponse resp, int statusCode, Object response) throws IOException {
		resp.setContentType("application/json");

		String json = gson.toJson(response);
		resp.setStatus(statusCode);
		resp.getWriter().println(json);
	}
	
	protected void jsonForbidden(HttpServletResponse resp, Object response) throws IOException {
		json(resp, HttpStatusCodes.STATUS_CODE_FORBIDDEN, response);
	}
	
	protected void jsonOk(HttpServletResponse resp, Object response) throws IOException {
		json(resp, HttpStatusCodes.STATUS_CODE_OK, response);
	}
}
