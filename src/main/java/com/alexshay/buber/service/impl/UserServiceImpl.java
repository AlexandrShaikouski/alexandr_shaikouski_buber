package com.alexshay.buber.service.impl;

import com.alexshay.buber.dao.*;
import com.alexshay.buber.dao.exception.DaoException;
import com.alexshay.buber.dao.exception.PersistException;
import com.alexshay.buber.domain.Bonus;
import com.alexshay.buber.domain.Role;
import com.alexshay.buber.domain.User;
import com.alexshay.buber.domain.UserBonus;
import com.alexshay.buber.service.UserService;
import com.alexshay.buber.service.email.MailGenerator;
import com.alexshay.buber.service.exception.ServiceException;
import com.alexshay.buber.validation.Validator;
import com.alexshay.buber.validation.impl.AuthenticationValidator;
import com.alexshay.buber.validation.impl.EmailValidatorImpl;
import com.alexshay.buber.validation.impl.RepasswordKeyValidator;
import com.alexshay.buber.validation.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Example of user service implementation
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User signUp(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        Validator validator = new UserValidatorImpl();
        try {
            GenericDao<User, Integer> userDao = daoFactory.getDao(User.class);
            String password = encryptPassword(user.getPassword() + user.getLogin());
            validator.validate(user);
            user.setPassword(password);
            return userDao.persist(user);

        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        } catch (PersistException e) {
            throw new ServiceException("Failed to save user. ", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to encrypt password. ", e);
        }
    }

    @Override
    public User signIn(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        Validator authenticationValidator = new AuthenticationValidator();

        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            String login = user.getLogin();
            String password = user.getPassword();
            user.setPassword(encryptPassword(password+login));
            authenticationValidator.validate(user);
            User userValid = userDao.getByLogin(login);

            return userValid;
        } catch (DaoException e) {
            throw new ServiceException("Failed to get user DAO. ", e);

        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to encrypt password. ", e);
        }

    }

    @Override
    public List<User> getAllClient() throws ServiceException {
        return getUsers("3");
    }

    @Override
    public List<User> getAllDriver() throws ServiceException {
        return getUsers("2");
    }

    @Override
    public List<User> getAllAdmin() throws ServiceException {
        return getUsers("1");
    }


    private List<User> getUsers(String role) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);

        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            Map<String, String> parameter = new HashMap<>(1, 1);
            parameter.put("role_id", role);
            return userDao.getByParameter(parameter);
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
        } catch (PersistException e) {
            throw new ServiceException("Failed to delete user. ", e);
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
                    List<Bonus> bonuses = userBonuses.stream().map(s -> {
                        try {
                            return bonusDao.getByPK(s.getBonusId());
                        } catch (DaoException e) {
                            LOGGER.error(e);
                        }
                        return null;
                    }).collect(Collectors.toList());
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
                Validator validator = new UserValidatorImpl();
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
        } catch (PersistException e) {
            throw new ServiceException("Failed to update user. ", e);
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
        } catch (PersistException e) {
            throw new ServiceException("Failed to update user. ", e);
        }
    }
    @Override
    public void sendResetPasswordKey(User user) throws ServiceException {
        Validator validator = new EmailValidatorImpl();
        validator.validate(user);
        MailGenerator mailGenerator = new MailGenerator();
        String repasswordKey = generateRandomString();
        String message = "This is your enter key \n" + repasswordKey;
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        try {
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            Map<String,String> parameter = new HashMap<>();
            parameter.put("email", user.getEmail());
            List<User> userList = userDao.getByParameter(parameter);
            user = userList.get(0);
            user.setRepasswordKey(encryptPassword(repasswordKey));
            userDao.update(user);
            mailGenerator.sendMessage("Reset password", message, Arrays.asList(user.getEmail()));

        } catch (DaoException | PersistException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to use Algorithm for password", e);
        }
    }
    @Override
    public void checkRepasswordKey(User user) throws ServiceException {
        DaoFactory daoFactory = FactoryProducer.getDaoFactory(DaoFactoryType.JDBC);
        Validator validatorRepassword = new RepasswordKeyValidator();
        try {
            user.setRepasswordKey(encryptPassword(user.getRepasswordKey()));
            validatorRepassword.validate(user);
            UserDao userDao = (UserDao) daoFactory.getDao(User.class);
            Map<String,String> parameter = new HashMap<>();
            parameter.put("email",user.getEmail());
            List<User> userList;
            userList = userDao.getByParameter(parameter);
            user = userList.get(0);
            user.setRepasswordKey(null);
            userDao.update(user);

        } catch (DaoException | PersistException e) {
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
            Map<String,String> parameter = new HashMap<>();
            parameter.put("email",user.getEmail());
            List<User> userList = userDao.getByParameter(parameter);
            User userValid = userList.get(0);
            userValid.setPassword(encryptPassword(user.getPassword() + userValid.getLogin()));
            userDao.update(userValid);
        } catch (DaoException | PersistException e) {
            throw new ServiceException("Failed to get user DAO. ", e);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException("Failed to use Algorithm for password", e);
        }
    }
}