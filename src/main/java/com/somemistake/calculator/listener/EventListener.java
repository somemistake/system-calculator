package com.somemistake.calculator.listener;

import java.lang.reflect.ParameterizedType;

public abstract class EventListener<T> implements Listener {

    public Class<T> getTypeClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

}
