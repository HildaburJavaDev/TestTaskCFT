package org.hildabur.models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class FloatStats extends Stats {
    private BigDecimal sum;
    private BigDecimal max;
    private BigDecimal min;

    public FloatStats() {
        super();
        sum = BigDecimal.ZERO;
        max = BigDecimal.valueOf(Double.MIN_VALUE);
        min = BigDecimal.valueOf(Double.MAX_VALUE);
    }

    public void updateStats(BigDecimal number) {
        incrementCount();
        updateMaxMin(number);
        updateSum(number);
    }

    private void updateMaxMin(BigDecimal number) {
        if (max.compareTo(number) < 0) {
            max = number;
        }
        if (min.compareTo(number) > 0) {
            min = number;
        }
    }

    private void updateSum(BigDecimal number) {
        sum = sum.add(number);
    }

    private BigDecimal calcAvg() {
        if (count == 0) return BigDecimal.ZERO;
        BigDecimal counting = new BigDecimal(count);
        try {
            return sum.divide(counting, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            return sum.divide(counting, 10, RoundingMode.HALF_UP);
        }
    }
    @Override
    public String getFullStats() {
        return "Full stats for Floats: " +
                "\ncount: " +
                count +
                "\nSum: " +
                sum.toString() +
                "\nMax: " +
                max.toString() +
                "\nMin: " +
                min.toString() +
                "\nAVG: " +
                calcAvg().toString();
    }
}
