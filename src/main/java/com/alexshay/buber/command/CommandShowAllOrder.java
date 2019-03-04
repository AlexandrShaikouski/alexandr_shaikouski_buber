package com.alexshay.buber.command;

import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.dto.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CommandShowAllOrder implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
        try {
            List<TripOrder> listOrders = tripOrderService.getAll();
            responseContent.setRouter(new Router("WEB-INF/jsp/admin/list-orders.jsp", Router.Type.FORWARD));
            request.setAttribute("listOrders", listOrders);
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("WEB-INF/jsp/admin/list-orders.jsp", Router.Type.FORWARD));
        }finally {
            return responseContent;
        }
    }
}
