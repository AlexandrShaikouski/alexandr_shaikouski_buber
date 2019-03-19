package com.alexshay.buber.command;

import com.alexshay.buber.domain.OrderStatus;
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

public class CommandAcceptOrder implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        ResponseContent responseContent = new ResponseContent();
        String tripOrderIdStr = request.getParameter("trip_order_id");
        String clientIdStr = request.getParameter("client_id");
        HttpSession session = request.getSession();
        Map<String, Object> responseParameters = new HashMap<>();
        User driver = (User) session.getAttribute("user");
        try {
            if (driver != null && tripOrderIdStr != null && clientIdStr != null) {
                TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
                TripOrder tripOrder = TripOrder.builder().
                        id(Integer.parseInt(tripOrderIdStr)).
                        statusOrder(OrderStatus.WAITING).
                        clientId(Integer.parseInt(clientIdStr)).
                        build();
                tripOrder = tripOrderService.getById(tripOrder);
                if(driver.getId() == tripOrder.getDriverId()  || (tripOrder != null && tripOrder.getDriverId() == 0)){
                    tripOrder.setDriverId(driver.getId());
                    tripOrder.setStatusOrder(OrderStatus.PENDING);
                    session.setAttribute("tripOrder", tripOrder);
                    tripOrderService.updateTripOrder(tripOrder);
                    responseParameters.put("tripOrder", tripOrder);
                    responseParameters.put("messageInfo", resourceBundle.getString("all.info.acceptorder"));
                }else if(tripOrder == null){
                    throw new ServiceException(resourceBundle.getString("all.error.ordercancel"));
                }else {
                    throw new ServiceException(resourceBundle.getString("all.error.acceptorder"));
                }


            }
        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
