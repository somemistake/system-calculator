package com.somemistake.calculator.configurator;

import com.somemistake.calculator.annotations.Logging;
import com.somemistake.calculator.handler.LoggingInvocationHandler;

import java.lang.reflect.Proxy;

public class LoggingAnnotationProxyConfigurator implements ProxyConfigurator {

    @Override
    public Object configure(Object object, Class<?> objectClass) {
        if (objectClass.isAnnotationPresent(Logging.class)) {
            Class<?>[] interfaces = objectClass.getInterfaces();

            Object proxy = Proxy.newProxyInstance(
                    objectClass.getClassLoader(),
                    interfaces,
                    new LoggingInvocationHandler(objectClass, object)
            );

            return proxy;
        }
        return object;
    }

}
