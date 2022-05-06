package com.somemistake.calculator.configuration;

import com.somemistake.calculator.annotations.Configuration;
import com.somemistake.calculator.model.formatter.CommonFormatter;
import com.somemistake.calculator.model.formatter.Formatter;
import com.somemistake.calculator.model.loader.ConsoleDataLoader;
import com.somemistake.calculator.model.loader.DataLoader;
import com.somemistake.calculator.model.printer.ConsolePrinter;
import com.somemistake.calculator.model.printer.Printer;
import com.somemistake.calculator.model.validator.CommonValidator;
import com.somemistake.calculator.model.validator.Validator;

@Configuration
public class ApplicationConfiguration {

    public Validator validator() {
        return new CommonValidator();
    }

    public DataLoader dataLoader() {
        return new ConsoleDataLoader();
    }

    public Formatter formatter() {
        return new CommonFormatter();
    }

    public Printer printer() {
        return new ConsolePrinter();
    }

}
