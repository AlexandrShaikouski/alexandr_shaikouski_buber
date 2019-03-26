package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.*;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.Bonus;
import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.domain.UserStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Example User DAO implementation
 */
public class UserDaoImpl extends AbstractJdbcDao<User, Integer> implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    private static final String DELETE_QUERY = "DELETE FROM user_account WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE user_account " +
            "SET login = ?, password = ?, first_name = ?, " +
            "email = ?, phone = ?, registration_date = ?, " +
            "status_ban = ?, role_id = ?, repassword_key = ?, status = ? " +
            "WHERE id = ?";
    private static final String SELECT_QUERY_BY_ID = "SELECT user_account.id, user_account.login, user_account.password, " +
            "user_account.first_name, user_account.email, user_account.phone, " +
            "user_account.registration_date, user_account.status_ban, " +
            "role.name AS role, user_account.repassword_key, user_account.status " +
            "FROM user_account " +
            "INNER JOIN role ON user_account.role_id = role.id " +
            "WHERE user_account.id = ?";
    private static final String SELECT_QUERY_ALL = "SELECT user_account.id, user_account.login, user_account.password, " +
            "user_account.first_name, user_account.email, user_account.phone, " +
            "user_account.registration_date, user_account.status_ban, " +
            "role.name AS role, user_account.repassword_key, user_account.status " +
            "FROM user_account " +
            "INNER JOIN role ON user_account.role_id = role.id ";


    private static final String CREATE_QUERY = "INSERT INTO user_account " +
            "(login, password, first_name, email, phone, registration_date, status_ban, " +
            "role_id, repassword_key, status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws SQLException {

        List<User> userList = new ArrayList<>();

        while (rs.next()) {
            User user = User.builder().
                    id(rs.getInt("id")).
                    login(rs.getString("login")).
                    password(rs.getString("password")).
                    firstName(rs.getString("first_name")).
                    email(rs.getString("email")).
                    phone(rs.getString("phone")).
                    registrationTime(new Date(rs.getLong("registration_date"))).
                    statusBan(rs.getLong("status_ban") != 0 ?
                            new Date(rs.getLong("status_ban")) :
                            null).
                    role(Role.fromValue(rs.getString("role"))).
                    repasswordKey(rs.getString("repassword_key")).
                    status(UserStatus.fromValue(rs.getString("status"))).
                    bonuses(getBonusesById(rs.getInt("id"))).
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
        statement.setString(++counter, object.getEmail());
        statement.setString(++counter, object.getPhone());
        statement.setLong(++counter, object.getRegistrationTime().getTime());
        if (object.getStatusBan() != null) {
            statement.setLong(++counter, object.getStatusBan().getTime());
        } else {
            statement.setLong(++counter, 0);
        }
        statement.setInt(++counter, getRoleId(object.getRole().value()));
        statement.setString(++counter, object.getRepasswordKey());
        statement.setString(++counter, object.getStatus().value());
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws SQLException {
        prepareStatementForInsert(statement, object);
        statement.setInt(11, object.getId());
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
    public User getByLogin(String login) throws DaoException {
        String sql = getSelectQueryAll() + " WHERE login=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return getUser(login, statement);
        } catch (SQLException e) {
            throw new DaoException("Not getting info by login from DB", e);
        }
    }

    @Override
    @AutoConnection
    public User getByEmail(String email) throws DaoException {
        String sql = getSelectQueryAll() + " WHERE email=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return getUser(email, statement);
        } catch (SQLException e) {
            throw new DaoException("Not getting info by mail from DB", e);
        }
    }

    @Override
    @AutoConnection
    public User getByPhone(String phone) throws DaoException {
        String sql = getSelectQueryAll() + " WHERE phone=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            return getUser(phone, statement);
        } catch (SQLException e) {
            throw new DaoException("Not getting info by mail from DB", e);
        }
    }

    private User getUser(String name, PreparedStatement statement) throws SQLException {
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery();
        List<User> parseRes = parseResultSet(resultSet);
        return parseRes.isEmpty() ? null : parseRes.get(0);
    }

    @Override
    @AutoConnection
    public List<User> getByRole(String role) throws DaoException {
        try {
            int roleId = getRoleId(role);
            String sql = getSelectQueryAll() + " WHERE role_id="+ roleId;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                ResultSet resultSet = statement.executeQuery();
                List<User> parseRes = parseResultSet(resultSet);
                return parseRes;
            }
        } catch (SQLException e) {
            throw new DaoException("Not getting info by role from DB", e);
        }
    }

    @AutoConnection
    private Integer getRoleId(String roleName) throws SQLException {
        String sql = "SELECT id FROM role WHERE name=\'" + roleName + "\'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();
            return resultSet.getInt("id");
        }
    }

    private List<Bonus> getBonusesById(int id) {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        BonusDao bonusDao;
        List<Bonus> bonuses = Collections.emptyList();
        try {
            bonusDao = (BonusDao) daoFactory.getDao(Bonus.class);
            bonuses = bonusDao.getByClientId(id);
        } catch (DaoException e) {
            LOGGER.error(e);
        }
        return bonuses;
    }
}
