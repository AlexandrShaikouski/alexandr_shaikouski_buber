package com.alexshay.buber.command;

import com.alexshay.buber.dto.ResponseContent;
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
                case "1":
                    return resetPassword.getPageEnterKey(request);
                case "2":
                    return resetPassword.getPageEnterPassword(request);
                case "3":
                    return resetPassword.getNewPassword(request);
            }

        } catch (ServiceException e) {
            request.setAttribute("email",email);
            request.setAttribute("reset", reset.equals("1")?null:Integer.parseInt(reset) - 1);
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("/WEB-INF/jsp/pages/reset-password.jsp", Router.Type.FORWARD));
            return responseContent;
        }
        return responseContent;


    }
}
