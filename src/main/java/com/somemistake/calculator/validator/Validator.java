package com.somemistake.calculator.validator;

import com.somemistake.calculator.model.exception.CalculatorException;

public interface Validator {
    void validate(String command) throws CalculatorException;
}
