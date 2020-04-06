package edu.social.openid.oauth.google.csrf;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsrfTokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String csrfHeader = request.getHeader(CsrfTokenUtil.getCsrfTokenHeaderName());
        String csrfQuqeryParam = request.getParameter(CsrfTokenUtil.getCsrfTokenQueryParam());
        if (csrfHeader == null || csrfQuqeryParam == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CSRF token missing!");
            return;
        }
        if (!csrfHeader.equals(csrfQuqeryParam)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "CSRF token matching error!");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
