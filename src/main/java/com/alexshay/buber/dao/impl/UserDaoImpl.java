package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.AutoConnection;
import com.alexshay.buber.dao.UserDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.domain.UserStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Example User DAO implementation
 */
public class UserDaoImpl extends AbstractJdbcDao<User, Integer> implements UserDao {
    private static final String DELETE_QUERY = "DELETE FROM user_account WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE user_account " +
            "SET login = ?, password = ?, first_name = ?, last_name = ?, " +
            "email = ?, phone = ?, registration_date = ?, location = ?, " +
            "status_ban = ?, role_id = ?, repassword_key = ?, status = ? " +
            "WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM user_account";
    private static final String CREATE_QUERY = "INSERT INTO user_account " +
            "(login, password, first_name, last_name, email, phone, registration_date, location, status_ban, " +
            "role_id, repassword_key, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws SQLException {

        List<User> userList = new ArrayList<>();

        while (rs.next()) {
            User user = User.builder().
                    id(rs.getInt("id")).
                    login(rs.getString("login")).
                    password(rs.getString("password")).
                    firstName(rs.getString("first_name")).
                    lastName(rs.getString("last_name")).
                    email(rs.getString("email")).
                    phone(rs.getString("phone")).
                    registrationTime(new Date(rs.getLong("registration_date"))).
                    location(rs.getString("location")).
                    statusBan(rs.getLong("status_ban") != 0?
                            new Date(rs.getLong("status_ban")):
                            null).
                    role(Role.fromValue(""+rs.getInt("role_id"))).
                    repasswordKey(rs.getString("repassword_key")).
                    status(UserStatus.fromValue(rs.getString("status"))).
                    build();
            userList.add(user);
        }

        return userList;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws SQLException {
        int counter = 0;

        statement.setString(++counter, object.getLogin());
        statement.setString(++counter, object.getPassword());
        statement.setString(++counter, object.getFirstName());
        statement.setString(++counter, object.getLastName());
        statement.setString(++counter, object.getEmail());
        statement.setString(++counter, object.getPhone());
        statement.setLong(++counter, object.getRegistrationTime().getTime());
        statement.setString(++counter, object.getLocation());
        if(object.getStatusBan() != null) {
            statement.setLong(++counter, object.getStatusBan().getTime());
        }else{
            statement.setLong(++counter, 0);
        }
        statement.setInt(++counter, Integer.parseInt(object.getRole().value()));
        statement.setString(++counter, object.getRepasswordKey());
        statement.setString(++counter, object.getStatus().value());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws SQLException {
        prepareStatementForInsert(statement,object);
        statement.setInt(13, object.getId());
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

    @Override
    @AutoConnection
    public List<User> getByParameter(Map<String, String> parameters) throws DaoException {
        String sql = getSelectQuery() + " WHERE ";
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

    @Override
    @AutoConnection
    public User getByLogin(String login) throws DaoException {
        String sql = getSelectQuery() + " WHERE login=\'" + login +"\'";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();
            List<User> parseRes = parseResultSet(resultSet);
            return parseRes.isEmpty()?null:parseRes.get(0);
        } catch (SQLException e) {
            throw new DaoException("Not getting info by PK from DB", e);
        }
    }
}
