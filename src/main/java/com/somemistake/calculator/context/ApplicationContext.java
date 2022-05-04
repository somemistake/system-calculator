package com.somemistake.calculator.context;

import com.somemistake.calculator.annotations.Singleton;
import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.factory.BeanFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private BeanFactory beanFactory;
    private Config config;
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }

        Class<?> implClass = type;

        if (implClass.isInterface()) {
            implClass = config.getImplClass(type);
        }

        T target = (T) beanFactory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, target);
        }

        return target;

    }

    public Config getConfig() {
        return config;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
