package com.alexshay.buber.command;

import com.alexshay.buber.dto.ResponseContent;

import javax.servlet.http.HttpServletRequest;

public class CommandSignOut implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        request.getSession().invalidate();
        responseContent.setRouter(new Router("/", Router.Type.FORWARD));
        return responseContent;
    }
}
