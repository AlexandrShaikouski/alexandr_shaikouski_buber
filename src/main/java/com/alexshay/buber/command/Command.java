package com.alexshay.buber.command;

import com.alexshay.buber.util.ResponseContent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Command
 */
public interface Command {
    Logger LOGGER = LogManager.getLogger(Command.class);
    /**
     * Execute command
     * @param request is used for extracting request parameters
     * @return response content
     */
    ResponseContent execute(HttpServletRequest request);
}
