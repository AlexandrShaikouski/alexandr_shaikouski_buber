package com.alexshay.buber.filter;

import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.util.LocaleBundle;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ResourceBundle;

@WebFilter(dispatcherTypes = {
        DispatcherType.REQUEST,
        DispatcherType.FORWARD,
        DispatcherType.INCLUDE
}, urlPatterns = "/WEB-INF/jsp/client/client.jsp")
public class ActualDataClientFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        HttpSession session = httpServletRequest.getSession();
        TripOrder tripOrder = (TripOrder) session.getAttribute("tripOrder");

        if(tripOrder != null){
            switch (tripOrder.getStatusOrder()){
                case WAITING:
                    session.setAttribute("statusDriver", resourceBundle.getString("client.page.carnotfound"));
                    break;
                case PENDING:
                    session.setAttribute("statusDriver", resourceBundle.getString("client.page.carfound"));
                    break;
                case IN_PROGRESS:
                    session.setAttribute("statusDriver", resourceBundle.getString("client.page.taxiarrived"));
                    break;
            }
        }


        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
