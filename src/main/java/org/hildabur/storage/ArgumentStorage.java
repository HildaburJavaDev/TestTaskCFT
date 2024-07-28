package org.hildabur.storage;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArgumentStorage {
    private boolean isOptionA;
    private boolean isOptionS;
    private boolean isOptionF;
    private String fileNamePrefix;
    private String filepath;
    private List<String> files;

    public ArgumentStorage() {
        files = new ArrayList<>();
    }

    public void addFile(String fileName) {
        files.add(fileName);
    }
}
