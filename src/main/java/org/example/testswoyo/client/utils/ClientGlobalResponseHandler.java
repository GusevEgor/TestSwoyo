package org.example.testswoyo.client.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientGlobalResponseHandler {
    private final ClientResponseRouter responseRouter;

    public String analyze(String json) {
        try {
            Optional<JsonNode> parent;
            try {
                parent = Optional.ofNullable(new ObjectMapper().readTree(json));
            } catch (Exception e) {
                parent = Optional.empty();
                log.warn("Json is invalid: {}, Error: {}", json, e.getMessage());
            }


            try {
                if (parent.isPresent() && parent.get().has("command")) {
                    JsonNode commandNode = parent.get().get("command");
                    ClientResponses responsesCommand;
                    try {
                        responsesCommand = ClientResponses.valueOf(commandNode.asText());
                        if (responsesCommand.equals(ClientResponses.SUCCESS)) {
                            return "Success";
                        }
                        if (responsesCommand.equals(ClientResponses.BAD_REQUEST)) {
                            if (parent.get().has("response")) {
                                log.warn("BAD_REQUEST: {}", commandNode);
                                return "Bad request: " + parent.get().get("response").toString();
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        log.warn("Command not found set UNEXPECTED: {}", commandNode);
                        return "Server error";
                    }

                    System.out.println(parent);


                    if (parent.get().has("response")) {
                        JsonNode dtoNode = parent.get().get("response");
                        if (dtoNode == null || dtoNode.isEmpty()) {
                            log.warn("response is empty or invalid");
                            return "Server error";
                        }

                        try {
                            String dtoJson = dtoNode.toString();
                            log.info("response: {}", dtoJson);

                            try{
                                return responsesCommand.execute(responseRouter, dtoJson);
                            } catch (Exception e) {
                                log.error("Error while processing in server: {}", e.getMessage());
                                return "Server error";
                            }

                        } catch (Exception e) {
                            log.warn("Error while processing DTO: {}", e.getMessage());
                            return "Server error";
                        }
                    } else {
                        log.warn("Parameters 'response' are required {}", parent.get());
                        return "Server error";
                    }
                } else {
                    log.warn("Command not found: {}", json);
                    return "Server error";
                }

            } catch (Exception e) {
                log.warn("Error while parsing dto: {}", e.getMessage());
                return "Server error";
            }
        } catch (Exception e) {
            log.info("Parsing error: {}", e.getMessage());
            return "Server error";
        }
    }
}
