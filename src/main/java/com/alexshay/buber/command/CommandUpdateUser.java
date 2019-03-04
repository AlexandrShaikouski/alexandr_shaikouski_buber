package com.alexshay.buber.command;

import com.alexshay.buber.domain.*;
import com.alexshay.buber.dto.ResponseContent;
import com.alexshay.buber.service.BonusService;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandUpdateUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {
        ResponseContent responseContent = new ResponseContent();
        UserService userService = ServiceFactory.getInstance().getUserService();
        String servletPath = request.getRequestURL().toString();
        String bonusIdStr = request.getParameter("bonus_id");
        String role = request.getParameter("role");
        request.setAttribute("role",role);
        int id = Integer.parseInt(request.getParameter("id"));



        List<Bonus> bonuses = new ArrayList<>();
        User user = User.builder().build();
        try {
            if(role.equals("client")){
                int bonusId = bonusId = Integer.parseInt(bonusIdStr);
                if (bonusId != -1) {
                    BonusService bonusService = ServiceFactory.getInstance().getBonusService();
                    Bonus bonus = bonusService.getById(bonusId);
                    bonuses.add(bonus);
                }
            }

            user = userService.getUserById(id);
            User userCheck = userService.getUserById(id);
            userCheck.setBonuses(bonuses);
            userCheck = addUpdateUserFields(userCheck, request);


            userService.updateUser(userCheck);
            responseContent.setRouter(new Router(servletPath + "?command=success_page", Router.Type.REDIRECT));
        } catch (ServiceException e) {
            request.setAttribute("user", user);
            request.setAttribute("message", e.getMessage());
            responseContent.setRouter(new Router("WEB-INF/jsp/admin/info-user.jsp", Router.Type.FORWARD));
        } finally {
            return responseContent;
        }


    }

    private Date getStatusBan(User user, HttpServletRequest request) throws ServiceException {
        Date date = user.getStatusBan();

        String banTime = request.getParameter("ban_time");
        String countTimeBanStr = request.getParameter("count_time_ban");
        int countTimeBan = 0;
        if (!countTimeBanStr.equals("")) {
            countTimeBan = Integer.parseInt(countTimeBanStr);
        }
        if (countTimeBan > 0) {
            switch (banTime) {
                case "hour":
                    return date == null ? DateUtils.addHours(new Date(), countTimeBan) : DateUtils.addMonths(date, countTimeBan);
                case "day":
                    return date == null ? DateUtils.addDays(new Date(), countTimeBan) : DateUtils.addDays(date, countTimeBan);
                case "week":
                    return date == null ? DateUtils.addWeeks(new Date(), countTimeBan) : DateUtils.addWeeks(date, countTimeBan);
                case "month":
                    return date == null ? DateUtils.addMonths(new Date(), countTimeBan) : DateUtils.addMonths(date, countTimeBan);
                case "year":
                    return date == null ? DateUtils.addYears(new Date(), countTimeBan) : DateUtils.addYears(date, countTimeBan);
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            throw new ServiceException("Add count for ban. ");
        }
    }

    private User addUpdateUserFields(User user, HttpServletRequest request) throws ServiceException {
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String firstName = request.getParameter("name");
        String location = request.getParameter("location");
        String status = request.getParameter("user_status");
        String banTime = request.getParameter("ban_time");
        if (login != null) {
            user.setLogin(login);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (phone != null) {
            user.setPhone(phone);
        }
        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (location != null) {
            user.setLocation(location);
        }
        if (status != null) {
            user.setStatus(UserStatus.fromValue(status));
        }
        if (!banTime.equals("none")) {
            user.setStatusBan(getStatusBan(user, request));
        }


        return user;
    }
}