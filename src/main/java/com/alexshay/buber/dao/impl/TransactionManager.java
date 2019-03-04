package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.ConnectionPool;
import com.alexshay.buber.dao.ConnectionPoolFactory;
import com.alexshay.buber.dao.GenericDao;
import com.alexshay.buber.dao.exception.ConnectionPoolException;
import com.alexshay.buber.dao.exception.DaoException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of transaction with DAO
 */
public final class TransactionManager {
    private Connection connection;

    public void begin(GenericDao dao, GenericDao... daos) throws DaoException {
        try {
            ConnectionPool connectionPool = ConnectionPoolFactory.getInstance().getConnectionPool();
            connection = connectionPool.retrieveConnection();
            connection.setAutoCommit(false);
            setConnectionWithReflection(dao, connection);
            for (GenericDao d : daos) {
                setConnectionWithReflection(d, connection);
            }
        } catch (ConnectionPoolException | SQLException  e) {
            throw new DaoException("Failed to get a connection from CP.", e);
        }
    }

    public void end() throws SQLException {
        connection.setAutoCommit(true);
        connection.close();
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public void rollback() throws SQLException {
        connection.rollback();
    }


    static void setConnectionWithReflection(Object dao, Connection connection) throws DaoException {
        if (!(dao instanceof AbstractJdbcDao)) {
            throw new DaoException("DAO implementation does not extend AbstractJdbcDao.");
        }

        try {

            Field connectionField = AbstractJdbcDao.class.getDeclaredField("connection");
            if (!connectionField.isAccessible()) {
                connectionField.setAccessible(true);
            }

            connectionField.set(dao, connection);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new DaoException("Failed to set connection for transactional DAO. ", e);
        }
    }

}
