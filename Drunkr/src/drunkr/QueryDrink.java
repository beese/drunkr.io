package drunkr;

import java.io.IOException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class QueryDrink extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("QueryDrink POST");
	}
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("QueryDrink GET");
	}
}
