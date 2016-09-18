package drunkr;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.http.HttpStatusCodes;
import com.google.gson.Gson;

import drunkr.api.APIError;
import drunkr.api.APIError.APIErrorCode;

public class Auth implements Filter {

	private ServletContext context;
	
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String uri = req.getRequestURI();
		this.context.log("Requested Resource::"+uri);
		
		HttpSession session = req.getSession(false);
		
		if(session == null && !(uri.endsWith("html") || uri.endsWith("LoginServlet"))){
			this.context.log("Unauthorized access request");

			resp.setContentType("application/json");

			String json = (new Gson()).toJson(new APIError(APIErrorCode.CombinationNotFound, "User & Password Combination not found."));
			
			resp.setStatus(HttpStatusCodes.STATUS_CODE_FORBIDDEN);
			resp.getWriter().println(json);
		}
		else
		{
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}
	}

	

	public void destroy() {
		//close any resources here
	}

}