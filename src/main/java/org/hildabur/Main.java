package org.hildabur;

import org.hildabur.services.ArgumentProvider;
import org.hildabur.services.DirectoryManager;
import org.hildabur.services.DirectoryProvider;
import org.hildabur.services.FileProcessorService;
import org.hildabur.storage.ArgumentStorage;
import org.hildabur.utils.Notificator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        start(args);
    }

    private static void start(String[] args) {
        ArgumentStorage argumentStorage = ArgumentProvider.getArguments(args);
        DirectoryManager directoryManager = new DirectoryManager(new DirectoryProvider(argumentStorage.getFilepath()));
        if (!directoryManager.createDirectoryIfNeed()) {
            Notificator.printWarning("Invalid path. Result will be in default location");
            argumentStorage.setFilepath("");
        } else {
            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (String fileName: argumentStorage.getFiles()) {
                executorService.execute(new FileProcessorService(argumentStorage, directoryManager.getDirectoryProvider(), fileName));
            }
            executorService.shutdown();
        }
    }
}