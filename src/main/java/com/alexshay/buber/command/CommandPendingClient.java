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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CommandPendingClient implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        ResponseContent responseContent = new ResponseContent();
        HttpSession session = request.getSession();
        Map<String, Object> responseParameters = new HashMap<>();
        TripOrder tripOrder = (TripOrder) session.getAttribute("tripOrder");
        try {
            if(tripOrder != null){
                TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
                UserService userService = ServiceFactory.getInstance().getUserService();
                tripOrder.setStatusOrder(OrderStatus.IN_PROGRESS);
                tripOrderService.updateTripOrder(tripOrder);
                User client = userService.getUserById(tripOrder.getClientId());
                responseParameters.put("tripOrder", tripOrder);
                responseParameters.put("clientName", client.getFirstName());
                responseParameters.put("clientPhone", client.getPhone());
            }else {
                throw new ServiceException(resourceBundle.getString("all.error.wrongdata"));
            }
        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
