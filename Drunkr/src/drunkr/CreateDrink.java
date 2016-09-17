package drunkr;

import java.io.IOException;
import javax.servlet.http.*;


@SuppressWarnings("serial")
public class CreateDrink extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("CreateDrink POST");
	}
	public void doGet(HttpServletRequest request, HttpServletResponse resp)
			throws IOException {
		resp.getWriter().println("CreateDrink GET");
	}
}
