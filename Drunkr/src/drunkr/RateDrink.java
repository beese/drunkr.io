package drunkr;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import drunkr.api.JsonServlet;


@SuppressWarnings("serial")
public class RateDrink extends JsonServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("RateDrink POST");
	}
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("RateDrink GET");
	}
}
