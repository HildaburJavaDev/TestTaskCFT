package org.hildabur.services;

import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Getter
public class DirectoryProvider {
    private final Path directoryPath;

    public DirectoryProvider(String directoryPath) {
        this.directoryPath = Paths.get(directoryPath).toAbsolutePath().normalize();
    }

    public boolean isExist() {
        return Files.exists(directoryPath);
    }
}
