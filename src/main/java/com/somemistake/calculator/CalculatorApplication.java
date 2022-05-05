package com.somemistake.calculator;

import com.somemistake.calculator.config.ClassConfig;
import com.somemistake.calculator.config.Config;
import com.somemistake.calculator.config.PackageConfig;
import com.somemistake.calculator.context.ApplicationContext;
import com.somemistake.calculator.context.ClassApplicationContext;
import com.somemistake.calculator.context.PackageApplicationContext;
import com.somemistake.calculator.factory.BeanFactory;

import java.util.Map;

public class CalculatorApplication {

    private ApplicationContext context;

    public CalculatorApplication(Class<?> packageClass) {
        Config config = new ClassConfig(packageClass);
        BeanFactory factory = new BeanFactory(config);
        this.context = new ClassApplicationContext(config, factory);
    }

    public CalculatorApplication(String packageName, Class<?>... values) {
        Config config = new PackageConfig(packageName, values);
        BeanFactory factory = new BeanFactory(config);
        this.context = new PackageApplicationContext(config, factory);
    }

    public CalculatorApplication(String packageName, Map<Class, Class> classes) {
        Config config = new PackageConfig(packageName, classes);
        BeanFactory factory = new BeanFactory(config);
        this.context = new PackageApplicationContext(config, factory);
    }

    public ApplicationContext run() {
        return context;
    }
}
