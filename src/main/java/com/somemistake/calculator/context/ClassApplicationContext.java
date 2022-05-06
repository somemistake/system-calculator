package com.somemistake.calculator.context;

import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.factory.BeanFactory;
import com.somemistake.calculator.exception.ApplicationException;

public class ClassApplicationContext extends ApplicationContext {

    public ClassApplicationContext(Config config, BeanFactory beanFactory) {
        super(config, beanFactory);
    }

    @Override
    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        T target = config.getBean(type);

        if (target == null && !type.isInterface()) {
            target = beanFactory.createObject(type);
        }

        if (target == null) {
            throw new ApplicationException("All interfaces should have implementations in configuration class");
        }

        Class<?> targetClass = target.getClass();

        process(target);

        target = configure(target, targetClass);

        return target;
    }

}
