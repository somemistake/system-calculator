package com.somemistake.calculator.config;

import com.somemistake.calculator.model.exception.CalculatorException;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {
    private Reflections reflections;
    private Map<Class, Class> ifc2Impls;

    public JavaConfig(String packageName, Map<Class, Class> ifc2Impls) {
        this.reflections = new Reflections(packageName, new SubTypesScanner(false));
        this.ifc2Impls = ifc2Impls;
    }

    public JavaConfig(String packageName, Class... values) {
        if (values.length % 2 != 0)
            throw new CalculatorException("Each key must have a value");

        this.reflections = new Reflections(packageName, new SubTypesScanner(false));
        this.ifc2Impls = new HashMap<>();
        for (int i = 0; i < values.length / 2; i++) {
            this.ifc2Impls.put(values[2 * i], values[2 * i + 1]);
        }
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        if (ifc2Impls.containsKey(ifc)) {
            return ifc2Impls.get(ifc);
        }

        Set<Class<? extends T>> classes = reflections.getSubTypesOf(ifc);

        if (classes.size() != 1)
            throw new CalculatorException(
                    String.format("Interface %s has more than 1 or 0 implementations", ifc.getName()));

        Class implClass = classes.iterator().next();
        ifc2Impls.put(ifc, implClass);
        return implClass;
    }

    public Reflections getReflections() {
        return reflections;
    }
}
