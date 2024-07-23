package org.hildabur.services;

import org.hildabur.storage.ArgumentStorage;

public class FileProcessorService implements Runnable{
    private final ArgumentStorage argumentStorage;
    private final DirectoryProvider directoryProvider;
    private final String fileName;

    public FileProcessorService(ArgumentStorage argumentStorage, DirectoryProvider directoryProvider, String fileName) {
        this.argumentStorage = argumentStorage;
        this.directoryProvider = directoryProvider;
        this.fileName = fileName;
    }

    @Override
    public void run() {

    }
}
