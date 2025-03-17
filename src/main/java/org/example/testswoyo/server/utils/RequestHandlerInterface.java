package org.example.testswoyo.server.utils;


@FunctionalInterface
public interface RequestHandlerInterface {
    String handle(ServerRequestRouter distributor, String json);
}
