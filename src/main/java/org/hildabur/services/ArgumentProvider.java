package org.hildabur.services;

import org.hildabur.storage.ArgumentStorage;
import org.hildabur.utils.Notificator;

public class ArgumentProvider {
    public static ArgumentStorage getArguments(String[] args) {
        ArgumentStorage argumentStorage = new ArgumentStorage();
        int argsLength = args.length;
        for (int i = 0; i < argsLength; i++) {
            switch (args[i]) {
                case "-a", "-A" -> argumentStorage.setOptionA(true);
                case "-s", "-S" -> {
                    if (argumentStorage.isOptionF()) {
                        Notificator.printWarning("CONFLICT! Option \"-f\" is exist. Skipped");
                    } else {
                        argumentStorage.setOptionS(true);
                    }
                }
                case "-f", "-F" -> {
                    if (argumentStorage.isOptionS()) {
                        Notificator.printWarning("CONFLICT! Option \"-s\" is exist. Skipped");
                    } else {
                        argumentStorage.setOptionF(true);
                    }
                }
                case "-p", "-P" -> argumentStorage.setFileNamePrefix(args[++i]);
                case "-o", "-O" -> argumentStorage.setFilepath(args[++i]);
                default -> Notificator.printWarning("Unknown option \"" + args[i] + "\". Skipped");
            }
        }
        return argumentStorage;
    }
}
