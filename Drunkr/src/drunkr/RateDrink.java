package drunkr;

import java.io.IOException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class RateDrink extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("RateDrink POST");
	}
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("RateDrink GET");
	}
}
