package org.hildabur;

import org.hildabur.models.FloatStats;
import org.hildabur.models.IntegerStats;
import org.hildabur.models.Stats;
import org.hildabur.models.StringsStats;
import org.hildabur.services.ArgumentProvider;
import org.hildabur.services.DirectoryManager;
import org.hildabur.services.DirectoryProvider;
import org.hildabur.services.FileService;
import org.hildabur.storage.ArgumentStorage;
import org.hildabur.utils.DataType;
import org.hildabur.utils.Notificator;

import java.util.HashMap;

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
            HashMap<DataType, Stats> statsMap = new HashMap<>();
            initializeStatsMap(statsMap);
            for (String fileName: argumentStorage.getFiles()) {
                new FileService(argumentStorage, fileName, statsMap).calc();
            }
            printStats(statsMap, argumentStorage.isOptionF());
        }
    }

    private static void initializeStatsMap(HashMap<DataType, Stats> statsMap) {
        statsMap.put(DataType.FLOAT, new FloatStats());
        statsMap.put(DataType.INTEGER, new IntegerStats());
        statsMap.put(DataType.STRING, new StringsStats());
    }
    private static void printStats(HashMap<DataType, Stats> statsMap, boolean isOutFullStats) {
        statsMap.forEach(
                (key, stats) -> System.out.println(isOutFullStats
                    ? stats.getFullStats()
                    : stats.getShortStats(key.getTypeName().toLowerCase()))
        );
    }
}