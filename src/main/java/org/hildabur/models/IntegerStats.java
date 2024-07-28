package org.hildabur.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class IntegerStats extends Stats {
    private BigInteger sum;
    private BigInteger max;
    private BigInteger min;

    public IntegerStats() {
        super();
        sum = BigInteger.ZERO;
        max = BigInteger.valueOf(Long.MIN_VALUE);
        min = BigInteger.valueOf(Long.MAX_VALUE);
    }

    @Override
    public void updateStats(Object value) {
        updateStats((BigInteger) value);
    }

    private void updateStats(BigInteger number) {
        incrementCount();
        updateMaxMin(number);
        updateSum(number);
    }

    private void updateMaxMin(BigInteger number) {
        if (max.compareTo(number) < 0) {
            max = number;
        }
        if (min.compareTo(number) > 0) {
            min = number;
        }
    }

    private void updateSum(BigInteger number) {
        sum = sum.add(number);
    }
    private BigDecimal calcAvg() {
        if (count == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal sumDecimal = new BigDecimal(sum);
        BigDecimal countDecimal = new BigDecimal(count);
        try {
            return sumDecimal.divide(countDecimal, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException e) {
            return sumDecimal.divide(countDecimal, 10, RoundingMode.HALF_UP);
        }
    }

    @Override
    public String getFullStats() {
        return count != 0 ? ("Full stats for Integers: " +
                "\ncount: " +
                count +
                "\nSum: " +
                sum.toString() +
                "\nMax: " +
                max.toString() +
                "\nMin: " +
                min.toString() +
                "\nAVG: " +
                calcAvg().toString())
                : ("No integer stats, because this datatype wasn't in input files");
    }
}
