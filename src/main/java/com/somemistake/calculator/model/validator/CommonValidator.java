package com.somemistake.calculator.model.validator;

import com.somemistake.calculator.annotations.Logging;
import com.somemistake.calculator.annotations.Singleton;
import com.somemistake.calculator.exception.ApplicationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.somemistake.calculator.util.CalculatorUtils.getNumberFromChar;

@Singleton
@Logging
public class CommonValidator implements Validator {

    private final Pattern inputPattern = Pattern.compile("-n [0-9A-Z]{1,32} -f \\d{1,2} -t \\d{1,2}");

    private final String divider = " ";

    public void validate(String command) throws ApplicationException {
        Matcher matcher = inputPattern.matcher(command);
        if (!matcher.matches())
            throw new ApplicationException(
                    String.format("Command %s is incorrect", command));

        String[] values = command.split(divider);

        char[] number = values[1].toCharArray();
        int fromSystem = Integer.parseInt(values[3]);
        int toSystem = Integer.parseInt(values[5]);

        if (fromSystem == toSystem)
            throw new ApplicationException("from system equals to system");

        for (char var1 : number) {
            long var2 = getNumberFromChar(var1);
            if (var2 >= fromSystem)
                throw new ApplicationException(
                        String.format("Numbers bigger than %s calculus system", fromSystem));
        }

    }
}
