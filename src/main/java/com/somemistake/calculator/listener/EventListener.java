package com.somemistake.calculator.listener;

import com.somemistake.calculator.event.ContextEvent;

import java.lang.reflect.ParameterizedType;

public abstract class EventListener<T extends ContextEvent> implements Listener {

    public Class<T> getTypeClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

}
