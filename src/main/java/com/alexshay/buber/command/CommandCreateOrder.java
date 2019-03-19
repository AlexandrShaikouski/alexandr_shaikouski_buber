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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CommandCreateOrder implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        ResponseContent responseContent = new ResponseContent();
        TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        float price = Float.parseFloat(request.getParameter("price"));
        String bonusIdStr = request.getParameter("bonus_id");
        HttpSession session = request.getSession();
        User client = (User) session.getAttribute("user");
        int clientId = client.getId();



        Map<String,Object> responseParameters = new HashMap<>();
        TripOrder tripOrder = TripOrder.builder().
                from(from).
                to(to).
                dateCreate(new Date()).
                price(price).
                clientId(clientId).
                statusOrder(OrderStatus.WAITING).
                build();
        if(bonusIdStr != null && !bonusIdStr.equals("0")){
            tripOrder.setBonusId(Integer.parseInt(bonusIdStr));
        }
        try {
            tripOrder = tripOrderService.createTripOrder(tripOrder);

            session.setAttribute("tripOrder",tripOrder);
            session.setAttribute("statusDriver", resourceBundle.getString("client.page.carnotfound"));
            responseParameters.put("messageInfo",resourceBundle.getString("client.info.successorder"));
        } catch (ServiceException e) {
            responseParameters.put("message",e.getMessage());
        }

        responseContent.setResponseParameters(responseParameters);
        return responseContent;
    }
}
