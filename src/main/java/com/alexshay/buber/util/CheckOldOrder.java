package com.alexshay.buber.util;

import com.alexshay.buber.dao.DaoFactory;
import com.alexshay.buber.dao.DaoFactoryType;
import com.alexshay.buber.dao.FactoryProducer;
import com.alexshay.buber.dao.TripOrderDao;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.OrderStatus;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.listener.ContextListener;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.service.impl.TripOrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CheckOldOrder implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);
    private static final int TIME_OUT_ORDER = 60*1000;
    private static final int TIME_OUT_CHECK_ORDER = 60;
    private TripOrderService tripOrderService;

    public CheckOldOrder() {
        tripOrderService = ServiceFactory.getInstance().getTripOrderService();
    }

    @Override
    public void run() {
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(TIME_OUT_CHECK_ORDER);
                List<TripOrder> tripOrders = tripOrderService.getByStausWaiting();
                if (!tripOrders.isEmpty()) {
                    tripOrders = tripOrders.stream().
                            filter(s -> s.getDateCreate().getTime() + TIME_OUT_ORDER  < new Date().getTime()).
                            collect(Collectors.toList());
                    for (TripOrder tripOrder : tripOrders) {
                        tripOrderService.deleteTripOrder(tripOrder);
                    }
                }

            }
        } catch (InterruptedException | ServiceException e) {
            LOGGER.error(e);
        }
    }
}
