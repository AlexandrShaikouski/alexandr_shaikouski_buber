package com.alexshay.buber.validation;


import com.alexshay.buber.validation.impl.AuthenticationValidator;
import com.alexshay.buber.validation.impl.EmailValidatorImpl;
import com.alexshay.buber.validation.impl.RepasswordKeyValidator;
import com.alexshay.buber.validation.impl.UserValidatorImpl;

public class ValidationFactory {
    private static ValidationFactory instance = new ValidationFactory();

    public static ValidationFactory getInstance() {
        return instance;
    }

    public Validator getAuthenticationValidator(){
        return new AuthenticationValidator();
    }
    public Validator getEmailValidator(){
        return new EmailValidatorImpl();
    }
    public Validator getRepasswordKeyValidator(){
        return new RepasswordKeyValidator();
    }
    public Validator getUserValidator(){
        return new UserValidatorImpl();
    }
}
