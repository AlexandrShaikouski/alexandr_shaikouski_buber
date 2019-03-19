package com.alexshay.buber.command;

import com.alexshay.buber.domain.OrderStatus;
import com.alexshay.buber.domain.TripOrder;
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

public class CommandCompleteOrderDriver implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
        ResponseContent responseContent = new ResponseContent();
        UserService userService = ServiceFactory.getInstance().getUserService();
        Map<String, Object> responseParameters = new HashMap<>();
        HttpSession session = request.getSession();
        TripOrder tripOrder = (TripOrder) session.getAttribute("tripOrder");
        session.setAttribute("tripOrder",null);

        try {
            if(tripOrder != null){

                tripOrder.setStatusOrder(OrderStatus.COMPLETE);
                tripOrderService.updateTripOrder(tripOrder);
                if(tripOrder.getBonusId() != 0){
                    userService.deleteBonus(tripOrder.getClientId(),tripOrder.getBonusId());
                }
                responseParameters.put("tripOrder", tripOrder);
            }else {
                LOGGER.info("Not find trip in user");
                throw new ServiceException(resourceBundle.getString("all.error.wrongdata"));
            }
        }catch (ServiceException e){
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
