package com.alexshay.buber.dao.exception;

/**
 * Connection Pool Exception
 */
public class ConnectionPoolException extends Exception {

    public ConnectionPoolException(String message, Exception cause) {
        super(message, cause);
    }
}
