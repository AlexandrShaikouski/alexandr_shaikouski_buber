package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * Abstract JDBC DAO
 * @param <T> - Identified entity
 * @param <PK> - Type primary key of entity
 */
public abstract class AbstractJdbcDao<T extends Identified<PK>, PK extends Number> implements GenericDao<T, PK> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractJdbcDao.class);
    protected Connection connection;

    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    public abstract String getSelectQueryById();

    public abstract String getSelectQueryAll();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    @Override
    @AutoConnection
    public T getByPK(PK key) throws DaoException {
        String sql = getSelectQueryById();
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,(Integer) key);
            ResultSet resultSet = statement.executeQuery();
            List<T> parseRes = parseResultSet(resultSet);
            return parseRes.isEmpty()?null:parseRes.get(0);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DaoException("Not getting info by PK from DB", e);
        }

    }

    @Override
    @AutoConnection
    public List<T> getAll() throws DaoException {

        try (Statement statement = connection.createStatement()){
            statement.execute(getSelectQueryAll());
            ResultSet resultSet = statement.getResultSet();
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DaoException("Not getting all information from DB", e);
        }
    }

    @Override
    @AutoConnection
    public T persist(T object) throws DaoException {

        try (PreparedStatement statement = connection.prepareStatement(getCreateQuery(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, object);
            statement.execute();
            try(ResultSet resultSetKey = statement.getGeneratedKeys()) {
                if (resultSetKey.next()) {
                    object.setId(resultSetKey.getInt(1));
                    return object;
                }else{
                    throw new SQLException();
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DaoException("Failed to insert", e);
        }
    }

    @Override
    @AutoConnection
    public void update(T object) throws DaoException{
        try(PreparedStatement statement = connection.prepareStatement(getUpdateQuery())){
            prepareStatementForUpdate(statement, object);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DaoException("Failed to update", e);
        }
    }

    @Override
    @AutoConnection
    public void delete(T object) throws DaoException {

        try(PreparedStatement statement = connection.prepareStatement(getDeleteQuery())){
            statement.setInt(1, object.getId().intValue());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e);
            throw new DaoException("Failed to delete", e);
        }
    }

}
