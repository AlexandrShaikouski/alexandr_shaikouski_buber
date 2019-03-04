package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.GenericDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.dao.exception.PersistException;
import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.domain.UserStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class UserDaoImplTest {
    private AbstractJdbcDao<User, Integer> daoUser;
    private GenericDao<User,Integer> genericDao;
    private User user;
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

    @Before
    public void init() throws DaoException {
        user = User.builder().
                login("A").
                password("871FF76E24362EFA16E7F39D65EE380ADE9129D969E895CE34E5DB54252604FB").
                firstName("A").
                lastName("Shaikouski").
                email("sash_shay@mail.ruff").
                phone("+373356182421").
                location("53.8853376,27.5546112,12").
                registrationTime(new Date()).
                role(Role.ADMIN).
                status(UserStatus.OFF_LINE).
                build();


        daoUser = (AbstractJdbcDao) JdbcDaoFactory.getInstance().getTransactionalDao(User.class);
        genericDao = JdbcDaoFactory.getInstance().getDao(User.class);
    }

    @Test
    public void AbstractJdbcDaoForUserTest() throws PersistException, DaoException {
        User user1 = null;
        try {
            user1 = genericDao.persist(user);
            User user2 = genericDao.getByPK(user1.getId());
            assertEquals(user1, user2);
            user.setLogin("B");
            genericDao.update(user1);
            user2 = genericDao.getByPK(user2.getId());
            assertEquals("B", user2.getLogin());
        }finally {
            if(user1 != null) {
                genericDao.delete(user1);
            }
        }
    }

    @Test
    public void getSelectQuery() {
        assertEquals(SELECT_QUERY,daoUser.getSelectQuery());
    }

    @Test
    public void getCreateQuery() {
        assertEquals(CREATE_QUERY,daoUser.getCreateQuery());
    }

    @Test
    public void getUpdateQuery() {
        assertEquals(UPDATE_QUERY,daoUser.getUpdateQuery());
    }

    @Test
    public void getDeleteQuery() {
        assertEquals(DELETE_QUERY,daoUser.getDeleteQuery());
    }

    @After
    public void destroy(){
        daoUser = null;
        genericDao = null;
        user = null;
    }
}