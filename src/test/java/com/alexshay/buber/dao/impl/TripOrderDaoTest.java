package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.GenericDao;
import com.alexshay.buber.dao.TripOrderDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TripOrderDaoTest {
    private AbstractJdbcDao abstractJdbcDao;
    private TripOrder tripOrderBuild;
    private User user;
    private TripOrderDao trioOrderDao;
    private GenericDao<User, Integer> daoUser;
    private static final String DELETE_QUERY = "DELETE FROM trip_order WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE trip_order " +
            "SET from_x = ?, to_y = ?, status_order = ?, price = ?, " +
            "client_id = ?, driver_id = ?, bonus_id = ?, date_create = ? " +
            "WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM trip_order";
    private static final String CREATE_QUERY = "INSERT INTO trip_order " +
            "(from_x, to_y, status_order, price, client_id, driver_id, bonus_id, date_create) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_QUERY_BY_ID = "SELECT * FROM trip_order " +
            "WHERE id=?";

    @Before
    public void init() throws DaoException {
        abstractJdbcDao = (AbstractJdbcDao) JdbcDaoFactory.getInstance().getTransactionalDao(TripOrder.class);
        trioOrderDao = (TripOrderDao) JdbcDaoFactory.getInstance().getDao(TripOrder.class);
        daoUser = JdbcDaoFactory.getInstance().getDao(User.class);
        user = User.builder().
                login("T").
                password("871FF76E24362EFA16E7F39D65EE380ADE9129D969E895CE34E5DB54252604FB").
                firstName("T").
                lastName("Shaikouski").
                email("sash_shay@mail.ruff").
                phone("+373356182421").
                location("53.8853376,27.5546112,12").
                registrationTime(new Date()).
                role(Role.CLIENT).
                status(UserStatus.OFF_LINE).
                build();
        tripOrderBuild = TripOrder.builder().
                from("53.8853376,27.5546112,12").
                to("53.8853376,27.5546112,13").
                statusOrder(OrderStatus.WAITING).
                price(3.4f).
                dateCreate(new Date()).
                build();


    }

    @Test
    public void abstractJdbcDaoForTripOrderTest() throws DaoException {
        user = daoUser.persist(user);
        tripOrderBuild.setDriverId(user.getId());
        tripOrderBuild.setClientId(user.getId());
        TripOrder tripOrderCheck = null;
        try {
            tripOrderCheck = trioOrderDao.persist(tripOrderBuild);
            tripOrderBuild = trioOrderDao.getByPK(tripOrderCheck.getId());
            assertEquals(tripOrderCheck, tripOrderBuild);
            tripOrderCheck.setStatusOrder(OrderStatus.IN_PROGRESS);
            trioOrderDao.update(tripOrderCheck);
            tripOrderCheck = trioOrderDao.getByPK(tripOrderCheck.getId());
            assertEquals(OrderStatus.IN_PROGRESS, tripOrderCheck.getStatusOrder());
            tripOrderCheck = trioOrderDao.getByClientId(user.getId()).get(0);
            tripOrderBuild = trioOrderDao.getByDriverId(user.getId()).get(0);
            assertEquals(tripOrderCheck, tripOrderBuild);
            tripOrderCheck = trioOrderDao.getByStatus("in-progress").stream().
                    filter(s -> s.getClientId() == user.getId()).
                    findFirst().orElse(null);
            assertNotNull(tripOrderCheck);
        } finally {
            if (tripOrderCheck != null) {
                trioOrderDao.delete(tripOrderCheck);
                tripOrderCheck = trioOrderDao.getByPK(tripOrderCheck.getId());
            }
            daoUser.delete(user);
        }
        assertNull(tripOrderCheck);


    }

    @Test
    public void getSelectQuery() {
        assertEquals(SELECT_QUERY, abstractJdbcDao.getSelectQueryAll());
    }

    @Test
    public void getCreateQuery() {
        assertEquals(CREATE_QUERY, abstractJdbcDao.getCreateQuery());
    }

    @Test
    public void getUpdateQuery() {
        assertEquals(UPDATE_QUERY, abstractJdbcDao.getUpdateQuery());
    }

    @Test
    public void getDeleteQuery() {
        assertEquals(DELETE_QUERY, abstractJdbcDao.getDeleteQuery());
    }

    @Test
    public void getSelectQueryById() {
        assertEquals(SELECT_QUERY_BY_ID, abstractJdbcDao.getSelectQueryById());
    }

    @After
    public void destroy() throws DaoException {
        abstractJdbcDao = null;
        user = null;
        trioOrderDao = null;
        daoUser = null;
        trioOrderDao = null;
    }
}
