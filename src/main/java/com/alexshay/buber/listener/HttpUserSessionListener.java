package com.alexshay.buber.listener;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.domain.UserStatus;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class HttpUserSessionListener implements HttpSessionListener {
    private static final Logger LOGGER = LogManager.getLogger(ContextListener.class);
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        UserService userService = ServiceFactory.getInstance().getUserService();
        User user = (User)httpSessionEvent.getSession().getAttribute("user");
        if(user != null){
            user.setStatus(UserStatus.OFF_LINE);
            try {
                userService.updateUser(user);
            } catch (ServiceException e) {
                LOGGER.error(e);
            }
        }
    }
}
