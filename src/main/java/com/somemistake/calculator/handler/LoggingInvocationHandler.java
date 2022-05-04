package com.somemistake.calculator.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler, net.sf.cglib.proxy.InvocationHandler {
    private Logger logger;
    private Object target;

    public LoggingInvocationHandler(String className, Object target) {
        this.logger = LoggerFactory.getLogger(className);
        this.target = target;
    }

    public LoggingInvocationHandler(Class<?> className, Object target) {
        this.logger = LoggerFactory.getLogger(className);
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("start {} method", method.getName());
        Object result = method.invoke(target, args);
        logger.info("finish {} method with result {}", method.getName(), result);
        return result;
    }
}
