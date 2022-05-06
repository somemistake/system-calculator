package com.somemistake.calculator.context;

import com.somemistake.calculator.annotations.Singleton;
import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.configurator.ProxyConfigurator;
import com.somemistake.calculator.factory.BeanFactory;
import com.somemistake.calculator.exception.ApplicationException;
import com.somemistake.calculator.processor.Processor;
import com.somemistake.calculator.publisher.EventPublisher;
import com.somemistake.calculator.publisher.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.somemistake.calculator.util.EventConstants.CONTEXT_REFRESHED;
import static com.somemistake.calculator.util.EventConstants.CONTEXT_STARTED;

public abstract class ApplicationContext {

    protected BeanFactory beanFactory;
    protected Config config;
    protected final Map<Class, Object> cache = new ConcurrentHashMap<>();
    protected final List<Processor> processors;
    protected final List<ProxyConfigurator> configurators;
    protected Publisher publisher;

    public ApplicationContext(Config config, BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        this.config = config;
        this.publisher = new EventPublisher(this);
        this.processors = new ArrayList<>();
        try {
            for (Class<? extends Processor> processorClass : config.getPackageInfo().getSubTypesOf(Processor.class)) {
                this.processors.add(processorClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new ApplicationException("Cannot create instance of processor class");
        }
        this.configurators = new ArrayList<>();
        try {
            for (Class<? extends ProxyConfigurator> configuratorClass : config.getPackageInfo().getSubTypesOf(ProxyConfigurator.class)) {
                this.configurators.add(configuratorClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new ApplicationException("Cannot create instance of configurator class");
        }
        this.publisher.publishEvent(CONTEXT_STARTED);
        createSingletons();
        this.publisher.publishEvent(CONTEXT_REFRESHED);
    }

    public abstract <T> T getObject(Class<T> type);

    protected <T> void process(T t) {
        processors.forEach(processor -> processor.process(t, this));
    }

    protected <T> T configure(T target, Class<?> targetClass) {
        for (ProxyConfigurator configurator : configurators) {
            target = (T) configurator.configure(target, targetClass);
        }
        return target;
    }

    private void createSingletons() {
        for (Class<?> packageClass : config.getPackageInfo().getSubTypesOf(Object.class)) {
            if (packageClass.isAnnotationPresent(Singleton.class)) {
                Class<?>[] interfaces = packageClass.getInterfaces();

                Class<?> targetClass = packageClass;

                if (interfaces.length > 1) {
                    throw new ApplicationException(
                            String.format("%s has more than 1 interfaces", packageClass.getCanonicalName()));
                } else if (interfaces.length == 1) {
                    targetClass = interfaces[0];
                }

                cache.put(targetClass, this.getObject(targetClass));
            }
        }
    }

    public Config getConfig() {
        return config;
    }
}
