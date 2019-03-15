package com.alexshay.buber.command;

import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class CommandDeleteBonus implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        UserService userService = ServiceFactory.getInstance().getUserService();
        int id = Integer.parseInt(request.getParameter("id"));
        int bonusId = Integer.parseInt(request.getParameter("bonus_id_delete"));
        try {
            userService.deleteBonus(id,bonusId);

            responseContent.setRouter(new Router(servletPath + "?command=success_page", Router.Type.REDIRECT));
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("/?command=info_user", Router.Type.FORWARD));
        }finally {
            return responseContent;
        }
    }
}
