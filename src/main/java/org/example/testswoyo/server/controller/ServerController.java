package org.example.testswoyo.server.controller;

import lombok.RequiredArgsConstructor;
import org.example.testswoyo.server.Server;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ServerController {
    private final Server server;
    private final Boolean isServerRunning = true;
    private final ComputerLog computerLog;
    private final Scanner scanner = new Scanner(System.in);



    public void startServer(Integer port) {
        server.start(port);

        while (isServerRunning) {

            String command = """
                Server controller
                Command:
                log - show log
                exit - exit
                """;

            System.out.println(command);
            System.out.println("Enter command: ");
            String input = scanner.nextLine();
            if (input.equals("log")) {
                Optional<Path> path = computerLog.getlog();
                if (path.isPresent()) {
                    try {
                        System.out.println(Files.readString(path.get()));
                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
            } else if (input.equals("exit")) {
                System.exit(0);
            } else {
                System.out.println("Invalid command");
            }
        }
    }



}
