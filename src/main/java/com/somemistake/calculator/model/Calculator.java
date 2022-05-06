package com.somemistake.calculator.model;

import com.somemistake.calculator.annotations.Inject;
import com.somemistake.calculator.annotations.InvokeMethod;
import com.somemistake.calculator.annotations.Singleton;
import com.somemistake.calculator.model.formatter.Formatter;
import com.somemistake.calculator.model.loader.DataLoader;
import com.somemistake.calculator.model.printer.Printer;

@Singleton
public class Calculator {

    @Inject
    private DataLoader loader;
    @Inject
    private Formatter formatter;
    @Inject
    private Printer printer;

    public Calculator() {

    }

    @InvokeMethod
    public void calculate() {
        Data loadedData = loader.loadData();
        Data formattedData = formatter.format(loadedData);
        printer.print(formattedData);
    }

    public DataLoader getLoader() {
        return loader;
    }

    public void setLoader(DataLoader loader) {
        this.loader = loader;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
