package org.hildabur.models;

public class StringsStats extends Stats{
    int maxLength;
    int minLength;

    public StringsStats() {
        super();
        minLength = Integer.MAX_VALUE;
        maxLength = Integer.MIN_VALUE;
    }

    @Override
    public void updateStats(Object value) {
        updateStats((String) value);
    }
    private void updateStats(String str) {
        int length = str.length();
        incrementCount();
        if (minLength > length) {
            minLength = length;
        }
        if (maxLength < length) {
            maxLength = length;
        }
    }
    @Override
    public String getFullStats() {
        return count != 0
                ? ("Full stats for Strings: " + count +
                "\n\tMax length: " + maxLength
                + "\nMin length: " + minLength)
                : ("No string stats, because this datatype wasn't in input files");
    }
}
