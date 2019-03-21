package com.alexshay.buber.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LocaleBundle {
    private ResourceBundle localeResourceBundle;
    private static LocaleBundle instance;
    private static Lock lock = new ReentrantLock();

    private LocaleBundle() {
    }

    public static LocaleBundle getInstance() {
        lock.lock();
        try {
            if (instance == null) {
                instance = new LocaleBundle();
            }
        } finally {
            lock.unlock();
        }
        return instance;
    }
    public void setLocaleResourceBundle(String locale){
        localeResourceBundle = ResourceBundle.getBundle("locale", new Locale(locale,"RU"));
    }

    public ResourceBundle getLocaleResourceBundle(){
        return localeResourceBundle !=null?
                localeResourceBundle:
                ResourceBundle.getBundle("locale", new Locale("en","RU"));
    }
}
