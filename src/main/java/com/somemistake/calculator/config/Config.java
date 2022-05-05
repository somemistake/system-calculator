package com.somemistake.calculator.config;

import org.reflections.Reflections;

public interface Config {
    <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass);

    <T> T getBean(Class<T> interfaceClass);

    Reflections getPackageInfo();
}
