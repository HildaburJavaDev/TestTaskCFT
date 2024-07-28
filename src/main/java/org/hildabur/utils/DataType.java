package org.hildabur.utils;

public enum DataType {
    INTEGER("integers.txt"),
    FLOAT("floats.txt"),
    STRING("strings.txt"),
    UNKNOWN("");

    private final String fileName;

    DataType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
