package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.UserBonus;

import java.util.List;
import java.util.Map;

public interface UserBonusDao extends GenericDao<UserBonus, Integer> {
    List<UserBonus> getByParameter(Map<String, String> parameters) throws DaoException;
}