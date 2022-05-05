package com.somemistake.calculator.config;

import com.somemistake.calculator.annotations.Configuration;
import com.somemistake.calculator.formatter.CommonFormatter;
import com.somemistake.calculator.formatter.Formatter;
import com.somemistake.calculator.loader.ConsoleDataLoader;
import com.somemistake.calculator.loader.DataLoader;
import com.somemistake.calculator.printer.ConsolePrinter;
import com.somemistake.calculator.printer.Printer;
import com.somemistake.calculator.validator.CommonValidator;
import com.somemistake.calculator.validator.Validator;

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
