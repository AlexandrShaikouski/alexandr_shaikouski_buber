package com.alexshay.buber.dao.impl;

import com.alexshay.buber.dao.AbstractJdbcDao;
import com.alexshay.buber.dao.BonusDao;
import com.alexshay.buber.dao.UserBonusDao;
import com.alexshay.buber.dao.UserDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserBonusDaoTest {
    private UserDao userDao;
    private AbstractJdbcDao abstractJdbcDao;
    private User client;
    private Bonus bonusBuild;
    private UserBonusDao userBonusDao;
    private BonusDao bonusDao;
    private static final String DELETE_QUERY = "DELETE FROM bonus WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE bonus " +
            "SET name = ?, factor = ?" +
            "WHERE id = ?";
    private static final String SELECT_QUERY_ALL = "SELECT * FROM bonus";
    private static final String SELECT_QUERY_BY_ID = "SELECT * FROM bonus WHERE id=?";
    private static final String CREATE_QUERY = "INSERT INTO bonus " +
            "(name, factor) " +
            "VALUES (?, ?)";
    private static final String SELECT_QUERY_BY_CLIENT_ID = "SELECT b.id,b.name,b.factor from user_account uc\n" +
            "inner join user_bonus ub on uc.id = ub.user_id\n" +
            "inner join bonus b on ub.bonus_id = b.id\n" +
            "where uc.id = ?";
    @Before
    public void init() throws DaoException {
        userBonusDao = (UserBonusDao) JdbcDaoFactory.getInstance().getDao(UserBonus.class);
        abstractJdbcDao = (AbstractJdbcDao) JdbcDaoFactory.getInstance().getTransactionalDao(Bonus.class);
        bonusDao = (BonusDao) JdbcDaoFactory.getInstance().getDao(Bonus.class);
        userDao = (UserDao) JdbcDaoFactory.getInstance().getDao(User.class);
        client = User.builder().
                login("C").
                password("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC").
                firstName("C").
                email("C").
                phone("C").
                registrationTime(new Date()).
                role(Role.CLIENT).
                status(UserStatus.OFF_LINE).
                bonuses(Collections.emptyList()).
                build();
        bonusBuild = Bonus.builder().
                name("Lucky").
                factor(0.3f).
                build();


    }

    @Test
    public void abstractJdbcDaoForUserBonusTest() throws DaoException {
        userDao.persist(client);
        Bonus bonusCheck = null;
        UserBonus userBonusCheck;
        try {
            bonusCheck = bonusDao.persist(bonusBuild);
            bonusBuild = bonusDao.getByPK(bonusCheck.getId());
            assertEquals(bonusCheck, bonusBuild);
            bonusCheck.setName("Unlucky");
            bonusDao.update(bonusCheck);
            bonusBuild = bonusDao.getByPK(bonusBuild.getId());
            assertEquals("Unlucky", bonusBuild.getName());
            UserBonus userBonusBuild = UserBonus.builder().
                    userId(client.getId()).
                    bonusId(bonusCheck.getId()).
                    build();
            userBonusCheck = userBonusDao.persist(userBonusBuild);
            userBonusBuild = userBonusDao.getByPK(userBonusCheck.getId());
            assertEquals(userBonusCheck, userBonusBuild);
            userBonusCheck = userBonusDao.getByParameter(new HashMap<String, String>(){{
                put("user_id",client.getId().toString());
            }}).get(0);
            userBonusBuild = userBonusDao.getByPK(userBonusCheck.getId());
            assertEquals(userBonusBuild, userBonusCheck);
            final int bonusId = userBonusCheck.getBonusId();
            bonusCheck = bonusDao.getByClientId(client.getId()).stream().
                    filter(s -> s.getId() == bonusId).
                    findFirst().get();
            assertEquals("Unlucky", bonusCheck.getName());

        } finally {
            if (bonusCheck != null) {
                bonusDao.delete(bonusCheck);
                bonusCheck = bonusDao.getByPK(bonusCheck.getId());
            }
            userDao.delete(client);
        }
        assertNull(bonusCheck);
    }

    @Test
    public void getSelectQueryAll() {
        assertEquals(SELECT_QUERY_ALL, abstractJdbcDao.getSelectQueryAll());
    }

    @Test
    public void getSelectQueryById() {
        assertEquals(SELECT_QUERY_BY_ID, abstractJdbcDao.getSelectQueryById());
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
    public void getSelectQueryByClientId() {
        Assert.assertEquals(SELECT_QUERY_BY_CLIENT_ID, ((BonusDaoImpl) abstractJdbcDao).getSelectQueryByClientId());
    }

    @After
    public void destroy() throws DaoException {
        userDao = null;
        abstractJdbcDao = null;
        client = null;
        bonusBuild = null;
        userBonusDao = null;
        bonusDao = null;
    }
}
