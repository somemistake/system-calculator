package com.somemistake.calculator.factory;

import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.exception.ApplicationException;

public class BeanFactory {

    private final Config config;

    public BeanFactory(Config config) {
        this.config = config;
    }

    public <T> T createObject(Class<T> target) {
        try {
            return target.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new ApplicationException(
                    String.format("Cannot create instance of %s class", target.getName()));
        }
    }

}
