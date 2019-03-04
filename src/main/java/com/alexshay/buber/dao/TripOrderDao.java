package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.TripOrder;

import java.util.List;
import java.util.Map;

public interface TripOrderDao extends GenericDao<TripOrder, Integer> {
    List<TripOrder> getByParameters(Map<String, String> parameters) throws DaoException;
}
