package com.alexshay.buber.service.impl;

import com.alexshay.buber.dao.*;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.dao.exception.PersistException;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;
import java.util.List;
import java.util.Map;

public class TripOrderServiceImpl implements TripOrderService {
    DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);

    @Override
    public List<TripOrder> getAll() throws ServiceException {
        try {
            GenericDao<TripOrder, Integer> tripOrderDao = daoFactory.getDao(TripOrder.class);
            return tripOrderDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        }
    }

    @Override
    public List<TripOrder> getByParameter(Map<String,String> parameter) throws ServiceException {
        try {
            TripOrderDao tripOrderDao = (TripOrderDao) daoFactory.getDao(TripOrder.class);
            return tripOrderDao.getByParameters(parameter);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get order DAO. ", e);
        }
    }

    @Override
    public TripOrder createTripOrder(TripOrder tripOrder) throws ServiceException {
        try {
            GenericDao<TripOrder, Integer> tripOrderDao = daoFactory.getDao(TripOrder.class);
            tripOrder = tripOrderDao.persist(tripOrder);
            return tripOrder;
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        } catch (PersistException e) {
            throw new ServiceException("Failed to save user. ", e);
        }
    }

    @Override
    public void deleteTripOrder(TripOrder tripOrder) throws ServiceException {
        try {
            GenericDao<TripOrder, Integer> tripOrderDao = daoFactory.getDao(TripOrder.class);
            tripOrderDao.delete(tripOrder);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        } catch (PersistException e) {
            throw new ServiceException("Failed to save user. ", e);
        }
    }

    @Override
    public void updateTripOrder(TripOrder tripOrder) throws ServiceException {
        try {
            GenericDao<TripOrder, Integer> tripOrderDao = daoFactory.getDao(TripOrder.class);
            tripOrderDao.update(tripOrder);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        } catch (PersistException e) {
            throw new ServiceException("Failed to save user. ", e);
        }
    }
}
