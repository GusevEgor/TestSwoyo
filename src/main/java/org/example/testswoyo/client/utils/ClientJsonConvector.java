package org.example.testswoyo.client.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ClientJsonConvector {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> Optional<T> deserialize(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty()) {
            log.error("Input JSON string cannot be null or empty");
            return Optional.empty();
        }

        try {
            return Optional.of(objectMapper.readValue(json, clazz));
        } catch (Exception e) {
            log.error("Unexpected error during JSON deserialization", e);
            return Optional.empty();
        }
    }

    public static Optional<String> serialize(Object object) {
        try {
            return Optional.of(objectMapper.writeValueAsString(object));
        } catch (Exception e) {
            log.error("Error while serializing message: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
