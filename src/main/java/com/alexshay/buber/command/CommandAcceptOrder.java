package com.alexshay.buber.command;

import com.alexshay.buber.domain.OrderStatus;
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

public class CommandAcceptOrder implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        String tripOrderIdStr = request.getParameter("trip_order_id");
        String clientIdStr = request.getParameter("client_id");
        HttpSession session = request.getSession();
        Map<String, Object> responseParameters = new HashMap<>();
        User user = (User) session.getAttribute("user");
        try {
            if (user != null && tripOrderIdStr != null && clientIdStr != null) {
                TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
                TripOrder tripOrder = TripOrder.builder().
                        id(Integer.parseInt(tripOrderIdStr)).
                        statusOrder(OrderStatus.WAITING).
                        clientId(Integer.parseInt(clientIdStr)).
                        build();
                tripOrder = tripOrderService.getById(tripOrder);
                if(tripOrder.getDriverId() == 0){
                    tripOrder.setDriverId(user.getId());
                    tripOrder.setStatusOrder(OrderStatus.IN_PROGRESS);
                    tripOrderService.updateTripOrder(tripOrder);
                    responseParameters.put("tripOrder", tripOrder);
                    responseParameters.put("messageInfo", "You accepted order. Please go to client.");
                }else{
                    throw new ServiceException("The order already accepted.");
                }

            }
        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
