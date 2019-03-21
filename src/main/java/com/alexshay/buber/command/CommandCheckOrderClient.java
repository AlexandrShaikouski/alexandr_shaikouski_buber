package com.alexshay.buber.command;

import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.util.LocaleBundle;
import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CommandCheckOrderClient implements Command {
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
                if(tripOrder != null && tripOrder.getDriverId() != 0){
                    responseParameters.put("messageInfo", resourceBundle.getString("client.info.acceptorder"));
                    responseParameters.put("driverId", tripOrder.getDriverId());
                    responseParameters.put("statusDriver", resourceBundle.getString("client.page.carfound"));
                    session.setAttribute("statusDriver", resourceBundle.getString("client.page.carfound"));
                    session.setAttribute("tripOrder", tripOrder);
                }else if(tripOrder == null){
                    responseParameters.put("message", resourceBundle.getString("all.error.ordercancel"));
                    responseParameters.put("tripOrder", tripOrder);
                    session.setAttribute("tripOrder", tripOrder);
                }
            }

        }catch (ServiceException e){
            session.setAttribute("tripOrder", null);
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }


}
