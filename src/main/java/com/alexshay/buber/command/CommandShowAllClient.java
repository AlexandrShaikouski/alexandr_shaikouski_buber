package com.alexshay.buber.command;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.dto.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CommandShowAllClient implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            List<User> clients = userService.getAllClient();
            responseContent.setRouter(new Router("WEB-INF/jsp/admin/list-clients.jsp", Router.Type.FORWARD));
            request.setAttribute("listUsers", clients);
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("WEB-INF/jsp/admin/list-clients.jsp", Router.Type.FORWARD));
        }finally {
            return responseContent;
        }
    }
}