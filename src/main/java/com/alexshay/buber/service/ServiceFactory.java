package com.alexshay.buber.service;

import com.alexshay.buber.service.impl.BonusServiceImpl;
import com.alexshay.buber.service.impl.TripOrderServiceImpl;
import com.alexshay.buber.service.impl.UserServiceImpl;

/**
 * Service factory
 */
public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return new UserServiceImpl();
    }
    public BonusService getBonusService(){return new BonusServiceImpl();}
    public TripOrderService getTripOrderService(){return new TripOrderServiceImpl();}
}
