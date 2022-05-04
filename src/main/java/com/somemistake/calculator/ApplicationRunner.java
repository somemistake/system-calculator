package com.somemistake.calculator;

import com.somemistake.calculator.annotations.Singleton;
import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.config.JavaConfig;
import com.somemistake.calculator.context.ApplicationContext;
import com.somemistake.calculator.event.ContextClosed;
import com.somemistake.calculator.event.ContextRefreshed;
import com.somemistake.calculator.event.ContextStarted;
import com.somemistake.calculator.event.ContextStopped;
import com.somemistake.calculator.factory.BeanFactory;
import com.somemistake.calculator.listener.EventListener;
import com.somemistake.calculator.model.exception.CalculatorException;

import java.util.ArrayList;
import java.util.List;

public class ApplicationRunner {

    private static final List<EventListener<?>> listeners = new ArrayList<>();

    public static ApplicationContext run(String packageName, Class... values) {
        Config config = new JavaConfig(packageName, values);
        ApplicationContext context = new ApplicationContext(config);
        BeanFactory factory = new BeanFactory(context);
        context.setBeanFactory(factory);
        createListeners(config);
        contextStarted(context);
        createSingletons(config, context);
        contextRefreshed(context);
        return context;
    }

    private static void contextStarted(ApplicationContext context) {
        listeners.stream().filter(listener -> listener.getTypeClass().equals(ContextStarted.class))
                .forEach(listener -> listener.action(context));
    }

    private static void contextStopped(ApplicationContext context) {
        listeners.stream().filter(listener -> listener.getTypeClass().equals(ContextStopped.class))
                .forEach(listener -> listener.action(context));
    }

    private static void contextRefreshed(ApplicationContext context) {
        listeners.stream().filter(listener -> listener.getTypeClass().equals(ContextRefreshed.class))
                .forEach(listener -> listener.action(context));
    }

    private static void contextClosed(ApplicationContext context) {
        listeners.stream().filter(listener -> listener.getTypeClass().equals(ContextClosed.class))
                .forEach(listener -> listener.action(context));
    }

    private static void createSingletons(Config config, ApplicationContext context) {
        for (Class<?> packageClass : config.getReflections().getTypesAnnotatedWith(Singleton.class)) {
            Class<?>[] ifcs = packageClass.getInterfaces();

            if (ifcs.length > 1) {
                throw new CalculatorException(
                        String.format("%s has more than 1 interfaces", packageClass.getName()));
            } else if (ifcs.length == 1) {
                context.getObject(ifcs[0]);
            } else {
                 context.getObject(packageClass);
            }

        }
    }

    private static void createListeners(Config config) {
        try {
            for (Class<? extends EventListener> listenerClass : config.getReflections().getSubTypesOf(EventListener.class)) {
                listeners.add(listenerClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new CalculatorException("Cannot create instance of event listener class");
        }
    }

}
