package com.somemistake.calculator.model.validator;

import com.somemistake.calculator.exception.ApplicationException;

public interface Validator {
    void validate(String command) throws ApplicationException;
}
