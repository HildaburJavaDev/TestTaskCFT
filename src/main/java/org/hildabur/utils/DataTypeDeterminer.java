package org.hildabur.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DataTypeDeterminer {
    public static DataType determineDataType(String line) {
        if (line.isEmpty()) {
            return DataType.UNKNOWN;
        } else if (isInteger(line)) {
            return DataType.INTEGER;
        } else if (isFloat(line)) {
            return DataType.FLOAT;
        } else {
            return DataType.STRING;
        }
    }

    private static boolean isInteger(String line) {
        try {
            new BigInteger(line);
            return true;
        } catch (NumberFormatException exc) {
            return false;
        }
    }

    private static boolean isFloat(String line) {
        try {
            new BigDecimal(line);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}
