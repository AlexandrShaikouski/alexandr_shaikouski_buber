package com.alexshay.buber.filter;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.util.CookieFinder;
import com.alexshay.buber.util.UserJWTKey;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", dispatcherTypes = {
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
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        String jwt = CookieFinder.getValueByName("keyjwt", cookies).orElse("");
        try {
            if (!jwt.equals("")) {
                UserService userService = ServiceFactory.getInstance().getUserService();
                UserJWTKey userJWTKey = UserJWTKey.getInstance();
                String id = userJWTKey.decodeJWT(jwt).getId();
                Integer userId = Integer.parseInt(id);
                User user = userService.getUserById(userId);
                switch (user.getRole()) {
                    case ADMIN:
                        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/admin/admin.jsp").forward(httpServletRequest, httpServletResponse);
                        break;
                    case DRIVER:
                        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/driver/driver.jsp").forward(httpServletRequest, httpServletResponse);
                        break;
                    case CLIENT:
                        httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/client/client.jsp").forward(httpServletRequest, httpServletResponse);
                        break;
                }
            }
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/main.jsp").forward(httpServletRequest, httpServletResponse);
        }
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}