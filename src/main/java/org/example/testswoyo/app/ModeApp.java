package org.example.testswoyo.app;
import lombok.RequiredArgsConstructor;
import org.example.testswoyo.client.controller.ClientController;
import org.example.testswoyo.server.controller.ServerController;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModeApp {

    private final ClientController clientController;
    private final ServerController serverController;

    public void startService(Mode mode, int port) {
        switch (mode) {
            case SERVER:
                startServer(port);
                break;
            case CLIENT:
                startClient(port);
                break;
            default:
                System.out.println("Invalid mode.");
                break;
        }
    }

    private void startServer(int port) {
        serverController.startServer(port);
    }

    private void startClient(int port) {
        clientController.start("localhost", port);
    }

}


