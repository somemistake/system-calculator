package com.somemistake.calculator.loader;

import com.somemistake.calculator.annotations.Inject;
import com.somemistake.calculator.annotations.Logging;
import com.somemistake.calculator.annotations.Singleton;
import com.somemistake.calculator.context.ApplicationContext;
import com.somemistake.calculator.model.Data;
import com.somemistake.calculator.model.exception.CalculatorException;
import com.somemistake.calculator.validator.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Singleton
@Logging
public class ConsoleDataLoader implements DataLoader {

    @Inject
    private Validator validator;

    public ConsoleDataLoader() {
    }

    private final String divider = "[ ]?-[nft][ ]";

    public Data loadData() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String command = reader.readLine();
            validator.validate(command);

            String[] inputData = command.split(divider);

            Data data = new Data();

            data.setNumber(inputData[1]);
            data.setFromSystem(Integer.parseInt(inputData[2]));
            data.setToSystem(Integer.parseInt(inputData[3]));

            return data;
        } catch (IOException e) {
            throw new CalculatorException("Cannot load data", e);
        }
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
