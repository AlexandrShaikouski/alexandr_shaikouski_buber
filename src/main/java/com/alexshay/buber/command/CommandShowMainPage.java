package com.alexshay.buber.command;

import com.alexshay.buber.dto.ResponseContent;
import javax.servlet.http.HttpServletRequest;

public class CommandShowMainPage implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        responseContent.setRouter(new Router("WEB-INF/jsp/main.jsp", Router.Type.FORWARD));
        return responseContent;
    }
}
