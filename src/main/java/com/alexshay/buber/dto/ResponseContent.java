package com.alexshay.buber.dto;

import com.alexshay.buber.command.Router;

import javax.servlet.http.Cookie;
import java.util.Map;

/**
 * Provide response content to View layer
 */
public class ResponseContent {
    private Router router;
    private Cookie[] cookies;
    private Map<String, Object> responseParameters;

    public Map<String, Object> getResponseParameters() {
        return responseParameters;
    }

    public void setResponseParameters(Map<String, Object> responseParameters) {
        this.responseParameters = responseParameters;
    }

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
