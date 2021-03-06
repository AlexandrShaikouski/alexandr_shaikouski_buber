package com.alexshay.buber.command;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;
import javax.servlet.http.HttpServletRequest;

class ResetPassword {

    ResponseContent getPageEnterKey(HttpServletRequest request) throws ServiceException {
        ResponseContent responseContent = new ResponseContent();
        UserService userService = ServiceFactory.getInstance().getUserService();
        String email = request.getParameter("mail");
        String reset = request.getParameter("reset");
        User user = User.builder().email(email).build();
        userService.sendResetPasswordKey(user);

        request.setAttribute("reset", reset);
        request.setAttribute("mail", email);
        responseContent.setRouter(new Router("/WEB-INF/jsp/pages/reset-password.jsp", Router.Type.FORWARD));

        return responseContent;
    }


    ResponseContent getPageEnterPassword(HttpServletRequest request) throws ServiceException {
        ResponseContent responseContent = new ResponseContent();
        UserService userService = ServiceFactory.getInstance().getUserService();
        String email = request.getParameter("mail");
        String reset = request.getParameter("reset");
        String repasswordKey = request.getParameter("key");
        User user = User.builder().email(email).repasswordKey(repasswordKey).build();
        userService.checkRepasswordKey(user);

        request.setAttribute("mail",email);
        request.setAttribute("reset",reset);
        responseContent.setRouter(new Router("/WEB-INF/jsp/pages/reset-password.jsp", Router.Type.FORWARD));
        return responseContent;
    }

    ResponseContent getNewPassword(HttpServletRequest request) throws ServiceException {
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        UserService userService = ServiceFactory.getInstance().getUserService();
        String email = request.getParameter("mail");
        String passwordUser = request.getParameter("passwordUser");
        User user = User.builder().password(passwordUser).email(email).build();

        userService.resetPassword(user);

        responseContent.setRouter(new Router(servletPath + "?command=main_page", Router.Type.REDIRECT));
        return responseContent;
    }



}
