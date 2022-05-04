package com.somemistake.calculator.util;

import static com.somemistake.calculator.util.CalculatorConstants.ASCII_LETTER_CONSTANT;
import static com.somemistake.calculator.util.CalculatorConstants.ASCII_NUMBER_CONSTANT;

public class CalculatorUtils {

    public static long getNumberFromChar(char var1) {
        return var1 >= 65 ? var1 - ASCII_LETTER_CONSTANT : var1 - ASCII_NUMBER_CONSTANT;
    }

    public static char getCharFromNumber(long var1) {
        return (char) (var1 >= 10 ? var1 + ASCII_LETTER_CONSTANT : var1 + ASCII_NUMBER_CONSTANT);
    }

    public static int getPowerOfNumber(long number, int calculusSystem) {
        long var1 = 1;
        int power = 0;

        while (var1 <= number) {
            var1 *= calculusSystem;
            power++;
        }

        return power;
    }

}
