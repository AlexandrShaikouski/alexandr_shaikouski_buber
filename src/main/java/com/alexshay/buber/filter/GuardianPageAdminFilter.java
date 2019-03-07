package com.alexshay.buber.filter;

import com.alexshay.buber.domain.Role;
import com.alexshay.buber.util.CookieFinder;
import com.alexshay.buber.util.UserJWTKey;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE
}, urlPatterns = "/WEB-INF/jsp/admin/*",
        initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class GuardianPageAdminFilter implements Filter {
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter("INDEX_PATH");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie [] cookies = httpServletRequest.getCookies();
        String jwt = CookieFinder.getValueByName("keyjwt", cookies).orElse("");
        if(!jwt.equals("")){
            UserJWTKey userJWTKey = UserJWTKey.getInstance();
            String role = userJWTKey.decodeJWT(jwt).getIssuer();
            if (!role.equals(Role.ADMIN.value())) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + indexPath);
            }
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
