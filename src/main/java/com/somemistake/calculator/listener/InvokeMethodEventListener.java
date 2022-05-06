package com.somemistake.calculator.listener;

import com.somemistake.calculator.annotations.InvokeMethod;
import com.somemistake.calculator.context.ApplicationContext;
import com.somemistake.calculator.event.ContextRefreshed;
import com.somemistake.calculator.exception.ApplicationException;

import java.lang.reflect.Method;

public class InvokeMethodEventListener extends EventListener<ContextRefreshed> {

    @Override
    public void action(ApplicationContext context) {
        for (Class<?> objectClass : context.getConfig().getPackageInfo().getSubTypesOf(Object.class)) {
            for (Method method : objectClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(InvokeMethod.class)) {
                    Object target = context.getObject(objectClass);
                    try {
                        method.invoke(target);
                    } catch (Exception e) {
                        throw new ApplicationException(
                                String.format("Cannot invoke %s method of %s", method.getName(), objectClass.getCanonicalName()));
                    }
                }
            }
        }
    }

}
