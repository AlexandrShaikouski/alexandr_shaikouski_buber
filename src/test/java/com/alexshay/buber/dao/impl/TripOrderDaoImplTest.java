package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.GenericDao;
import com.alexshay.buber.dao.exception.ConnectionPoolException;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TripOrderDaoImplTest {
    private AbstractJdbcDao<TripOrder, Integer> daoTripOrder;
    private TripOrder tripOrder;
    private User user;
    private GenericDao<TripOrder, Integer> genericDao;
    private GenericDao<User, Integer> daoUser;
    private static final String DELETE_QUERY = "DELETE FROM trip_order WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE trip_order " +
            "SET from_x = ?, to_y = ?, status_order = ?, price = ?, " +
            "client_id = ?, driver_id = ?, bonus_id = ? " +
            "WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM trip_order";
    private static final String CREATE_QUERY = "INSERT INTO trip_order " +
            "(from_x, to_y, status_order, price, client_id, driver_id, bonus_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    @Before
    public void init() throws DaoException{

        user  = User.builder().
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
        daoUser = JdbcDaoFactory.getInstance().getDao(User.class);
        user = daoUser.persist(user);

        tripOrder = TripOrder.builder().
                from("53.8853376,27.5546112,12").
                to("53.8853376,27.5546112,13").
                statusOrder(OrderStatus.WAITING).
                price(3.4f).
                clientId(user.getId()).
                build();


        daoTripOrder = (AbstractJdbcDao) JdbcDaoFactory.getInstance().getTransactionalDao(TripOrder.class);
        genericDao = JdbcDaoFactory.getInstance().getDao(TripOrder.class);
    }

    @Test
    public void AbstractJdbcDaoForTripOrderTest() throws DaoException{
        TripOrder tripOrder1 = null;
        try {
            tripOrder1 = genericDao.persist(tripOrder);
            tripOrder = genericDao.getByPK(tripOrder1.getId());
            assertEquals(tripOrder1, tripOrder);
            tripOrder.setStatusOrder(OrderStatus.IN_PROGRESS);
            genericDao.update(tripOrder);
            tripOrder = genericDao.getByPK(tripOrder.getId());
            assertEquals(OrderStatus.IN_PROGRESS, tripOrder.getStatusOrder());
        } finally {
            if (tripOrder1 != null) {
                genericDao.delete(tripOrder);
            }
        }


    }
    @Test
    public void getSelectQuery() {
        assertEquals(SELECT_QUERY,daoTripOrder.getSelectQueryAll());
    }

    @Test
    public void getCreateQuery() {
        assertEquals(CREATE_QUERY,daoTripOrder.getCreateQuery());
    }

    @Test
    public void getUpdateQuery() {
        assertEquals(UPDATE_QUERY,daoTripOrder.getUpdateQuery());
    }

    @Test
    public void getDeleteQuery() {
        assertEquals(DELETE_QUERY,daoTripOrder.getDeleteQuery());
    }

    @After
    public void destroy() throws DaoException {
        daoUser.delete(user);
        daoTripOrder = null;
        tripOrder = null;
        user = null;
        genericDao = null;
        daoUser = null;
    }
}
