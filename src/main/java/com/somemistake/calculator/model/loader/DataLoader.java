package com.somemistake.calculator.model.loader;

import com.somemistake.calculator.model.Data;
import com.somemistake.calculator.model.validator.Validator;

public interface DataLoader {
    Data loadData();

    void setValidator(Validator validator);
}
