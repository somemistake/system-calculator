package com.somemistake.calculator;

import com.somemistake.calculator.context.ApplicationContext;

import java.util.Map;

public class ApplicationRunner {

    public static ApplicationContext run(Class<?> packageClass) {
        return new Application(packageClass).run();
    }

    public static ApplicationContext run(String packageName, Class<?>... values) {
        return new Application(packageName, values).run();
    }

    public static ApplicationContext run(String packageName, Map<Class, Class> classes) {
        return new Application(packageName, classes).run();
    }

}
