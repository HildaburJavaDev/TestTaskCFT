package org.hildabur.services;

import org.hildabur.models.Stats;
import org.hildabur.storage.ArgumentStorage;
import org.hildabur.utils.DataType;
import org.hildabur.utils.DataTypeDeterminer;
import org.hildabur.utils.Notificator;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

public class FileService {
    private final ArgumentStorage argumentStorage;
    private final String fileName;
    private final HashMap<DataType, Stats> statsMap;

    public FileService(ArgumentStorage argumentStorage, String fileName, HashMap<DataType, Stats> statsMap) {
        this.argumentStorage = argumentStorage;
        this.fileName = fileName;
        this.statsMap = statsMap;
    }

    public void calc() {
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
                default -> {
                    return;
                }
            }
            writeToFile(dataType, line);
        }
    }

    private void writeToFile(DataType dataType, String line) {
        String filepath = argumentStorage.getFilepath() + "/" + argumentStorage.getFileNamePrefix() + dataType.getFileName();
        File file = new File(filepath);

        try {
            if (statsMap.get(dataType).getCount() == 1) {
                if (file.exists() && !argumentStorage.isOptionA()) {
                    writeLineToFile(file, line + "\n", false);
                } else if (!file.exists()) {
                    if (!file.createNewFile()) {
                        throw new IOException("Error: Could not create file");
                    } else {
                        writeLineToFile(file, line + "\n", true);
                    }
                } else {
                    writeLineToFile(file, line + "\n", true);
                }
            } else {
                writeLineToFile(file, line + "\n", true);
            }
        } catch (IOException exception) {
            Notificator.printWarning("Error: Could not create or write to file '" + filepath + "'. " + exception.getMessage());
        }
    }

    private void writeLineToFile(File file, String line, boolean isAppend) {
        try (FileWriter writer = new FileWriter(file, isAppend)) {
            writer.write(line);
        } catch (IOException e) {
            Notificator.printWarning("Error: Could not write to file '" + file.getPath() + "'.");
        }
    }
}
