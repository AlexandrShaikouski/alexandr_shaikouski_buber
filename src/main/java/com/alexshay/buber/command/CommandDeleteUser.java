package com.alexshay.buber.command;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.util.LocaleBundle;
import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

public class CommandDeleteUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        UserService userService = ServiceFactory.getInstance().getUserService();
        int id = Integer.parseInt(request.getParameter("id"));
        String role = request.getParameter("role");

        try {
            User user = userService.getUserById(id);
            if(user != null){
                userService.deleteUser(user);
                if(role.equals("client")){
                    responseContent.setRouter(new Router(servletPath + "?command=list_clients", Router.Type.REDIRECT));
                }else {
                    responseContent.setRouter(new Router(servletPath + "?command=list_drivers", Router.Type.REDIRECT));
                }
            }else{
                request.setAttribute("message", resourceBundle.getString("admin.page.usernotexist"));
                if(role.equals("client")){
                    responseContent.setRouter(new Router("/buber?command=list_clients", Router.Type.FORWARD));
                }else {
                    responseContent.setRouter(new Router("/buber?command=list_drivers", Router.Type.FORWARD));
                }
            }

        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            if(role.equals("client")){
                responseContent.setRouter(new Router("/buber?command=list_clients", Router.Type.FORWARD));
            }else {
                responseContent.setRouter(new Router("/buber?command=list_drivers", Router.Type.FORWARD));
            }
        }
        return responseContent;

    }
}
