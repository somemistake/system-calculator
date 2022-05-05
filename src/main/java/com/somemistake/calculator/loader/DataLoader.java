package com.somemistake.calculator.loader;

import com.somemistake.calculator.model.Data;
import com.somemistake.calculator.validator.Validator;

public interface DataLoader {
    Data loadData();

    void setValidator(Validator validator);
}
