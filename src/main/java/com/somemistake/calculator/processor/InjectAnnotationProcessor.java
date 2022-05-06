package com.somemistake.calculator.processor;

import com.somemistake.calculator.annotations.Inject;
import com.somemistake.calculator.context.ApplicationContext;
import com.somemistake.calculator.exception.ApplicationException;

import java.lang.reflect.Field;

public class InjectAnnotationProcessor implements Processor {

    @Override
    public void process(Object object, ApplicationContext context) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> fieldClass = field.getType();
                Object value = context.getObject(fieldClass);
                field.setAccessible(true);
                try {
                    field.set(object, value);
                } catch (IllegalAccessException e) {
                    throw new ApplicationException(
                            String.format("Cannot set value to %s field", object.getClass()));
                }
            }
        }
    }

}
