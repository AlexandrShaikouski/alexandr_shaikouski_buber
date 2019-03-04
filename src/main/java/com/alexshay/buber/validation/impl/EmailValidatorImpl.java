package com.alexshay.buber.validation.impl;

import com.alexshay.buber.dao.*;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class EmailValidatorImpl implements Validator {
    @Override
    public void validate(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            Map<String,String> parameter = new HashMap<>(1,1);
            parameter.put("email", user.getEmail());
            if(userDao.getByParameter(parameter).isEmpty()){
                throw new ServiceException("Email do not exist. ");
            }
        }catch (DaoException e){
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }
}
