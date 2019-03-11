package com.alexshay.buber.command;

import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.dto.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class CommandCheckOrderClient implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        Map<String, Object> responseParameters = new HashMap<>();
        HttpSession session = request.getSession();
        TripOrder tripOrder = (TripOrder) session.getAttribute("tripOrder");
        User client = (User) session.getAttribute("user");
        String locale = (String) session.getAttribute("locale");

        if(locale != null){
            responseParameters.put("locale",locale);
        }
        try {
            if (client != null && client.getRole().equals(Role.CLIENT) && tripOrder != null){
                TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
                tripOrder = tripOrderService.getById(tripOrder);
                if(tripOrder.getDriverId() != 0){
                    responseParameters.put("messageInfo", "Your order was accepted");
                    responseParameters.put("driverId", tripOrder.getDriverId());
                }
            }

        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
