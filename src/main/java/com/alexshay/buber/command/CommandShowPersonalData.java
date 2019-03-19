package com.alexshay.buber.command;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.util.ResponseContent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommandShowPersonalData implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        switch (user.getRole()) {
            case CLIENT:
                responseContent.setRouter(new Router("/WEB-INF/jsp/client/info-client.jsp", Router.Type.FORWARD));
                break;
            case DRIVER:
                responseContent.setRouter(new Router("/WEB-INF/jsp/driver/info-driver.jsp", Router.Type.FORWARD));
                break;
            case ADMIN:
                responseContent.setRouter(new Router("/WEB-INF/jsp/admin/info-admin.jsp", Router.Type.FORWARD));
                break;
        }
        return responseContent;
    }
}
