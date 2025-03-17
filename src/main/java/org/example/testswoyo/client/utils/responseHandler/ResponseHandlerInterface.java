package org.example.testswoyo.client.utils.responseHandler;


import org.example.testswoyo.client.utils.ClientResponseRouter;

@FunctionalInterface
public interface ResponseHandlerInterface {
    String handle(ClientResponseRouter distributor, String json);
}
