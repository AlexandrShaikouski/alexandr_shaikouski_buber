package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.GenericDao;
import com.alexshay.buber.domain.Bonus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BonusDaoImpl extends AbstractJdbcDao<Bonus, Integer> implements GenericDao<Bonus, Integer> {
    private static final String DELETE_QUERY = "DELETE FROM bonus WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE bonus " +
            "SET name = ?, factor = ?" +
            "WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM bonus";
    private static final String CREATE_QUERY = "INSERT INTO bonus " +
            "(name, factor) " +
            "VALUES (?, ?)";

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
    public String getSelectQuery() {
        return SELECT_QUERY;
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

}
