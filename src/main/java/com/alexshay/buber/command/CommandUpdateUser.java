package com.alexshay.buber.command;

import com.alexshay.buber.domain.*;
import com.alexshay.buber.util.LocaleBundle;
import com.alexshay.buber.util.ResponseContent;
import com.alexshay.buber.service.BonusService;
import com.alexshay.buber.service.ServiceFactory;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.exception.ServiceException;
import org.apache.commons.lang3.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class CommandUpdateUser implements Command {
    @Override
    public ResponseContent execute(HttpServletRequest request) {

        ResponseContent responseContent = new ResponseContent();
        BonusService bonusService = ServiceFactory.getInstance().getBonusService();
        UserService userService = ServiceFactory.getInstance().getUserService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String servletPath = request.getRequestURL().toString();
        String bonusIdStr = request.getParameter("bonus_id");
        String role = request.getParameter("role");
        request.setAttribute("role", role);
        int id = Integer.parseInt(request.getParameter("id"));
        List<Bonus> listBonuses = Collections.emptyList();

        List<Bonus> bonuses = new ArrayList<>();
        User userError = User.builder().build();
        try {
            listBonuses = bonusService.getAll();
            if ("client".equals(role)) {
                int bonusId = Integer.parseInt(bonusIdStr);
                if (bonusId != -1) {
                    Bonus bonus = bonusService.getById(bonusId);
                    bonuses.add(bonus);
                }
            }

            userError = userService.getUserById(id);
            User userCheck = userService.getUserById(id);
            userCheck.setBonuses(bonuses);
            userCheck = addUpdateUserFields(userCheck, request);


            userService.updateUser(userCheck);
            if (role == null) {
                session.setAttribute("user", userService.getUserById(user.getId()));
            }
            responseContent.setRouter(new Router(servletPath + "?command=success_page", Router.Type.REDIRECT));
        } catch (ServiceException e) {


            request.setAttribute("user", userError);
            request.setAttribute("listBonuses", listBonuses);
            request.setAttribute("message", e.getMessage());
            switch (user.getRole()) {
                case CLIENT:
                    responseContent.setRouter(new Router("WEB-INF/jsp/client/info-client.jsp", Router.Type.FORWARD));
                    break;
                case DRIVER:
                    responseContent.setRouter(new Router("WEB-INF/jsp/driver/info-driver.jsp", Router.Type.FORWARD));
                    break;
                case ADMIN:
                    responseContent.setRouter(new Router("WEB-INF/jsp/admin/info-user.jsp", Router.Type.FORWARD));
                    break;
            }

        }
        return responseContent;


    }

    private Date getStatusBanInner(User user, HttpServletRequest request) throws ServiceException {
        Date date = user.getStatusBan();

        String banTime = request.getParameter("ban_time");
        String countTimeBanStr = request.getParameter("count_time_ban");
        int countTimeBan = 0;
        if (!"".equals(countTimeBanStr)) {
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
            ResourceBundle resourceBundle = LocaleBundle.getInstance().getLocaleResourceBundle();
            throw new ServiceException(resourceBundle.getString("admin.infouser.countbanerr"));
        }
    }

    private User addUpdateUserFields(User user, HttpServletRequest request) throws ServiceException {
        String login = request.getParameter("login");
        String email = request.getParameter("mail");
        String phone = request.getParameter("phone");
        String firstName = request.getParameter("name");
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
        if (status != null) {
            user.setStatus(UserStatus.fromValue(status));
        }
        if (banTime != null && !"none".equals(banTime)) {
            user.setStatusBan(getStatusBanInner(user, request));
        }


        return user;
    }
}
