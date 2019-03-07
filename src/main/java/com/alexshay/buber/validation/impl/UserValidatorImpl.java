package com.alexshay.buber.validation.impl;

import com.alexshay.buber.dao.*;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.validation.Validator;

import java.util.HashMap;
import java.util.Map;

public class UserValidatorImpl implements Validator {
    private static final String LOGIN_REGEX = "^[a-zA-Z0-9]{2,45}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9!@#$%^&*]{6,45}$";
    private static final String EMAIL_REGEX = "^[-a-z0-9!#$%&'*+=?^_`{|}~]+(?:\\.[-a-z0-9!#$%&'*+=?^_`{|}~]+)*@(?:[a-z0-9]([-a-z0-9]{0,61}[a-z0-9])?\\.)*(?:aero|arpa|asia|biz|cat|com|coop|edu|gov|info|int|jobs|mil|mobi|museum|name|net|org|pro|tel|travel|[a-z][a-z])$";
    private static final String PHONE_REGEX = "^(\\+375)((25)|(29)|(33)|(44))[0-9]{7}$";
    private static final String NAME_REGEX = "^[a-zA-Zа-яА-Я]{2,45}$";

    @Override
    public void validate(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        String validString = "";
        int counter = 0;

        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            if (user.getLogin() != null && !user.getLogin().matches(LOGIN_REGEX)) {
                validString += validString.equals("") ?
                        "Login" : ", login";
            }
            if (user.getPassword() != null && !user.getPassword().matches(PASSWORD_REGEX)) {
                validString += validString.equals("") ?
                        "Password" : ", password";
            }
            if (user.getEmail() != null && !user.getEmail().matches(EMAIL_REGEX)) {
                validString += validString.equals("") ?
                        "Email" : ", email";
            }
            if (user.getPhone() != null && !user.getPhone().matches(PHONE_REGEX)) {
                validString += validString.equals("") ?
                        "Phone" : ", phone";
            }
            if (user.getFirstName() != null && !user.getFirstName().matches(NAME_REGEX)) {
                validString += validString.equals("") ?
                        "First name" : ", first name";
            }

            if (!validString.equals("")) {
                validString += ": uncorrect data. ";
            }
            if (user.getLogin() != null && userDao.getByLogin(user.getLogin()) != null) {
                validString += validString.equals("") || counter == 0?
                        "Login" : ", login";
                counter++;
            }
            if (user.getEmail() != null && userDao.getByEmail(user.getEmail()) != null) {
                validString += validString.equals("") || counter == 0 ?
                        "Email" : ", email";
                counter++;
            }
            if (user.getPhone() != null && userDao.getByPhone(user.getPhone()) != null) {
                validString += validString.equals("") || counter == 0 ?
                        "Phone" : ", phone";
                counter++;
            }
            if (counter != 0) {
                throw new ServiceException(validString + " already exist!");
            }else if(!validString.equals("")){
                throw new ServiceException(validString);
            }
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }

    }
}