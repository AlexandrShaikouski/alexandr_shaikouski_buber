package com.alexshay.buber.command;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.dto.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandSignInUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        UserService userService = ServiceFactory.getInstance().getUserService();
        String login = request.getParameter("login");
        String password = request.getParameter("passwordUser");
        User user = User.builder().
                login(login).
                password(password).
                build();
        try {
            user = userService.signIn(user);

            HttpSession session = request.getSession();
            session.setAttribute("user",user);

        } catch (ServiceException e) {
            request.setAttribute("message", e.getMessage());
        }finally {
            responseContent.setRouter(new Router("/", Router.Type.FORWARD));
            return responseContent;
        }

    }
}