package br.com.svo.util;

import br.com.svo.entities.Identity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Identity identity = null;
        HttpSession session = ((HttpServletRequest)request).getSession(false);

        if (session != null){
            identity = (Identity) session.getAttribute("user");
        }

        if (identity == null) {
            String contextPath = ((HttpServletRequest)request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {}
}
