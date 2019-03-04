package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao extends GenericDao<User, Integer>{
    List<User> getByParameter(Map<String, String> parameters) throws DaoException;
    User getByLogin(String login) throws DaoException;
}
