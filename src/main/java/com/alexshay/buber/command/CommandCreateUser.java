package com.alexshay.buber.command;

import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.domain.UserStatus;
import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CommandCreateUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        String flag = request.getParameter("flag");
        String login = request.getParameter("login");
        String password = request.getParameter("passwordUser");
        String role = request.getParameter("role");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String firstName = request.getParameter("first_name");
        User user = User.builder().
                login(login).
                password(password).
                role(Role.fromValue(role)).
                email(email).
                phone(phone).
                firstName(firstName).
                status(UserStatus.OFF_LINE).
                registrationTime(new Date()).
                build();

        UserService userService = ServiceFactory.getInstance().getUserService();
        try {
            userService.signUp(user);
            if(flag.equals("client")){
                responseContent.setRouter(new Router(servletPath + "?command=main_page", Router.Type.REDIRECT));
            }else{
                responseContent.setRouter(new Router(servletPath + "?command=success_page", Router.Type.REDIRECT));
            }
        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
            if(flag.equals("client")){
                responseContent.setRouter(new Router("/buber?command=register_page", Router.Type.FORWARD));
            }else if(flag.equals("admin")){
                responseContent.setRouter(new Router("/buber?command=create_page", Router.Type.FORWARD));
            }
        }
        return responseContent;


    }
}
