package org.hildabur.providers;

import org.hildabur.storage.ArgumentStorage;
import org.hildabur.utils.Notificator;

public class ArgumentProvider {
    public static ArgumentStorage getArguments(String[] args) {
        ArgumentStorage argumentStorage = new ArgumentStorage();
        for (String str : args) {
            switch (str) {
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
                default -> Notificator.printWarning("Unknown option \"" + str + "\". Skipped");
            }
        }
        return null;
    }
}
