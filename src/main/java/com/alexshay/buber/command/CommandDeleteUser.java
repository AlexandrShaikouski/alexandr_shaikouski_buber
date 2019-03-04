package com.alexshay.buber.command;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.dto.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class CommandDeleteUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        UserService userService = ServiceFactory.getInstance().getUserService();
        int id = Integer.parseInt(request.getParameter("id"));
        String role = request.getParameter("role");
        User user = User.builder().id(id).build();
        try {
            userService.deleteUser(user);
            if(role.equals("client")){
                responseContent.setRouter(new Router(servletPath + "?command=list_clients", Router.Type.REDIRECT));
            }else {
                responseContent.setRouter(new Router(servletPath + "?command=list_drivers", Router.Type.REDIRECT));
            }
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            if(role.equals("client")){
                responseContent.setRouter(new Router(servletPath + "?command=list_clients", Router.Type.FORWARD));
            }else {
                responseContent.setRouter(new Router(servletPath + "?command=list_drivers", Router.Type.FORWARD));
            }
        }finally {
            return responseContent;
        }
    }
}
