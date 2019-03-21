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
        HttpSession session = request.getSession();
        Map<String, Object> responseParameters = new HashMap<>();
        User driver = (User) session.getAttribute("user");
        TripOrder tripOrder = (TripOrder) session.getAttribute("tripOrder");
        try {
            if (tripOrder == null) {
                if (driver != null && tripOrderIdStr != null) {
                    TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
                    tripOrder = TripOrder.builder().
                            id(Integer.parseInt(tripOrderIdStr)).
                            build();
                    tripOrder = tripOrderService.getById(tripOrder);
                    if (tripOrder != null) {
                        tripOrder.setDriverId(driver.getId());
                        tripOrder.setStatusOrder(OrderStatus.PENDING);
                        session.setAttribute("tripOrder", tripOrder);
                        tripOrderService.updateTripOrder(tripOrder);
                        responseParameters.put("tripOrder", tripOrder);
                        responseParameters.put("messageInfo", resourceBundle.getString("all.info.acceptorder"));
                    } else {
                        throw new ServiceException(resourceBundle.getString("all.error.ordercancel"));
                    }

                }
            }else{
                responseParameters.put("tripOrder", tripOrder);
                responseParameters.put("messageInfo", resourceBundle.getString("all.info.acceptorder"));
            }
        } catch (ServiceException e) {
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
