package org.hildabur.services;

import lombok.Getter;
import org.hildabur.utils.Notificator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;

@Getter
public class DirectoryManager {
    private final DirectoryProvider directoryProvider;

    public DirectoryManager(DirectoryProvider directoryProvider) {
        this.directoryProvider = directoryProvider;
    }

    public boolean createDirectoryIfNeed() {
        if (!directoryProvider.isExist()) {
            try {
                createDirectory();
                return true;
            } catch (IOException | SecurityException | InvalidPathException exception) {
                Notificator.printWarning(exception.getMessage());
                return false;
            }
        } else {
            return true;
        }
    }

    private void createDirectory() throws IOException, SecurityException, InvalidPathException {
        Files.createDirectory(directoryProvider.getDirectoryPath());
    }
}
