package com.somemistake.calculator.publisher;

import com.somemistake.calculator.context.ApplicationContext;
import com.somemistake.calculator.event.ContextEvent;
import com.somemistake.calculator.listener.EventListener;
import com.somemistake.calculator.model.exception.CalculatorException;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher implements Publisher {

    private ApplicationContext context;
    private final List<EventListener<?>> listeners;

    public EventPublisher(ApplicationContext context) {
        this.context = context;
        this.listeners = new ArrayList<>();

        try {
            for (Class<? extends EventListener> listenerClass : context.getConfig().getPackageInfo().getSubTypesOf(EventListener.class)) {
                this.listeners.add(listenerClass.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            throw new CalculatorException("Cannot create instance of event listener class");
        }
    }

    @Override
    public void publishEvent(ContextEvent event) {
        listeners.stream().filter(listener -> listener.getTypeClass().equals(event.getClass()))
                .forEach(listener -> listener.action(context));
    }
}
