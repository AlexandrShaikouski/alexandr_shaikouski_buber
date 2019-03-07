package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.AutoConnection;
import com.alexshay.buber.dao.UserBonusDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.UserBonus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserBonusDaoImpl extends AbstractJdbcDao<UserBonus, Integer> implements UserBonusDao {
    private static final String DELETE_QUERY = "DELETE FROM user_bonus WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE user_bonus " +
            "SET bonus_id = ?, user_id = ?" +
            "WHERE id = ?";
    private static final String SELECT_QUERY_BY_ID = "SELECT * FROM user_bonus" +
            "WHER id = ?";
    private static final String SELECT_QUERY_ALL = "SELECT * FROM user_bonus";
    private static final String CREATE_QUERY = "INSERT INTO user_bonus " +
            "(bonus_id, user_id) " +
            "VALUES (?, ?)";
    @Override
    protected List<UserBonus> parseResultSet(ResultSet rs) throws SQLException {
        List<UserBonus> userBonuses = new ArrayList<>();

        while (rs.next()) {
            UserBonus userBonus = UserBonus.builder().
                    id(rs.getInt("id")).
                    bonusId(rs.getInt("bonus_id")).
                    userId(rs.getInt("user_id")).
                    build();
            userBonuses.add(userBonus);
        }

        return userBonuses;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, UserBonus object) throws SQLException {
        int counter = 0;

        statement.setInt(++counter, object.getBonusId());
        statement.setInt(++counter, object.getUserId());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, UserBonus object) throws SQLException {
        prepareStatementForInsert(statement, object);
        statement.setInt(3,object.getId());
    }

    @Override
    public String getSelectQueryById() {
        return SELECT_QUERY_BY_ID;
    }

    @Override
    public String getSelectQueryAll() {
        return SELECT_QUERY_ALL;
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
    public List<UserBonus> getByParameter(Map<String, String> parameters) throws DaoException {
        String sql = getSelectQueryAll() + " WHERE ";
        Iterator it = parameters.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            sql +=  pair.getKey() + "=\'" + pair.getValue() +"\'";
            if(it.hasNext()){
                sql += " AND ";
            }
        }
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException("Not getting info by PK from DB", e);
        }
    }
}
