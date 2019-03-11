package com.alexshay.buber.validation.impl;

import com.alexshay.buber.dao.*;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.validation.ValidatorUser;

public class EmailValidatorImpl implements ValidatorUser {
    @Override
    public void validate(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            if(userDao.getByEmail(user.getEmail()) != null){
                throw new ServiceException("Email do not exist. ");
            }
        }catch (DaoException e){
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }
}
