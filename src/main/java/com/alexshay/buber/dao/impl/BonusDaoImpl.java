package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.AutoConnection;
import com.alexshay.buber.dao.BonusDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.Bonus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BonusDaoImpl extends AbstractJdbcDao<Bonus, Integer> implements BonusDao {
    private static final Logger LOGGER = LogManager.getLogger(BonusDaoImpl.class);
    private static final String DELETE_QUERY = "DELETE FROM bonus WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE bonus " +
            "SET name = ?, factor = ?" +
            "WHERE id = ?";
    private static final String SELECT_QUERY_ALL = "SELECT * FROM bonus";
    private static final String SELECT_QUERY_BY_ID = "SELECT * FROM bonus WHERE id=?";
    private static final String CREATE_QUERY = "INSERT INTO bonus " +
            "(name, factor) " +
            "VALUES (?, ?)";
    private static final String SELECT_QUERY_BY_CLIENT_ID = "SELECT b.id,b.name,b.factor from user_account uc\n" +
            "inner join user_bonus ub on uc.id = ub.user_id\n" +
            "inner join bonus b on ub.bonus_id = b.id\n" +
            "where uc.id = ?";
    @Override
    protected List<Bonus> parseResultSet(ResultSet rs) throws SQLException {
        List<Bonus> bonuses = new ArrayList<>();
        while (rs.next()) {
            Bonus bonus = Bonus.builder().
                    id(rs.getInt("id")).
                    name(rs.getString("name")).
                    factor(rs.getFloat("factor")).
                    build();
            bonuses.add(bonus);
        }
        return bonuses;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Bonus object) throws SQLException {
        int counter = 0;

        statement.setString(++counter, object.getName());
        statement.setFloat(++counter, object.getFactor());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Bonus object) throws SQLException {
        prepareStatementForInsert(statement, object);
        statement.setInt(3,object.getId());
    }


    @Override
    public String getSelectQueryAll() {
        return SELECT_QUERY_ALL;
    }
    @Override
    public String getSelectQueryById() {
        return SELECT_QUERY_BY_ID;
    }

    @Override
    public String getCreateQuery() {
        return CREATE_QUERY;
    }

    @Override
    public String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    public String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    @AutoConnection
    public List<Bonus> getByClientId(int clientId) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY_BY_CLIENT_ID)) {
            statement.setInt(1,clientId);
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        }catch (SQLException e){
            LOGGER.error(e);
            throw new DaoException("Not getting info by role from DB", e);
        }
    }
}
