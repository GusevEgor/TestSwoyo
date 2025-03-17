package org.example.testswoyo.app;

import java.util.Arrays;

public enum Mode {
    SERVER,
    CLIENT;


    public static Mode fromString(String mode) {
        return Arrays.stream(Mode.values())
                .filter(m -> m.name()
                        .equalsIgnoreCase(mode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid mode: " + mode));
    }
}
