package org.hildabur;

import org.hildabur.providers.ArgumentProvider;

public class Main {
    public static void main(String[] args) {
        System.out.println("storage: " + ArgumentProvider.getArguments(args));
    }
}