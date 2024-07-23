package org.hildabur.models;

public class StringsStats extends Stats{
    int maxLength;
    int minLength;

    public StringsStats() {
        super();
        minLength = Integer.MAX_VALUE;
        maxLength = Integer.MIN_VALUE;
    }

    public void updateStats(String str) {
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
        return "Full stats for Strings: " + count
                + "\nMax length: " + maxLength
                + "\nMin length: " + minLength;
    }
}
