package com.alexshay.buber.command;

import com.alexshay.buber.domain.*;
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

public class CommandCompleteOrderClient implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        UserService userService = ServiceFactory.getInstance().getUserService();
        ResponseContent responseContent = new ResponseContent();
        Map<String, Object> responseParameters = new HashMap<>();
        HttpSession session = request.getSession();
        TripOrder tripOrder = (TripOrder) session.getAttribute("tripOrder");
        User client = (User) session.getAttribute("user");

        try {
            if(tripOrder != null) {
                TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
                TripOrder tripOrderValid = tripOrderService.getById(tripOrder);
                if (tripOrderValid == null) {
                    session.setAttribute("tripOrder", null);
                    session.setAttribute("user", client);
                    responseParameters.put("message", resourceBundle.getString("client.page.canceldriver"));
                    responseContent.setResponseParameters(responseParameters);
                    return responseContent;
                }

                if (client != null && client.getRole().equals(Role.CLIENT)) {
                    if (tripOrderValid.getStatusOrder() == OrderStatus.COMPLETE) {
                        client = userService.getUserById(client.getId());
                        responseParameters.put("messageInfo", resourceBundle.getString("client.page.finishtrip"));
                        responseParameters.put("statusOrder", OrderStatus.COMPLETE.value().toUpperCase());
                        session.setAttribute("tripOrder", null);
                        session.setAttribute("user", client);
                    }
                }
            }

        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
