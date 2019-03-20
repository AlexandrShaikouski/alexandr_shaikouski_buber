package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.ConnectionPool;
import com.alexshay.buber.dao.exception.ConnectionPoolException;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Deque;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class ConnectionPoolImplTest {
    private ConnectionPool connectionPool;
    private int poolCapacity;
    private static final int COUNT_THREAD = 4;

    @Before
    public void initData() throws ConnectionPoolException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
        connectionPool = ConnectionPoolImpl.getInstance();
        poolCapacity = Integer.parseInt(resourceBundle.getString("poolCapacity"));
    }


    @Test
    public void retrieveConnection() throws ConnectionPoolException {
        Deque<Connection> used = ((ConnectionPoolImpl) connectionPool).getUsedConnection();
        Deque<Connection> available = ((ConnectionPoolImpl) connectionPool).getAvailableConnection();
        for (int i = 0; i < COUNT_THREAD; i++) {
            connectionPool.retrieveConnection();
        }
        assertEquals(used.size(), COUNT_THREAD);
        assertEquals(available.size(), poolCapacity > COUNT_THREAD ? poolCapacity - COUNT_THREAD : 0);

    }


    @Test
    public void destroyPool() throws ConnectionPoolException {
        Deque<Connection> used = ((ConnectionPoolImpl) connectionPool).getUsedConnection();
        Deque<Connection> available = ((ConnectionPoolImpl) connectionPool).getAvailableConnection();
        for (int i = 0; i < COUNT_THREAD; i++) {
            connectionPool.retrieveConnection();
        }
        connectionPool.destroyPool();

        assertEquals(used.size(), 0);
        assertEquals(available.size(), poolCapacity);
    }
}