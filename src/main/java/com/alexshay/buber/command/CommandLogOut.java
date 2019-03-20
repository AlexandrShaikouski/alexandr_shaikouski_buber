package com.alexshay.buber.command;

import com.alexshay.buber.util.ResponseContent;

import javax.servlet.http.HttpServletRequest;

public class CommandLogOut implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        request.getSession().invalidate();
        responseContent.setRouter(new Router(servletPath + "?command=main_page", Router.Type.REDIRECT));
        return responseContent;
    }
}
