package com.alexshay.buber.validation.impl;

import com.alexshay.buber.dao.DaoFactory;
import com.alexshay.buber.dao.DaoFactoryType;
import com.alexshay.buber.dao.FactoryProducer;
import com.alexshay.buber.dao.UserDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.util.LocaleBundle;
import com.alexshay.buber.validation.ValidatorUser;

import java.util.ResourceBundle;

public class RepasswordKeyValidator implements ValidatorUser {
    @Override
    public void validate(User user) throws ServiceException {
        ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            User validUser = userDao.getByEmail(user.getEmail());
            if(!validUser.getRepasswordKey().equals(user.getRepasswordKey())){
                throw new ServiceException(resourceBundle.getString("all.error.uncrepasskey"));
            }
        }catch (DaoException e){
            throw new ServiceException(resourceBundle.getString("all.error.failgetdao"), e);
        }
    }
}
