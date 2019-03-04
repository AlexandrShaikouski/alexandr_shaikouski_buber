package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.ConnectionPoolException;
import com.alexshay.buber.dao.impl.ConnectionPoolImpl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Connection Pool Factory
 */
public class ConnectionPoolFactory {
    private static ConnectionPoolFactory instance;
    private static Lock lock = new ReentrantLock();

    private ConnectionPoolFactory() {}

    public static ConnectionPoolFactory getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ConnectionPoolFactory();
            }

        } finally {
            lock.unlock();
        }

        return instance;
    }

    public ConnectionPool getConnectionPool() throws ConnectionPoolException {
        return ConnectionPoolImpl.getInstance();
    }
}
