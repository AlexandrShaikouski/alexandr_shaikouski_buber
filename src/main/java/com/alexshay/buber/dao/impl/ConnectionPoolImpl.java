package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.ConnectionPool;
import com.alexshay.buber.dao.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Implementation of Connection Pool
 */
public class ConnectionPoolImpl implements ConnectionPool {
    private static ConnectionPool instance;
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPoolImpl.class);
    private Deque<Connection> availableConnection;
    private Deque<Connection> usedConnection;
    private static Lock lock = new ReentrantLock();

    private ConnectionPoolImpl() {
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ConnectionPoolImpl();
                instance.init();
            }

        } finally {
            lock.unlock();
        }

        return instance;
    }

    @Override
    public void init() throws ConnectionPoolException {
        try {
            ResourceBundle resource = ResourceBundle.getBundle("db");
            int poolCapacity = Integer.parseInt(resource.getString("poolCapacity"));
            availableConnection = new ConcurrentLinkedDeque<>();
            for (int i = 0; i < poolCapacity; i++) {
                availableConnection.add(getProxyConnection());
            }
            usedConnection = new ConcurrentLinkedDeque<>();

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new ConnectionPoolException("Initialize problem", e);
        }
    }

    @Override
    public Connection retrieveConnection() throws ConnectionPoolException {
        try {
            lock.lock();
            if (!availableConnection.isEmpty()) {
                Connection connection = availableConnection.pop();
                usedConnection.addLast(connection);
                return usedConnection.getLast();
            } else {
                Connection connection = ConnectorDB.getConnection();
                usedConnection.addLast(connection);
                return usedConnection.getLast();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new ConnectionPoolException("Problem with getting connection", e);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void putBackConnection(Connection connection) {
        try {
            lock.lock();
            availableConnection.add(connection);
            usedConnection.remove(connection);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void destroyPool() throws ConnectionPoolException {
        try {
            for (Connection c : availableConnection) {
                c.close();
            }
            for (Connection c : usedConnection) {
                c.close();
                usedConnection.remove(c);
            }

        } catch (SQLException e) {
            LOGGER.error(e);
            throw new ConnectionPoolException("Problem with destroy", e);
        }
    }

    private Connection getProxyConnection() throws SQLException {
        Connection connection = ConnectorDB.getConnection();
        return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (method.getName().matches(".*close.*")) {
                        putBackConnection(connection);
                        return null;
                    }else if(method.getName().matches(".*equals.*")){
                        return proxy == args[0] ;
                    }

                    return method.invoke(connection, args);
                });
    }

     private static class ConnectorDB {

        public static Connection getConnection() throws SQLException {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
            ResourceBundle resource = ResourceBundle.getBundle("db");
            String url = resource.getString("url")+ "?" +
                    "useUnicode=" + resource.getString("useUnicode") + "&" +
                    "characterEncoding=" + resource.getString("characterEncoding") + "&" +
                    "autoReconnect=" + resource.getString("autoReconnect") + "&" +
                    "useSSL=" + resource.getString("useSSL");
            String user = resource.getString("user");
            String pass = resource.getString("password");
            return DriverManager.getConnection(url, user, pass);
        }
    }

    public Deque<Connection> getAvailableConnection() {
        return availableConnection;
    }

    public Deque<Connection> getUsedConnection() {
        return usedConnection;
    }
}