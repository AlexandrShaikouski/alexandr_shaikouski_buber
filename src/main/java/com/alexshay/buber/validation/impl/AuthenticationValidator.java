package com.alexshay.buber.validation.impl;

import com.alexshay.buber.dao.DaoFactory;
import com.alexshay.buber.dao.DaoFactoryType;
import com.alexshay.buber.dao.FactoryProducer;
import com.alexshay.buber.dao.UserDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.validation.ValidatorUser;

public class AuthenticationValidator implements ValidatorUser {
    @Override
    public void validate(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            User userValid = userDao.getByLogin(user.getLogin());
            if(userValid != null){
                if(!user.getPassword().equals(userValid.getPassword())){
                    throw new ServiceException("Wrong password! ");
                }
            }else {
                throw new ServiceException("Login do not exist. ");
            }

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }
}
