package com.somemistake.calculator.factory;

import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.model.exception.CalculatorException;

public class BeanFactory {

    private final Config config;

    public BeanFactory(Config config) {
        this.config = config;
    }

    public <T> T createObject(Class<T> target) {
        try {
            return target.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new CalculatorException(
                    String.format("Cannot create instance of %s class", target.getName()));
        }
    }

}
