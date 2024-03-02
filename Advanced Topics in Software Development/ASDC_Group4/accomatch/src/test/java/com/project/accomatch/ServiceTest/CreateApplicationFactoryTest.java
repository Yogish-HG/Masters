package com.project.accomatch.ServiceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.project.accomatch.Service.HouseSeekerService;
import com.project.accomatch.Service.Implementation.CreateApplicationFactory;
import com.project.accomatch.Service.LeaseHolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

public class CreateApplicationFactoryTest {

    @Mock
    private LeaseHolderService leaseHolderService;

    @Mock
    private HouseSeekerService houseSeekerService;

    @InjectMocks
    private CreateApplicationFactory createApplicationFactory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAD_HouseSeeker_Success() {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", "AP");

        // Act
        String result = createApplicationFactory.createAD(requestBody);

        // Assert
        assertEquals("Success", result);
        verify(houseSeekerService, times(1)).createAD(requestBody);

    }

    @Test
    void testCreateAD_LeaseHolder_Success() {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("type", "LH");

        // Act
        String result = createApplicationFactory.createAD(requestBody);

        // Assert
        assertEquals("Success", result);
        verify(leaseHolderService, times(1)).createAD(requestBody);

    }



    @Test
    void testCreateAD_NullType_Error() {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        // Missing "type" in the requestBody

        // Act
        String result = createApplicationFactory.createAD(requestBody);

        // Assert
        assertEquals("Error", result);

    }

}
