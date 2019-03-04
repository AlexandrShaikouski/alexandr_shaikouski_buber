package com.alexshay.buber.dto;

import com.alexshay.buber.command.Router;

/**
 * Provide response content to View layer
 */
public class ResponseContent {
    private Router router;

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

}
