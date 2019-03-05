package com.alexshay.buber.command;

import com.alexshay.buber.dto.ResponseContent;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CommandSignOut implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        String servletPath = request.getRequestURL().toString();
        request.getSession().invalidate();
        Cookie[] cookies = request.getCookies();
        Arrays.stream(cookies).forEach(s->s.setMaxAge(0));
        responseContent.setCookies(cookies);
        responseContent.setRouter(new Router(servletPath + "?command=main_page", Router.Type.REDIRECT));
        return responseContent;
    }
}
