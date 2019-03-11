package com.alexshay.buber.service.impl;

import com.alexshay.buber.dao.*;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.domain.*;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.email.MailGenerator;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.validation.ValidationFactory;
import com.alexshay.buber.validation.ValidatorUser;
import com.alexshay.buber.validation.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User signUp(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        ValidatorUser validator = ValidationFactory.getInstance().getUserValidator();
        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            String password = encryptPassword(user.getPassword() + user.getLogin());
            validator.validate(user);
            user.setPassword(password);
            return userDao.persist(user);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }  catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to encrypt password. ", e);
        }
    }

    @Override
    public User signIn(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        ValidatorUser authenticationValidator = ValidationFactory.getInstance().getAuthenticationValidator();

        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            String login = user.getLogin();
            String password = user.getPassword();
            if(password.length() != 64){
                user.setPassword(encryptPassword(password+login));
            }
            authenticationValidator.validate(user);
            User userValid = userDao.getByLogin(login);
            userValid.setStatus(UserStatus.ONLINE);
            userDao.update(userValid);
            return userValid;
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to encrypt password. ", e);
        }

    }

    @Override
    public List<User> getAllClient() throws ServiceException {
        return getUsers("client");
    }

    @Override
    public List<User> getAllDriver() throws ServiceException {
        return getUsers("driver");
    }

    @Override
    public List<User> getAllAdmin() throws ServiceException {
        return getUsers("admin");
    }


    private List<User> getUsers(String role) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);

        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            return userDao.getByRole(role);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }

    @Override
    public void deleteUser(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);

        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            userDao.delete(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }

    @Override
    public User getUserById(int id) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            User user = userDao.getByPK(id);
            if (user.getRole().equals(Role.CLIENT)) {
                UserBonusDao userBonusDao = (UserBonusDao) daoFactory.getDao(UserBonus.class);
                GenericDao<Bonus, Integer> bonusDao = daoFactory.getDao(Bonus.class);
                Map<String,String> parameter = new HashMap<>(1,1);
                parameter.put("user_id", user.getId().toString());
                List<UserBonus> userBonuses = userBonusDao.getByParameter(parameter);
                if (userBonuses != null) {
                    List<Bonus> bonuses = new ArrayList<>();
                    for(UserBonus userBonus : userBonuses){
                        bonuses.add(bonusDao.getByPK(userBonus.getBonusId()));
                    }
                    user.setBonuses(bonuses);
                }
            }

            return user;
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            User userValid = userDao.getByPK(user.getId());
            User checkUser = User.builder().build();
            if(!userValid.getLogin().equals(user.getLogin())){
                checkUser.setLogin(user.getLogin());
            }
            if(!userValid.getEmail().equals(user.getEmail())){
                checkUser.setEmail(user.getEmail());
            }
            if(!userValid.getPhone().equals(user.getPhone())){
                checkUser.setPhone(user.getPhone());
            }
            if(!userValid.getFirstName().equals(user.getFirstName())){
                checkUser.setFirstName(user.getFirstName());
            }
            if(!checkUser.equals(User.builder().build())){
                ValidatorUser validator = new UserValidatorImpl();
                validator.validate(checkUser);
            }
            if(!user.getBonuses().isEmpty()){
                GenericDao<UserBonus,Integer> userBonusDao = daoFactory.getDao(UserBonus.class);
                UserBonus userBonus = UserBonus.builder().
                        userId(user.getId()).
                        bonusId(user.getBonuses().get(0).getId()).
                        build();
                userBonusDao.persist(userBonus);
            }



            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }

    @Override
    public void deleteBonus(Integer userId, Integer bonusId) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);

        try {
            UserBonusDao userBonusDao = (UserBonusDao) daoFactory.getDao(UserBonus.class);
            Map<String,String> parameters = new HashMap<>(2,1);
            parameters.put("bonus_id", bonusId.toString());
            parameters.put("user_id", userId.toString());

            List<UserBonus> userBonuses = userBonusDao.getByParameter(parameters);
            if(!userBonuses.isEmpty()){
                userBonusDao.delete(userBonuses.get(0));
            }

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        }
    }
    @Override
    public void sendResetPasswordKey(User user) throws ServiceException {
        ValidatorUser validator = ValidationFactory.getInstance().getEmailValidator();
        validator.validate(user);
        MailGenerator mailGenerator = new MailGenerator();
        String repasswordKey = generateRandomString();
        String message = "This is your enter key \n" + repasswordKey;
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            user = userDao.getByEmail(user.getEmail());
            user.setRepasswordKey(encryptPassword(repasswordKey));
            userDao.update(user);
            mailGenerator.sendMessage("Reset password", message, Arrays.asList(user.getEmail()));

        } catch (DaoException  e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to use Algorithm for password", e);
        }
    }
    @Override
    public void checkRepasswordKey(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        ValidatorUser validatorRepassword = ValidationFactory.getInstance().getRepasswordKeyValidator();
        try {
            user.setRepasswordKey(encryptPassword(user.getRepasswordKey()));
            validatorRepassword.validate(user);
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            Map<String,String> parameter = new HashMap<>();
            user = userDao.getByEmail(user.getEmail());
            user.setRepasswordKey(null);
            userDao.update(user);

        } catch (DaoException  e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to use Algorithm for password", e);
        }
    }
    @Override
    public void resetPassword(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            User userValid = userDao.getByEmail(user.getEmail());
            userValid.setPassword(encryptPassword(user.getPassword() + userValid.getLogin()));
            userDao.update(userValid);
        } catch (DaoException  e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to use Algorithm for password", e);
        }
    }



}