package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.Bonus;

import java.util.List;

public interface BonusDao extends GenericDao<Bonus, Integer> {
    List<Bonus> getByClientId(int clientId) throws DaoException;
}
