package com.alexshay.buber.dao;

import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.TripOrder;

import java.util.List;

public interface TripOrderDao extends GenericDao<TripOrder, Integer> {
    List<TripOrder> getByClientId(Integer clientId) throws DaoException;
    List<TripOrder> getByDriverId(Integer driverId) throws DaoException;
    List<TripOrder> getByStatus(String status) throws DaoException;
}
