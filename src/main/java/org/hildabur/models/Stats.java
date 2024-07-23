package org.hildabur.models;

public abstract class Stats implements StatsInterface {
    protected int count;

    public Stats() {
        this.count = 0;
    }

    public void incrementCount() {
        ++count;
    }

    public abstract String getFullStats();

    public String getShortStats(String dataTypeName) {
        return "Short stats for " + dataTypeName + ":" +
                "\ncount: " + count;
    }
}
