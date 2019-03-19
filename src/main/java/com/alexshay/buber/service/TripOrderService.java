package com.alexshay.buber.service;

import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.service.exception.ServiceException;

import java.util.List;

public interface TripOrderService {
    TripOrder getById(TripOrder tripOrder) throws ServiceException;
    List<TripOrder> getAll() throws ServiceException;
    TripOrder createTripOrder(TripOrder tripOrder) throws ServiceException;
    void deleteTripOrder(TripOrder tripOrder) throws ServiceException;
    void updateTripOrder(TripOrder tripOrder) throws ServiceException;
    List<TripOrder> getByClientId(Integer clientId) throws ServiceException;
    List<TripOrder> getByDriverId(Integer driverId) throws ServiceException;
    List<TripOrder> getByStaus(String status) throws ServiceException;
}
