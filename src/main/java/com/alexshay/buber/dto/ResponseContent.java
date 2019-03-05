package com.alexshay.buber.dto;

import com.alexshay.buber.command.Router;

import javax.servlet.http.Cookie;

/**
 * Provide response content to View layer
 */
public class ResponseContent {
    private Router router;
    private Cookie[] cookies;

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

}
