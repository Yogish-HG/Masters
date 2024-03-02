package com.project.accomatch.ServiceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.accomatch.Model.LeaseHolderModel;
import com.project.accomatch.Repository.Implementation.LeaseHolderFoodTableOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderGenderTableOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderImagesTableOperations;
import com.project.accomatch.Repository.Implementation.LeaseHolderTableOperations;
import com.project.accomatch.Service.Implementation.LeaseHolderServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.*;

public class LeaseHolderServiceImplementationTest {

    @Mock
    private LeaseHolderTableOperations leaseHolderTableOperations;

    @Mock
    private LeaseHolderFoodTableOperations leaseHolderFoodTableOperations;

    @Mock
    private LeaseHolderGenderTableOperations leaseHolderGenderTableOperations;

    @Mock
    private LeaseHolderImagesTableOperations leaseHolderImagesTableOperations;

    @InjectMocks
    private LeaseHolderServiceImplementation leaseHolderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAD_Success() throws Exception {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", 1);
        requestBody.put("size", 2);
        requestBody.put("title", "Sample Title");
        requestBody.put("subtitle", "Sample Subtitle");
        requestBody.put("address", "Sample Address");
        requestBody.put("location_city", "Sample City");
        requestBody.put("room_type", "Single Room");
        requestBody.put("document", "Sample Document");
        requestBody.put("other_preferences", "Sample Preferences");
        requestBody.put("rent", 1000.0);
        requestBody.put("is_verified", 0);
        requestBody.put("start_age", 18);
        requestBody.put("end_age", 30);
        requestBody.put("start_date", "2023-07-01");
        requestBody.put("food_preferences", new ArrayList());
        requestBody.put("images",  new ArrayList());
        requestBody.put("gender_preferences",  new ArrayList());

        // Mock the leaseHolderTableOperations.createAD method to return a leaseholder_application_id
        int leaseholderApplicationId = 123;
        when(leaseHolderTableOperations.createAD(any(LeaseHolderModel.class))).thenReturn(leaseholderApplicationId);

        // Mock the leaseHolderFoodTableOperations.createFoodReferences, leaseHolderImagesTableOperations.addImages,
        // and leaseHolderGenderTableOperations.createGenderReferences methods to return true
        when(leaseHolderFoodTableOperations.createFoodReferences(any(LeaseHolderModel.class), eq(leaseholderApplicationId))).thenReturn(true);
        when(leaseHolderImagesTableOperations.addImages(any(LeaseHolderModel.class), eq(leaseholderApplicationId))).thenReturn(true);
        when(leaseHolderGenderTableOperations.createGenderReferences(any(LeaseHolderModel.class), eq(leaseholderApplicationId))).thenReturn(true);

        // Act
        String result = leaseHolderService.createAD(requestBody);

        // Assert
        assertEquals("Success", result);
        verify(leaseHolderTableOperations, times(1)).createAD(any(LeaseHolderModel.class));
        verify(leaseHolderFoodTableOperations, times(1)).createFoodReferences(any(LeaseHolderModel.class), eq(leaseholderApplicationId));
        verify(leaseHolderImagesTableOperations, times(1)).addImages(any(LeaseHolderModel.class), eq(leaseholderApplicationId));
        verify(leaseHolderGenderTableOperations, times(1)).createGenderReferences(any(LeaseHolderModel.class), eq(leaseholderApplicationId));
    }

    @Test
    void testCreateAD_InvalidInput() throws Exception {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();

        // Act & Assert
        assertThrows(RuntimeException.class, () -> leaseHolderService.createAD(requestBody));
        verifyNoInteractions(leaseHolderTableOperations, leaseHolderFoodTableOperations, leaseHolderGenderTableOperations);
    }

    @Test
    void testCreateAD_Exception() {
        // Arrange
        Map<String, Object> requestBody = new HashMap<>();
       
        // Mock the leaseHolderTableOperations.createAD method to throw an exception
        when(leaseHolderTableOperations.createAD(any(LeaseHolderModel.class))).thenThrow(new RuntimeException());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> leaseHolderService.createAD(requestBody));
    }
}
