package com.alexshay.buber.command;

import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

public class CommandShowAllOrder implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
        User user = (User) request.getSession().getAttribute("user");
        List<TripOrder> listOrders;
        try {
            switch (user.getRole()) {
                case ADMIN:
                    listOrders = tripOrderService.getAll();
                    responseContent.setRouter(new Router("WEB-INF/jsp/admin/list-orders.jsp", Router.Type.FORWARD));
                    request.setAttribute("listOrders", listOrders);
                    break;
                case DRIVER:
                    listOrders = tripOrderService.getByDriverId(user.getId());
                    responseContent.setRouter(new Router("WEB-INF/jsp/driver/list-orders.jsp", Router.Type.FORWARD));
                    request.setAttribute("listOrders", listOrders);
                    break;
                case CLIENT:
                    listOrders = tripOrderService.getByClientId(user.getId());
                    responseContent.setRouter(new Router("WEB-INF/jsp/client/list-orders.jsp", Router.Type.FORWARD));
                    request.setAttribute("listOrders", listOrders);
                    break;
            }

        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("WEB-INF/jsp/admin/list-orders.jsp", Router.Type.FORWARD));
        }
        return responseContent;

    }
}
