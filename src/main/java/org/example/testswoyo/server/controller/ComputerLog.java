package org.example.testswoyo.server.controller;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Component
public class ComputerLog {

    private final String logFilePath = "logs/application.log";


    public Optional<Path> getlog() {
        Path logPath = Path.of(logFilePath);
        if (Files.notExists(logPath)) {
            System.out.println("Log file does not exist.");
        }
        try {
            Path tempLogPath = Files.createTempFile("user_log_", ".log");

            Files.copy(logPath, tempLogPath, StandardCopyOption.REPLACE_EXISTING);
            return Optional.of(tempLogPath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return Optional.empty();
    }

}
