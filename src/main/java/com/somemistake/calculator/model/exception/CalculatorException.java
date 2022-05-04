package com.somemistake.calculator.model.exception;

public class CalculatorException extends RuntimeException {

    public CalculatorException(String message, Exception e) {
        super(message, e);
    }

    public CalculatorException(String message) {
        super(message);
    }
}
