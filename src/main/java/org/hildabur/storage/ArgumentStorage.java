package org.hildabur.storage;

import lombok.Data;

import java.util.List;

@Data
public class ArgumentStorage {
    private boolean isOptionA;
    private boolean isOptionS;
    private boolean isOptionF;
    private String fileNamePrefix;
    private String filepath;
    private List<String> files;

    public void addFile(String fileName) {
        files.add(fileName);
    }
}
