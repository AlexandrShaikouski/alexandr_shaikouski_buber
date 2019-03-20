package com.alexshay.buber.command;

import com.alexshay.buber.util.ResponseContent;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;

public class CommandChangeLocale implements Command {

    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        String locale = request.getParameter("localePage");
        HttpSession session = request.getSession();
        session.setAttribute("locale",locale);
        Cookie localeCookie = new Cookie("locale",locale);
        responseContent.setCookies(new Cookie[]{localeCookie});

        responseContent.setRouter(new Router(servletPath + "?command=main_page", Router.Type.REDIRECT));

        return responseContent;
    }
}
