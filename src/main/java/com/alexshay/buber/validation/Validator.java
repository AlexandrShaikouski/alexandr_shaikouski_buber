package com.alexshay.buber.validation;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;

public interface Validator {
    void validate(User user) throws ServiceException;
}
