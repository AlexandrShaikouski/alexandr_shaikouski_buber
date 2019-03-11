package com.alexshay.buber.validation;

import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.service.exception.ServiceException;

public interface ValidatorTripOrder {
    void validate(TripOrder tripOrder) throws ServiceException;
}
