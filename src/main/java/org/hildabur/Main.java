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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        start(args);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void start(String[] args) {
        ArgumentStorage argumentStorage = ArgumentProvider.getArguments(args);
        DirectoryManager directoryManager = new DirectoryManager(new DirectoryProvider(argumentStorage.getFilepath()));
        if (!directoryManager.createDirectoryIfNeed()) {
            Notificator.printWarning("Invalid path. Result will be in default location");
            argumentStorage.setFilepath("");
        } else {
            ConcurrentHashMap<DataType, Stats> statsMap = new ConcurrentHashMap<>();
            initializeStatsMap(statsMap);

            ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            for (String fileName: argumentStorage.getFiles()) {
                executorService.execute(new FileService(argumentStorage, fileName, statsMap));
            }
            executorService.shutdown();
            try {
                while (!executorService.isTerminated()) {
                    executorService.awaitTermination(1, TimeUnit.SECONDS);
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
            printStats(statsMap, argumentStorage.isOptionF());
        }
    }

    private static void initializeStatsMap(ConcurrentHashMap<DataType, Stats> statsMap) {
        statsMap.put(DataType.FLOAT, new FloatStats());
        statsMap.put(DataType.INTEGER, new IntegerStats());
        statsMap.put(DataType.STRING, new StringsStats());
    }
    private static void printStats(ConcurrentHashMap<DataType, Stats> statsMap, boolean isOutFullStats) {
        statsMap.forEach(
                (key, stats) -> System.out.println(isOutFullStats
                    ? stats.getFullStats()
                    : stats.getShortStats(key.getTypeName().toLowerCase()))
        );
    }
}