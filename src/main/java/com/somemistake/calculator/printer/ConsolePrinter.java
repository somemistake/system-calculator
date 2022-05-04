package com.somemistake.calculator.printer;

import com.somemistake.calculator.annotations.Logging;
import com.somemistake.calculator.annotations.Singleton;
import com.somemistake.calculator.model.Data;

@Singleton
@Logging
public class ConsolePrinter implements Printer {

    public ConsolePrinter() {
    }

    public void print(Data dataToPrint) {
        System.out.printf(
                "Number %s in %d calculus system",
                dataToPrint.getNumber(),
                dataToPrint.getToSystem()
        );
    }

}
