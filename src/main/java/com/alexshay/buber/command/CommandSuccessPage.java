package com.alexshay.buber.command;

import com.alexshay.buber.util.ResponseContent;

import javax.servlet.http.HttpServletRequest;

public class CommandSuccessPage implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        responseContent.setRouter(new Router("/WEB-INF/jsp/admin/success-page.jsp", Router.Type.FORWARD));
        return responseContent;
    }
}
