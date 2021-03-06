package com.alexshay.buber.validation;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;

public interface ValidatorUser {
    void validate(User user) throws ServiceException;
}
