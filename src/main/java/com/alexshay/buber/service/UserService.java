package com.alexshay.buber.service;

import com.alexshay.buber.domain.User;
import com.alexshay.buber.service.exception.ServiceException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Example of user service
 */
public interface UserService {

    /**
     * Sign up user
     * @param user - User
     * @return - saved user
     * @throws ServiceException should be clarify
     */
    User signUp(User user) throws ServiceException;
    User signIn(User user) throws ServiceException;
    List<User> getAllClient() throws ServiceException;
    List<User> getAllDriver() throws ServiceException;
    List<User> getAllAdmin() throws ServiceException;
    void deleteUser(User user) throws ServiceException;
    User getUserById(int id) throws ServiceException;
    void updateUser(User user) throws ServiceException;
    void deleteBonus(Integer userId, Integer bonusId) throws ServiceException;
    void sendResetPasswordKey(User user) throws ServiceException;
    void checkRepasswordKey(User user) throws ServiceException;
    void resetPassword(User user) throws ServiceException;

    default String encryptPassword(String password) throws NoSuchAlgorithmException {
        byte[] hexHash = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));
        return IntStream.range(0, hexHash.length).mapToObj(i -> Integer.toHexString(0xff & hexHash[i]))
                .map(s -> (s.length() == 1) ? "0" + s : s).collect(Collectors.joining());
    }
    default String generateRandomString(){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] chars = str.toCharArray();
        Random random = new SecureRandom();
        String key = "";
        for(int i = 0; i < 10; i++){
            key += chars[random.nextInt(chars.length)];
        }
        return key;
    }

}
