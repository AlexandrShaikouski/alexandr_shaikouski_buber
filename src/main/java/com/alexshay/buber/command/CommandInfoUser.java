package com.alexshay.buber.command;

import com.alexshay.buber.domain.Bonus;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.dto.ResponseContent;
import com.alexshay.buber.service.BonusService;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandInfoUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        UserService userService = ServiceFactory.getInstance().getUserService();
        BonusService bonusService = ServiceFactory.getInstance().getBonusService();
        TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
        String role = request.getParameter("role");
        int id = Integer.parseInt(request.getParameter("id"));
        Map<String, String> parameter = new HashMap<>();
        request.setAttribute("role",role);

        try {
            User user = userService.getUserById(id);
            if (role.equals("client")) {
                parameter.put("client_id", user.getId().toString());
                List<Bonus> bonuses = bonusService.getAll();
                request.setAttribute("listBonuses", bonuses);
            } else {
                parameter.put("driver_id", user.getId().toString());
            }
            List<TripOrder> tripOrders = tripOrderService.getByParameter(parameter);
            request.setAttribute("user", user);
            request.setAttribute("listOrders", tripOrders);
            responseContent.setRouter(new Router("/WEB-INF/jsp/admin/info-user.jsp", Router.Type.FORWARD));

        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("/", Router.Type.FORWARD));
        } finally {
            return responseContent;
        }
    }
}
