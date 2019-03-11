package com.alexshay.buber.validation;


import com.alexshay.buber.validation.impl.*;

public class ValidationFactory {
    private static ValidationFactory instance = new ValidationFactory();

    public static ValidationFactory getInstance() {
        return instance;
    }

    public ValidatorUser getAuthenticationValidator(){
        return new AuthenticationValidator();
    }
    public ValidatorUser getEmailValidator(){
        return new EmailValidatorImpl();
    }
    public ValidatorUser getRepasswordKeyValidator(){
        return new RepasswordKeyValidator();
    }
    public ValidatorUser getUserValidator(){
        return new UserValidatorImpl();
    }
    public ValidatorTripOrder getTripOrderValidator(){
        return new TripOrderValidator();
    }
}
