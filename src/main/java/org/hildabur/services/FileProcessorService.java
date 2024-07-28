package org.hildabur.services;

import org.hildabur.models.Stats;
import org.hildabur.storage.ArgumentStorage;
import org.hildabur.utils.DataType;
import org.hildabur.utils.DataTypeDeterminer;
import org.hildabur.utils.Notificator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;

public class FileProcessorService implements Runnable {
    private final ArgumentStorage argumentStorage;
    private final DirectoryProvider directoryProvider;
    private final String fileName;
    private final ConcurrentHashMap<DataType, Stats> statsMap;

    public FileProcessorService(ArgumentStorage argumentStorage, DirectoryProvider directoryProvider, String fileName, ConcurrentHashMap<DataType, Stats> statsMap) {
        this.argumentStorage = argumentStorage;
        this.directoryProvider = directoryProvider;
        this.fileName = fileName;
        this.statsMap = statsMap;
    }

    @Override
    public void run() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                updateDataTypeStats(DataTypeDeterminer.determineDataType(line), line);
            }
        } catch (FileNotFoundException exception) {
            Notificator.printWarning("Error: The file '" + fileName + "' was not found. Please check the file path and try again.");
        } catch (IOException exception) {
            Notificator.printWarning("Error: An unexpected error occurred while processing the file '" + fileName + "'.");
        }
    }

    private void updateDataTypeStats(DataType dataType, String line) {
        Stats stats = statsMap.get(dataType);
        if (stats != null) {
            switch (dataType) {
                case INTEGER -> stats.updateStats(new BigInteger(line));
                case FLOAT -> stats.updateStats(new BigDecimal(line));
                case STRING -> stats.updateStats(line);
                default -> Notificator.printWarning("Unknown data type " + line);
            }
        }
    }
}
