package com.alexshay.buber.listener;

import com.alexshay.buber.dao.exception.ConnectionPoolException;
import com.alexshay.buber.dao.impl.ConnectionPoolImpl;
import com.alexshay.buber.util.CheckOldOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Thread thread = new Thread(new CheckOldOrder());
        thread.setDaemon(true);
        thread.start();
        LOGGER.info("Create thread daemon");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPoolImpl.getInstance().destroyPool();
        } catch (ConnectionPoolException e) {
            LOGGER.error(e);
        }
    }
}