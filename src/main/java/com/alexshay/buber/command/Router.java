package com.alexshay.buber.command;

/**
 * Provide route to jsp page
 */
public class Router {
    private String route;
    private Type type = Type.FORWARD;

    public enum Type {
        FORWARD, REDIRECT
    }

    public Router(String route, Type type) {
        this.route = route;
        this.type = type;
    }

    public String getRoute() {
        return route;
    }

    public Type getType() {
        return type;
    }
}
