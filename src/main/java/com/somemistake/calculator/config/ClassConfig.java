package com.somemistake.calculator.config;

import com.somemistake.calculator.annotations.Configuration;
import com.somemistake.calculator.model.exception.CalculatorException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassConfig implements Config {

    private final Reflections packageInfo;
    private final Set<Class<?>> configurationClasses;
    private final Map<Class<?>, Object> configurations;

    public ClassConfig(Class packageClass) {
        this.packageInfo = new Reflections(packageClass.getPackage().getName(), new SubTypesScanner(false));
        this.configurationClasses = new HashSet<>();
        for (Class<?> objectClass : packageInfo.getSubTypesOf(Object.class)) {
            if (objectClass.isAnnotationPresent(Configuration.class)) {
                this.configurationClasses.add(objectClass);
            }
        }

        this.configurations = this.configurationClasses.stream()
                .collect(Collectors.toMap(
                        configurationClass -> configurationClass, configurationClass -> {
                            try {
                                return configurationClass.getDeclaredConstructor().newInstance();
                            } catch (Exception e) {
                                throw new CalculatorException(
                                        String.format("Cannot create instance of %s", configurationClass.getCanonicalName()));
                            }
                        }
                ));
    }

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> interfaceClass) {
        for (Map.Entry<Class<?>, Object> configurationEntry : configurations.entrySet()) {
            Class<?> configurationClass = configurationEntry.getKey();
            Object configuration = configurationEntry.getValue();
            for (Method method : configurationClass.getDeclaredMethods()) {
                if (method.getReturnType().equals(interfaceClass)) {
                    try {
                        return (T) method.invoke(configuration);
                    } catch (Exception e) {
                        throw new CalculatorException(
                                String.format("Cannot create instance of %s class in %s configuration",
                                        interfaceClass.getCanonicalName(), configurationClass.getCanonicalName()));
                    }
                }
            }
        }
        return null;
    }

    public Reflections getPackageInfo() {
        return packageInfo;
    }

    public Set<Class<?>> getConfigurationClasses() {
        return configurationClasses;
    }

    public Map<Class<?>, Object> getConfigurations() {
        return configurations;
    }
}
