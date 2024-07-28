package org.hildabur.models;

public interface StatsInterface {
    String getFullStats();

    String getShortStats(String dataTypeName);
    void updateStats(Object value);
}
