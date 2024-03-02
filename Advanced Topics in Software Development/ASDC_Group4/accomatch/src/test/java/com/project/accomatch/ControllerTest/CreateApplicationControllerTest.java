package com.project.accomatch.ControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.project.accomatch.Controller.CreateApplicationController;
import com.project.accomatch.Service.Implementation.CreateApplicationFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class CreateApplicationControllerTest {

    @Mock
    private CreateApplicationFactory createApplicationService;

    @InjectMocks
    private CreateApplicationController createApplicationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAD_Success() {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", "LH");

        when(createApplicationService.createAD(requestBody)).thenReturn("Success");

        // Act
        String result = createApplicationController.createAD(requestBody);

        // Assert
        assertEquals("Success", result);
        verify(createApplicationService, times(1)).createAD(requestBody);
    }

    @Test
    void testCreateAD_Exception() {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", "LH");

        String errorMessage = "Error message";
        when(createApplicationService.createAD(requestBody)).thenThrow(new RuntimeException(errorMessage));

        // Act
        String result = createApplicationController.createAD(requestBody);

        // Assert
        assertEquals(errorMessage, result);
        verify(createApplicationService, times(1)).createAD(requestBody);
    }


}
