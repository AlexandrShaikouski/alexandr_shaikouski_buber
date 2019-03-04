package com.alexshay.buber.validation.impl;

import com.alexshay.buber.dao.DaoFactory;
import com.alexshay.buber.dao.DaoFactoryType;
import com.alexshay.buber.dao.FactoryProducer;
import com.alexshay.buber.dao.UserDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class RepasswordKeyValidator implements Validator {
    @Override
    public void validate(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            Map<String,String> parameter = new HashMap<>(1,1);
            parameter.put("email", user.getEmail());
            User validUser = userDao.getByParameter(parameter).get(0);
            if(!validUser.getRepasswordKey().equals(user.getRepasswordKey())){
                throw new ServiceException("Uncorrect key. ");
            }
        }catch (DaoException e){
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }
}
