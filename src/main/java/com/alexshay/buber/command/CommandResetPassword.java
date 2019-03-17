package com.alexshay.buber.command;

import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public class CommandResetPassword implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        ResetPassword resetPassword = new ResetPassword();
        String reset = request.getParameter("reset");
        String email = request.getParameter("email");
        if (reset == null){
            responseContent.setRouter(new Router("/WEB-INF/jsp/pages/reset-password.jsp", Router.Type.FORWARD));
            return responseContent;
        }
        try {
            switch (reset) {
                case "send_key":
                    return resetPassword.getPageEnterKey(request);
                case "check_key":
                    return resetPassword.getPageEnterPassword(request);
                case "enter_new_pass":
                    return resetPassword.getNewPassword(request);
            }

        } catch (ServiceException e) {
            request.setAttribute("email",email);
            request.setAttribute("reset", reset.equals("send_key")?null:reset.equals("check_key")?"send_key":reset);
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("/WEB-INF/jsp/pages/reset-password.jsp", Router.Type.FORWARD));
            return responseContent;
        }
        return responseContent;


    }
}
