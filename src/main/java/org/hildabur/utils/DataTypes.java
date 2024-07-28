package org.hildabur.utils;

public enum DataTypes {
    INTEGER("integers.txt"),
    FLOAT("floats.txt"),
    STRING("strings.txt"),
    UNKNOWN("");

    private final String fileName;

    DataTypes(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
