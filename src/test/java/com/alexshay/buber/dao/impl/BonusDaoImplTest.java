package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.GenericDao;
import com.alexshay.buber.dao.exception.ConnectionPoolException;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.dao.exception.PersistException;
import com.alexshay.buber.domain.Bonus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BonusDaoImplTest {
    private AbstractJdbcDao<Bonus, Integer> daoEntity;
    private Bonus bonus;
    private GenericDao<Bonus,Integer> genericDao;



    @Before
    public void init() throws DaoException, ConnectionPoolException, PersistException {
        bonus = Bonus.builder().
                name("Lucky").
                factor(0.3f).
                build();

        daoEntity = (AbstractJdbcDao) JdbcDaoFactory.getInstance().getTransactionalDao(Bonus.class);
        genericDao = JdbcDaoFactory.getInstance().getDao(Bonus.class);
    }

    @Test
    public void AbstractJdbcDaoForBonusTest() throws DaoException, PersistException {
        Bonus bonus1 = null;
        try {
            bonus1 = genericDao.persist(bonus);
            bonus = genericDao.getByPK(bonus1.getId());
            assertEquals(bonus1, bonus);
            bonus.setName("Unlucky");
            genericDao.update(bonus);
            bonus = genericDao.getByPK(bonus.getId());
            assertEquals("Unlucky", bonus.getName());
        } finally {
            if (bonus1 != null) {
                genericDao.delete(bonus1);
            }
        }


    }
    @After
    public void destroy() throws PersistException {
        daoEntity = null;
        genericDao = null;
        bonus = null;
    }
}
