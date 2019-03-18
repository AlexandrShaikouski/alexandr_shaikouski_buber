package com.alexshay.buber.command;

import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.util.ResponseContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class CommandCancelOrder implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
        ResponseContent responseContent = new ResponseContent();
        Map<String, Object> responseParameters = new HashMap<>();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        TripOrder tripOrder = (TripOrder) session.getAttribute("tripOrder");
        session.setAttribute("tripOrder", null);

        try {
            LOGGER.info(user.getLogin() + " cancel order.");
            tripOrderService.deleteTripOrder(tripOrder);
            responseParameters.put("cancel", "cancel");

        } catch (ServiceException e) {
            responseParameters.put("message", e.getMessage());
        }
        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
