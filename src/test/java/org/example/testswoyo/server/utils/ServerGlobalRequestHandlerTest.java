package org.example.testswoyo.server.utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class ServerGlobalRequestHandlerTest {

    @InjectMocks
    private ServerGlobalRequestHandler serverGlobalRequestHandler;

    @Mock
    private ServerGlobalResponseHandler serverGlobalResponseHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAnalyzeWithInvalidJson() {
        String invalidJson = "{ invalid json }";
        Mockito.when(serverGlobalResponseHandler.sendBadRequest("Bad request")).thenReturn("Bad request");
        String result = serverGlobalRequestHandler.analyze(invalidJson);
        verify(serverGlobalResponseHandler).sendBadRequest("Bad request");
        assertEquals("Bad request", result);

    }

    @Test
    void testAnalyzeWithValidJsonButNoCommand() {
        String jsonWithoutCommand = "{\"request\": {}}";
        Mockito.when(serverGlobalResponseHandler.sendBadRequest("Bad request")).thenReturn("Bad request");
        String result = serverGlobalRequestHandler.analyze(jsonWithoutCommand);
        verify(serverGlobalResponseHandler).sendBadRequest("Bad request");
        assertEquals("Bad request", result);
    }

    @Test
    void testAnalyzeWithValidJsonAndCommandNotFound() {
        String jsonWithInvalidCommand = "{\"command\": \"INVALID_COMMAND\", \"request\": {}}";

        Mockito.when(serverGlobalResponseHandler.sendBadRequest("Bad request")).thenReturn("Bad request");
        String result = serverGlobalRequestHandler.analyze(jsonWithInvalidCommand);
        verify(serverGlobalResponseHandler).sendBadRequest("Bad request");
        assertEquals("Bad request", result);
    }

    @Test
    void testAnalyzeWithValidJsonAndValidCommandButEmptyDto() {
        String jsonWithEmptyDto = "{\"command\": \"VIEW\", \"request\": {}}";
        Mockito.when(serverGlobalResponseHandler.sendBadRequest("Bad request")).thenReturn("Bad request");
        String result = serverGlobalRequestHandler.analyze(jsonWithEmptyDto);
        verify(serverGlobalResponseHandler).sendBadRequest("Bad request");
        assertEquals("Bad request", result);
    }

    @Test
    void testAnalyzeWithValidJsonAndCommandButDtoException() {
        String jsonWithDtoError = "{\"command\": \"VALID_COMMAND\", \"request\": {\"invalid\": \"data\"}}";

        Mockito.when(serverGlobalResponseHandler.sendBadRequest("Bad request")).thenReturn("Bad request");
        String result = serverGlobalRequestHandler.analyze(jsonWithDtoError);
        verify(serverGlobalResponseHandler).sendBadRequest("Bad request");
        assertEquals("Bad request", result);
    }
}

