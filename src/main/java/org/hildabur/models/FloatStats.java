package org.hildabur.models;

import java.math.BigDecimal;
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

    @Override
    public void updateStats(Object value) {
        updateStats((BigDecimal) value);
    }
    private synchronized void updateStats(BigDecimal number) {
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
        return
                count != 0 ?
                ("Full stats for Floats: " +
                "\n\tcount: " +
                count +
                "\n\tSum: " +
                sum.toString() +
                "\n\tMax: " +
                max.toString() +
                "\n\tMin: " +
                min.toString() +
                "\n\tAVG: " +
                calcAvg().toString())
                : ("No float stats, because this datatype wasn't in input files");
    }
}
