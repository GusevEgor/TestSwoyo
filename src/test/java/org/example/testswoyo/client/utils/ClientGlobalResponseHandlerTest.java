package org.example.testswoyo.client.utils;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ClientGlobalResponseHandlerTest {

    @InjectMocks
    private ClientGlobalResponseHandler clientGlobalResponseHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAnalyzeWithInvalidJson() {
        String invalidJson = "{ invalid json }";
        String result = clientGlobalResponseHandler.analyze(invalidJson);

        assertEquals("Server error", result);

    }

    @Test
    void testAnalyzeWithValidJsonButNoCommand() {
        String jsonWithoutCommand = "{\"request\": {}}";
        String result = clientGlobalResponseHandler.analyze(jsonWithoutCommand);
        assertEquals("Server error", result);
    }

    @Test
    void testAnalyzeWithValidJsonAndCommandNotFound() {
        String jsonWithInvalidCommand = "{\"command\": \"INVALID_COMMAND\", \"request\": {}}";

        String result = clientGlobalResponseHandler.analyze(jsonWithInvalidCommand);
        assertEquals("Server error", result);
    }

    @Test
    void testAnalyzeWithValidJsonAndValidCommandButEmptyDto() {
        String jsonWithEmptyDto = "{\"command\": \"VIEW\", \"request\": {}}";
        String result = clientGlobalResponseHandler.analyze(jsonWithEmptyDto);
        assertEquals("Server error", result);
    }

    @Test
    void testAnalyzeWithValidJsonAndCommandButDtoException() {
        String jsonWithDtoError = "{\"command\": \"VALID_COMMAND\", \"request\": {\"invalid\": \"data\"}}";

        String result = clientGlobalResponseHandler.analyze(jsonWithDtoError);
        assertEquals("Server error", result);
    }
}


