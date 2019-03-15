package com.alexshay.buber.command;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class CommandDeleteBan implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        UserService userService = ServiceFactory.getInstance().getUserService();
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            User user = userService.getUserById(id);
            if(user.getStatusBan() == null){
                responseContent.setRouter(new Router("/?command=info_user", Router.Type.FORWARD));
                return responseContent;
            }else{
                user.setStatusBan(null);
                userService.updateUser(user);
                responseContent.setRouter(new Router(servletPath + "?command=success_page", Router.Type.REDIRECT));
            }

        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("/?command=info_user", Router.Type.FORWARD));
        }finally {
            return responseContent;
        }



    }
}
