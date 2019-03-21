package com.alexshay.buber.command;

import com.alexshay.buber.domain.OrderStatus;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.util.LocaleBundle;
import com.alexshay.buber.util.ResponseContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.ResourceBundle;

public class CommandSignInUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        UserService userService = ServiceFactory.getInstance().getUserService();
        TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
        List<TripOrder> tripOrders;
        TripOrder tripOrder;
        String login = request.getParameter("login");
        String password = request.getParameter("passwordUser");
        User user = User.builder().
                login(login).
                password(password).
                build();
        try {
            user = userService.signIn(user);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            switch (user.getRole()) {
                case CLIENT:
                    tripOrders = tripOrderService.getByClientId(user.getId());
                    tripOrder = tripOrders.stream().
                            filter(s -> s.getStatusOrder() != OrderStatus.COMPLETE).
                            findFirst().orElse(null);
                    session.setAttribute("tripOrder", tripOrder);
                    break;
                case DRIVER:
                    tripOrders = tripOrderService.getByDriverId(user.getId());
                    tripOrder = tripOrders.stream().
                            filter(s -> s.getStatusOrder() != OrderStatus.COMPLETE && s.getStatusOrder() != OrderStatus.WAITING).
                            findFirst().orElse(null);
                    session.setAttribute("tripOrder", tripOrder);
                    break;
            }


            responseContent.setRouter(new Router(servletPath + "?command=main_page", Router.Type.REDIRECT));
            return responseContent;
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("/", Router.Type.FORWARD));
            return responseContent;
        }


    }
}
