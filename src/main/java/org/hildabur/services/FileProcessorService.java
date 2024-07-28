package org.hildabur.services;

import org.hildabur.storage.ArgumentStorage;
import org.hildabur.utils.Notificator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            Notificator.printWarning("Error: The file '" + fileName + "' was not found. Please check the file path and try again.");
        } catch (IOException e) {
            Notificator.printWarning("Error: An unexpected error occurred while processing the file '" + fileName + "'.");
        }
    }
}
