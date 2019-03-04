package com.alexshay.buber.controller.filter;

import com.alexshay.buber.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        HttpSession session = httpServletRequest.getSession();
        User user = (User)session.getAttribute("user");
        switch (user.getRole()){
            case ADMIN:
                httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp").forward(request,response);
                break;
            case CLIENT:
                httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/client/driver.jsp").forward(request,response);
                break;
            case DRIVER:
                httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/driver/driver.jsp").forward(request,response);
                break;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}