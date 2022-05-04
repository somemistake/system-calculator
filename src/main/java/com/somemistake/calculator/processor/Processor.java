package com.somemistake.calculator.processor;

import com.somemistake.calculator.context.ApplicationContext;

public interface Processor {
    void process(Object object, ApplicationContext context);
}
