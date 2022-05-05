package com.somemistake.calculator.publisher;

import com.somemistake.calculator.event.ContextEvent;

public interface Publisher {
    void publishEvent(ContextEvent event);
}
