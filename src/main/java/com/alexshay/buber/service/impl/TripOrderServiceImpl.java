package com.alexshay.buber.service.impl;

import com.alexshay.buber.dao.*;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;
import java.util.List;

public class TripOrderServiceImpl implements TripOrderService {


    @Override
    public List<TripOrder> getAll() throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<TripOrder, Integer> tripOrderDao = daoFactory.getDao(TripOrder.class);
            return tripOrderDao.getAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        }
    }


    @Override
    public TripOrder createTripOrder(TripOrder tripOrder) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<TripOrder, Integer> tripOrderDao = daoFactory.getDao(TripOrder.class);
            tripOrder = tripOrderDao.persist(tripOrder);
            return tripOrder;
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }

    @Override
    public void deleteTripOrder(TripOrder tripOrder) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<TripOrder, Integer> tripOrderDao = daoFactory.getDao(TripOrder.class);
            tripOrderDao.delete(tripOrder);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        }
    }

    @Override
    public void updateTripOrder(TripOrder tripOrder) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<TripOrder, Integer> tripOrderDao = daoFactory.getDao(TripOrder.class);
            tripOrderDao.update(tripOrder);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        }
    }

    @Override
    public List<TripOrder> getByClientId(Integer clientId) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            TripOrderDao tripOrderDao = (TripOrderDao) daoFactory.getDao(TripOrder.class);
            return tripOrderDao.getByClientId(clientId);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }

    @Override
    public List<TripOrder> getByDriverId(Integer driverId) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            TripOrderDao tripOrderDao = (TripOrderDao) daoFactory.getDao(TripOrder.class);
            return tripOrderDao.getByDriverId(driverId);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }
}
