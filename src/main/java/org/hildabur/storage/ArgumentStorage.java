package org.hildabur.storage;

import lombok.Data;

@Data
public class ArgumentStorage {
    private boolean isOptionA;
    private boolean isOptionS;
    private boolean isOptionF;
    private String fileNamePrefix;
    private String filepath;
}
