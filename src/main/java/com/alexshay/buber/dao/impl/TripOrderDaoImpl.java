package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.AutoConnection;
import com.alexshay.buber.dao.TripOrderDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.OrderStatus;
import com.alexshay.buber.domain.TripOrder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripOrderDaoImpl extends AbstractJdbcDao<TripOrder, Integer> implements TripOrderDao {
    private static final String DELETE_QUERY = "DELETE FROM trip_order WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE trip_order " +
            "SET from_x = ?, to_y = ?, status_order = ?, price = ?, " +
            "client_id = ?, driver_id = ?, bonus_id = ?, date_create = ?" +
            "WHERE id = ?";
    private static final String SELECT_QUERY_BY_ID = "SELECT * FROM trip_order " +
            "WHERE id=?";
    private static final String SELECT_QUERY_ALL = "SELECT * FROM trip_order";
    private static final String CREATE_QUERY = "INSERT INTO trip_order " +
            "(from_x, to_y, status_order, price, client_id, driver_id, bonus_id, date_create) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    @Override
    protected List<TripOrder> parseResultSet(ResultSet rs) throws SQLException {
        List<TripOrder> tripOrders = new ArrayList<>();
        while (rs.next()){
            TripOrder tripOrder = TripOrder.builder().
                    id(rs.getInt("id")).
                    from(rs.getString("from_x")).
                    to(rs.getString("to_y")).
                    statusOrder(OrderStatus.fromValue(rs.getString("status_order"))).
                    dateCreate(new Date(rs.getLong("date_create")) ).
                    price(rs.getFloat("price")).
                    clientId(rs.getInt("client_id")).
                    driverId(rs.getInt("driver_id")).
                    bonusId(rs.getInt("bonus_id")).
                    build();
            tripOrders.add(tripOrder);
        }
        return tripOrders;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, TripOrder object) throws SQLException {
        int counter = 0;

        statement.setString(++counter, object.getFrom());
        statement.setString(++counter, object.getTo());
        statement.setString(++counter, object.getStatusOrder().value());
        statement.setFloat(++counter, object.getPrice());
        statement.setInt(++counter, object.getClientId());
        if(object.getDriverId() != 0) {
            statement.setInt(++counter, object.getDriverId());
        }else{
            statement.setNull(++counter, object.getDriverId());
        }
        if(object.getBonusId() != 0) {
            statement.setInt(++counter, object.getBonusId());
        }else{
            statement.setNull(++counter, object.getBonusId());
        }
        statement.setLong(++counter,object.getDateCreate().getTime());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, TripOrder object) throws SQLException {
        prepareStatementForInsert(statement, object);
        statement.setInt(8, object.getId());
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
    public List<TripOrder> getByClientId(Integer clientId) throws DaoException {
        String sql = getSelectQueryAll() + " WHERE client_id=?";
        return getTripOrders(clientId, sql);
    }

    @Override
    @AutoConnection
    public List<TripOrder> getByDriverId(Integer driverId) throws DaoException {
        String sql = getSelectQueryAll() + " WHERE driver_id=?";
        return getTripOrders(driverId, sql);
    }

    @Override
    @AutoConnection
    public List<TripOrder> getByStatus(String status) throws DaoException {
        String sql = getSelectQueryAll() + " WHERE status_order=?";
        return getTripOrders(status, sql);
    }

    private List<TripOrder> getTripOrders(Object parameter, String sql) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,parameter.toString());
            ResultSet resultSet = statement.executeQuery();
            List<TripOrder> parseRes = parseResultSet(resultSet);
            return parseRes;
        } catch (SQLException e) {
            throw new DaoException("Not getting info by email from DB", e);
        }
    }
}
