package org.example.testswoyo;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.example.testswoyo.app.ModeApp;
import org.example.testswoyo.app.Mode;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class TestSwoyoApplication implements CommandLineRunner {
    private final ModeApp modeApp;

    private String mode;
    private String host = "localhost";
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(TestSwoyoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter mode (server or client):");
            mode = scanner.nextLine();
            System.out.println("Enter port:");
            String port = scanner.nextLine();
            if (port.isEmpty() || !isNumber(port)){
                port = "8080";
                System.out.println("Default port: " + port);
            }

            Mode modeEnum = Mode.fromString(mode);

            System.out.println("Mode: " + modeEnum);
            System.out.println("Host: " + host);
            System.out.println("Port: " + port);

            modeApp.startService(modeEnum, Integer.parseInt(port));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid mode: " + mode);
        }
    }

    public boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}