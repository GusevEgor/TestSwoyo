package org.example.testswoyo.server.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class ServerGlobalRequestHandler {
    private final ServerRequestRouter requestRouter;
    private final ServerGlobalResponseHandler serverGlobalResponseHandler;

    public String analyze(String json) {
        try {
            Optional<JsonNode> parent;
            try {
                parent = Optional.ofNullable(new ObjectMapper().readTree(json));
            } catch (Exception e) {
                parent = Optional.empty();
                log.warn("Json is invalid: {}, Error: {}", json, e.getMessage());
                return serverGlobalResponseHandler.sendBadRequest("Bad request");
            }


            try {
                if (parent.isPresent() && parent.get().has("command")) {
                    JsonNode commandNode = parent.get().get("command");
                    ServerRequest requestsCommand;
                    try {
                        requestsCommand = ServerRequest.valueOf(commandNode.asText());
                    } catch (IllegalArgumentException e) {
                        log.warn("Command not found set UNEXPECTED: {}", commandNode);
                        return serverGlobalResponseHandler.sendBadRequest("Bad request");
                    }


                    if (parent.get().has("request")) {
                        JsonNode dtoNode = parent.get().get("request");
                        if (dtoNode == null || dtoNode.isEmpty()) {
                            log.warn("request is empty or invalid");
                            return serverGlobalResponseHandler.sendBadRequest("Bad request");
                        }

                        try {
                            String dtoJson = dtoNode.toString();
                            log.info("request: {}", dtoJson);

                            try {
                                return requestsCommand.execute(requestRouter, dtoJson);
                            } catch (Exception e) {
                                log.error("Error while processing in server: {}", e.getMessage());
                                return serverGlobalResponseHandler.sendBadRequest("Bad request");
                            }


                        } catch (Exception e) {
                            log.warn("Error while processing DTO: {}", e.getMessage());
                            return serverGlobalResponseHandler.sendBadRequest("Bad request");
                        }

                    } else {
                        log.warn("Parameters 'request' are required {}", parent.get());
                        return serverGlobalResponseHandler.sendBadRequest("Bad request");
                    }
                } else {
                    log.warn("Command not found: {}", json);
                    return serverGlobalResponseHandler.sendBadRequest("Bad request");
                }

            } catch (Exception e) {
                log.warn("Error while parsing dto: {}", e.getMessage());
                return serverGlobalResponseHandler.sendBadRequest("Bad request");
            }
        } catch (Exception e) {
            log.info("Parsing error: {}", e.getMessage());
            return serverGlobalResponseHandler.sendBadRequest("Bad request");
        }
    }
}


