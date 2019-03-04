package com.alexshay.buber.service.impl;

import com.alexshay.buber.dao.DaoFactory;
import com.alexshay.buber.dao.DaoFactoryType;
import com.alexshay.buber.dao.FactoryProducer;
import com.alexshay.buber.dao.GenericDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.dao.exception.PersistException;
import com.alexshay.buber.domain.Bonus;
import com.alexshay.buber.service.BonusService;
import com.alexshay.buber.service.exception.ServiceException;

import java.util.List;

public class BonusServiceImpl implements BonusService {
    private DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);




    @Override
    public List<Bonus> getAll() throws ServiceException {
        try {
            GenericDao<Bonus,Integer> bonusDao = daoFactory.getDao(Bonus.class);
            return bonusDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to get bonus DAO. ", e);
        }
    }

    @Override
    public Bonus getById(int id) throws ServiceException {
        try {
            GenericDao<Bonus,Integer> bonusDao = daoFactory.getDao(Bonus.class);
            return bonusDao.getByPK(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get bonus DAO. ", e);
        }
    }

    @Override
    public void createBonus(Bonus bonus) throws ServiceException {
        try {
            GenericDao<Bonus,Integer> bonusDao = daoFactory.getDao(Bonus.class);
            bonusDao.persist(bonus);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get bonus DAO. ", e);
        } catch (PersistException e) {
            throw new ServiceException("Failed to save bonus. ", e);
        }
    }

    @Override
    public void deleteBonus(Bonus bonus) throws ServiceException {
        try {
            GenericDao<Bonus,Integer> bonusDao = daoFactory.getDao(Bonus.class);
            bonusDao.delete(bonus);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get bonus DAO. ", e);

        } catch (PersistException e) {
            throw new ServiceException("Failed to save bonus. ", e);
        }
    }
}
