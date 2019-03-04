package com.alexshay.buber.service;

import com.alexshay.buber.domain.Bonus;
import com.alexshay.buber.service.exception.ServiceException;

import java.util.List;

public interface BonusService {
    List<Bonus> getAll() throws ServiceException;
    Bonus getById(int id) throws ServiceException;
    void createBonus(Bonus bonus) throws ServiceException;
    void deleteBonus(Bonus bonus) throws ServiceException;
}
