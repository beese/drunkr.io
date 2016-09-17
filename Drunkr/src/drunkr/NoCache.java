package drunkr;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
 
public class NoCache implements Filter {
 
    @Override
    public void init(FilterConfig config) throws ServletException {
        // NOOP
    }
 
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP
                                                                                    // 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        chain.doFilter(req, res);
    }
 
    @Override
    public void destroy() {
        // NOOP
    }
}