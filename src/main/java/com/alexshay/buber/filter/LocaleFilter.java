package com.alexshay.buber.filter;
import com.alexshay.buber.util.CookieFinder;
import com.alexshay.buber.util.LocaleBundle;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(urlPatterns = { "/*" })
public class LocaleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        Cookie[] cookies = httpServletRequest.getCookies();
        String locale = CookieFinder.getValueByName("locale",cookies).orElse(null);
        if (locale != null) {
            LocaleBundle.getInstance().setLocaleResourceBundle(locale);
        }
        filterChain.doFilter(servletRequest, servletResponse);



    }

    @Override
    public void destroy() {
    }
}
