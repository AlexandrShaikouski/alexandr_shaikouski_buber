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
import java.util.Arrays;
import java.util.List;

public class CommandInfoUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        UserService userService = ServiceFactory.getInstance().getUserService();
        BonusService bonusService = ServiceFactory.getInstance().getBonusService();
        TripOrderService tripOrderService = ServiceFactory.getInstance().getTripOrderService();
        String role = request.getParameter("role");
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("role",role);

        try {
            List<TripOrder> tripOrders = Arrays.asList();
            User user = userService.getUserById(id);
            if (role.equals("client")) {
                List<Bonus> bonuses = bonusService.getAll();
                tripOrders = tripOrderService.getByClientId(user.getId());
                request.setAttribute("listBonuses", bonuses);
            } else if(role.equals("driver")){
                tripOrders = tripOrderService.getByDriverId(user.getId());
            }


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
