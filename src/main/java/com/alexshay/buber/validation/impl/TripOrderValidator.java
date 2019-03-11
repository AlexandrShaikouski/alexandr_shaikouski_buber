package com.alexshay.buber.validation.impl;

import com.alexshay.buber.dao.DaoFactory;
import com.alexshay.buber.dao.DaoFactoryType;
import com.alexshay.buber.dao.FactoryProducer;
import com.alexshay.buber.dao.GenericDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.Bonus;
import com.alexshay.buber.domain.OrderStatus;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.validation.ValidatorTripOrder;

public class TripOrderValidator implements ValidatorTripOrder {
    private static final String REGEX_POINT_GEO = "^(53.)[0-9]+(,27.)[0-9]+$";
    @Override
    public void validate(TripOrder tripOrder) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        String from = tripOrder.getFrom();
        String to = tripOrder.getTo();
        Float price = tripOrder.getPrice();
        OrderStatus status = tripOrder.getStatusOrder();
        Integer driverId = tripOrder.getDriverId();
        Integer id = tripOrder.getId();
        Integer clientId = tripOrder.getClientId();
        Integer bonusId = tripOrder.getBonusId();

        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            GenericDao<Bonus, Integer> bonusDao = daoFactory.getDao(Bonus.class);
            if (from != null && !from.matches(REGEX_POINT_GEO)) {
                throw new ServiceException("Wrong data in order. ");
            }
            if (to != null && !to.matches(REGEX_POINT_GEO)) {
                throw new ServiceException("Wrong data in order. ");
            }
            if (price < 6.0 && price > 200.0) {
                throw new ServiceException("Wrong data in order. ");
            }
            if (status == null) {
                throw new ServiceException("Wrong data in order. ");
            }
            if(userDao.getByPK(clientId) == null){
                throw new ServiceException("Wrong data in order. ");
            }
            if(driverId != 0 && userDao.getByPK(driverId) == null){
                throw new ServiceException("Wrong data in order. ");
            }
            if(bonusId != 0 && bonusDao.getByPK(bonusId) == null){
                throw new ServiceException("Wrong data in order. ");
            }

        }catch (DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
