package com.alexshay.buber.util;

import com.alexshay.buber.domain.OrderStatus;
import com.alexshay.buber.domain.TripOrder;
import com.alexshay.buber.listener.ContextListener;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.TripOrderService;
import com.alexshay.buber.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CheckOldOrder implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);
    private static final int TIME_OUT_ORDER_WAITING = 60*1000;
    private static final int TIME_OUT_ORDER_PANDING = 3600*1000;
    private static final int TIME_OUT_ORDER_IN_PROGRESS = 5400*1000;
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
                List<TripOrder> tripOrdersWaiting = tripOrderService.getByStaus(OrderStatus.WAITING.value());
                List<TripOrder> tripOrdersPanding = tripOrderService.getByStaus(OrderStatus.PENDING.value());
                List<TripOrder> tripOrdersInProgress = tripOrderService.getByStaus(OrderStatus.IN_PROGRESS.value());
                checkOrder(tripOrdersWaiting,TIME_OUT_ORDER_WAITING);
                checkOrder(tripOrdersPanding,TIME_OUT_ORDER_PANDING);
                checkOrder(tripOrdersInProgress,TIME_OUT_ORDER_IN_PROGRESS);

            }
        } catch (InterruptedException | ServiceException e) {
            LOGGER.error(e);
        }
    }
    private void checkOrder(List<TripOrder> tripOrders, int timeOut) throws ServiceException {
        if(!tripOrders.isEmpty()){
            tripOrders = tripOrders.stream().
                    filter(s -> s.getDateCreate().getTime() + timeOut  < new Date().getTime()).
                    collect(Collectors.toList());
            for (TripOrder tripOrder : tripOrders) {
                tripOrderService.deleteTripOrder(tripOrder);
            }
        }
    }
}
