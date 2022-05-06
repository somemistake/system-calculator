package com.somemistake.calculator.model.formatter;

import com.somemistake.calculator.annotations.Logging;
import com.somemistake.calculator.annotations.Singleton;
import com.somemistake.calculator.model.Data;

import static com.somemistake.calculator.util.CalculatorUtils.*;

@Singleton
@Logging
public class CommonFormatter implements Formatter {

    public CommonFormatter() {
    }

    public Data format(Data data) {
        long decimalNumber = 0;

        char[] numberChars = data.getNumber().toCharArray();
        int fromSystem = data.getFromSystem();
        int toSystem = data.getToSystem();

        if (fromSystem != 10) {
            int numberLength = numberChars.length;

            for (int i = numberLength - 1; i >= 0; i--) {
                char var1 = numberChars[i];
                decimalNumber += getNumberFromChar(var1) * Math.pow(fromSystem, numberLength - i - 1);
            }
        } else
            decimalNumber = Long.parseLong(data.getNumber());

        int power = getPowerOfNumber(decimalNumber, toSystem);

        char[] nonDecimalNumberCharArray = new char[power];

        for (int i = 0; i < power; i++) {
            nonDecimalNumberCharArray[power - i - 1] = getCharFromNumber(decimalNumber % toSystem);
            decimalNumber /= toSystem;
        }

        String formattedNumber = String.valueOf(nonDecimalNumberCharArray);

        Data formattedData = new Data();

        formattedData.setNumber(formattedNumber);
        formattedData.setFromSystem(fromSystem);
        formattedData.setToSystem(toSystem);

        return formattedData;
    }

}
