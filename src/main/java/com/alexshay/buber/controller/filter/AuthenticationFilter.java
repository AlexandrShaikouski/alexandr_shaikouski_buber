package com.alexshay.buber.controller.filter;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.util.CookieFinder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter",dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE
        }, urlPatterns = "/WEB-INF/jsp/main.jsp")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;

        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        String login = CookieFinder.getValueByName("name",cookies).orElse("");
        String password = CookieFinder.getValueByName("claps",cookies).orElse("");
        if(!login.equals("") && !password.equals("")){
            httpServletRequest.setAttribute("login", login);
            httpServletRequest.setAttribute("passwordUser", password);
            httpServletRequest.getRequestDispatcher("/buber?command=sign_in&login=" + login + "&passwordUser=" + password)
                    .forward(httpServletRequest,httpServletResponse);
        }


        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}