package com.alexshay.buber.command;

import com.alexshay.buber.domain.*;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.util.LocaleBundle;
import com.alexshay.buber.util.ResponseContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CommandCompleteOrderClient implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
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
                if(tripOrder.getStatusOrder() == OrderStatus.COMPLETE){
                    responseParameters.put("messageInfo", resourceBundle.getString("client.page.finishtrip"));
                    responseParameters.put("statusOrder", OrderStatus.COMPLETE.value().toUpperCase());
                    session.setAttribute("tripOrder", null);
                }
            }

        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
