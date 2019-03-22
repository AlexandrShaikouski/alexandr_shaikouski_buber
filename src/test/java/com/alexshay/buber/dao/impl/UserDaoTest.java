package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.UserDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.domain.UserStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserDaoTest {
    private AbstractJdbcDao<User, Integer> abstractJdbcDao;
    private UserDao userDao;
    private User userBuild;
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

    @Before
    public void init() throws DaoException {
        abstractJdbcDao = (AbstractJdbcDao) JdbcDaoFactory.getInstance().getTransactionalDao(User.class);
        userDao = (UserDao) JdbcDaoFactory.getInstance().getDao(User.class);
        userBuild = User.builder().
                login("A").
                password("871FF76E24362EFA16E7F39D65EE380ADE9129D969E895CE34E5DB54252604FB").
                firstName("A").
                email("A").
                phone("A").
                registrationTime(new Date()).
                role(Role.ADMIN).
                status(UserStatus.OFF_LINE).
                bonuses(Collections.emptyList()).
                build();



    }

    @Test
    public void abstractJdbcDaoForUserTest() throws DaoException {
        User userCheck = null;
        try {
            userCheck = userDao.persist(userBuild);
            userBuild = userDao.getByPK(userCheck.getId());
            assertEquals(userCheck, userBuild);
            userCheck.setLogin("B");
            userDao.update(userCheck);
            userCheck = userDao.getByPK(userCheck.getId());
            assertEquals("B", userCheck.getLogin());
            userCheck = userDao.getByEmail("A");
            assertNotNull(userCheck);
            userCheck = userDao.getByLogin("B");
            assertNotNull(userCheck);
            userCheck = userDao.getByPhone("A");
            assertNotNull(userCheck);
            userCheck = userDao.getByRole(Role.ADMIN.value()).stream().
                    filter(s -> s.getLogin().equals("B")).
                    findFirst().orElse(null);
            assertNotNull(userCheck);
        }finally {
            if(userCheck != null) {
                userDao.delete(userCheck);
            }
        }
    }

    @Test
    public void getSelectQueryAll() {
        assertEquals(SELECT_QUERY_ALL,abstractJdbcDao.getSelectQueryAll());
    }

    @Test
    public void getSelectQueryById() {
        assertEquals(SELECT_QUERY_BY_ID,abstractJdbcDao.getSelectQueryById());
    }

    @Test
    public void getCreateQuery() {
        assertEquals(CREATE_QUERY,abstractJdbcDao.getCreateQuery());
    }

    @Test
    public void getUpdateQuery() {
        assertEquals(UPDATE_QUERY,abstractJdbcDao.getUpdateQuery());
    }

    @Test
    public void getDeleteQuery() {
        assertEquals(DELETE_QUERY,abstractJdbcDao.getDeleteQuery());
    }



    @After
    public void destroy(){
        abstractJdbcDao = null;
        userDao = null;
        userBuild = null;
    }
}