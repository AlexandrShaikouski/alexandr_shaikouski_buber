package com.alexshay.buber.command;

import com.alexshay.buber.domain.*;
import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandCheckOrderDriver implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        Map<String, Object> responseParameters = new HashMap<>();
        HttpSession session = request.getSession();
        User driver = (User) session.getAttribute("user");
        String locale = (String) session.getAttribute("locale");
        if(locale != null){
            responseParameters.put("locale",locale);
        }
        try {
            if (driver != null && driver.getRole().equals(Role.DRIVER) && driver.getStatus().equals(UserStatus.ONLINE)){
                TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
                List<TripOrder> tripOrders = tripOrderService.getByStaus(OrderStatus.WAITING.value());
                if(!tripOrders.isEmpty()){
                    responseParameters.put("tripOrders", tripOrders);
                }
            }

        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
