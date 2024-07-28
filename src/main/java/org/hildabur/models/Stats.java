package org.hildabur.models;

public abstract class Stats implements StatsInterface {
    protected int count;

    public Stats() {
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount() {
        ++count;
    }

    public abstract String getFullStats();

    public String getShortStats(String dataTypeName) {
        return count != 0 ? ("Short stats for " + dataTypeName + ":" +
                "\ncount: " + count)
                : ("No stats for " + dataTypeName + ", because this datatype wasn't in input files");
    }
}
