package com.somemistake.calculator.config;

import com.somemistake.calculator.exception.ApplicationException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PackageConfig implements Config {

    private final Reflections packageInfo;
    private final Map<Class, Class> classes;

    public PackageConfig(String packageName, Map<Class, Class> classes) {
        this.packageInfo = new Reflections(packageName, new SubTypesScanner(false));
        this.classes = classes;
    }

    public PackageConfig(String packageName, Class... values) {
        if (values.length % 2 != 0)
            throw new ApplicationException("Each key must have a value");

        this.packageInfo = new Reflections(packageName, new SubTypesScanner(false));
        this.classes = new HashMap<>();
        for (int i = 0; i < values.length / 2; i++) {
            this.classes.put(values[2 * i], values[2 * i + 1]);
        }
    }

    @Override
    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {
        if (classes.containsKey(interfaceClass)) {
            return classes.get(interfaceClass);
        }

        Set<Class<? extends T>> classes = packageInfo.getSubTypesOf(interfaceClass);

        if (classes.size() != 1)
            throw new ApplicationException(
                    String.format("Interface %s has more than 1 or 0 implementations", interfaceClass.getName()));

        Class implementationClass = classes.iterator().next();
        this.classes.put(interfaceClass, implementationClass);
        return implementationClass;
    }

    @Override
    public <T> T getBean(Class<T> interfaceClass) {
        return null;
    }

    public Reflections getPackageInfo() {
        return packageInfo;
    }
}
