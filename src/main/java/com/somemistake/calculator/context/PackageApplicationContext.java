package com.somemistake.calculator.context;

import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.factory.BeanFactory;

public class PackageApplicationContext extends ApplicationContext {

    public PackageApplicationContext(Config config, BeanFactory beanFactory) {
        super(config, beanFactory);
    }

    @Override
    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<?> implementationClass = type;

        if (implementationClass.isInterface()) {
            implementationClass = config.getImplementationClass(type);
        }

        T target = (T) beanFactory.createObject(implementationClass);

        process(target);

        target = configure(target, implementationClass);

        return target;
    }
}
