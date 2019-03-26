package com.alexshay.buber.command;

import com.alexshay.buber.domain.OrderStatus;
import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
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

public class CommandPendingDriver implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        ResponseContent responseContent = new ResponseContent();
        Map<String, Object> responseParameters = new HashMap<>();
        HttpSession session = request.getSession();
        TripOrder tripOrder = (TripOrder) session.getAttribute("tripOrder");
        User client = (User) session.getAttribute("user");

        try {
            if (client != null && client.getRole().equals(Role.CLIENT) && tripOrder != null){
                TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
                tripOrder = tripOrderService.getById(tripOrder);
                if(tripOrder.getStatusOrder() == OrderStatus.IN_PROGRESS){
                    responseParameters.put("driverId", tripOrder.getDriverId());
                    responseParameters.put("statusOrder", tripOrder.getStatusOrder().value().toUpperCase());
                    responseParameters.put("statusDriver", resourceBundle.getString("client.page.taxiarrived"));
                    session.setAttribute("statusDriver", resourceBundle.getString("client.page.taxiarrived"));
                    session.setAttribute("tripOrder", tripOrder);
                }
            }

        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
