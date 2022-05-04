package com.somemistake.calculator.factory;

import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.configurator.ProxyConfigurator;
import com.somemistake.calculator.context.ApplicationContext;
import com.somemistake.calculator.model.exception.CalculatorException;
import com.somemistake.calculator.processor.Processor;

import java.util.ArrayList;
import java.util.List;

public class BeanFactory {

    private ApplicationContext context;
    private List<Processor> processors;
    private List<ProxyConfigurator> configurators;

    public BeanFactory(ApplicationContext context) {
        this.context = context;
        Config config = context.getConfig();

        this.processors = new ArrayList<>();
        try {
            for (Class<? extends Processor> processorClass : config.getReflections().getSubTypesOf(Processor.class)) {
                this.processors.add(processorClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new CalculatorException("Cannot create instance of processor class");
        }

        this.configurators = new ArrayList<>();
        try {
            for (Class<? extends ProxyConfigurator> configuratorClass : config.getReflections().getSubTypesOf(ProxyConfigurator.class)) {
                this.configurators.add(configuratorClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new CalculatorException("Cannot create instance of configurator class");
        }
    }

    public <T> T createObject(Class<T> target) {
        try {
            T t = target.getDeclaredConstructor().newInstance();

            process(t);

            t = configure(t, target);

            return t;
        } catch (Exception e) {
            throw new CalculatorException(
                    String.format("Cannot create instance of %s class", target.getName()));
        }
    }

    private <T> void process(T t) {
        processors.forEach(processor -> processor.process(t, context));
    }

    private <T> T configure(T target, Class<?> targetClass) {
        for (ProxyConfigurator configurator : configurators) {
            target = (T) configurator.configure(target, targetClass);
        }
        return target;
    }

}
