package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao extends GenericDao<User, Integer>{
    User getByLogin(String login) throws DaoException;
    User getByEmail(String email) throws DaoException;
    User getByPhone(String phone) throws DaoException;
    List<User> getByRole(String role) throws DaoException;
}
