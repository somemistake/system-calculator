package com.somemistake.calculator.listener;

import com.somemistake.calculator.annotations.InvokeMethod;
import com.somemistake.calculator.context.ApplicationContext;
import com.somemistake.calculator.event.ContextRefreshed;
import com.somemistake.calculator.model.exception.CalculatorException;

import java.lang.reflect.Method;

public class InvokeMethodEventListener extends EventListener<ContextRefreshed> {

    @Override
    public void action(ApplicationContext context) {
        for (Class<?> parentClass : context.getConfig().getReflections().getSubTypesOf(Object.class)) {
            for (Method method : parentClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(InvokeMethod.class)) {
                    Object target = context.getObject(parentClass);
                    try {
                        method.invoke(target);
                    } catch (Exception e) {
                        throw new CalculatorException(
                                String.format("Cannot invoke %s method of %s", method.getName(), parentClass.getCanonicalName()));
                    }
                }
            }
        }
    }

}
